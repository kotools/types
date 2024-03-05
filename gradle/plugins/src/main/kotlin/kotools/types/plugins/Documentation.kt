package kotools.types.plugins

import kotools.types.tasks.TaskGroup
import kotools.types.tasks.description
import kotools.types.tasks.group
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Provider
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.versioning.VersioningConfiguration
import org.jetbrains.dokka.versioning.VersioningPlugin
import java.io.File

/** Plugin configuring the API reference of Kotools Types. */
public class DocumentationPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project): Unit = project.tasks.run {
        dokkaTasks(project)
        apiReferenceJar(project)
        saveApiReference(project)
        cleanDokkaHtml()
    }
}

private fun TaskContainer.dokkaTasks(project: Project): Unit =
    withType<DokkaTask>().configureEach {
        val dokkaDirectory: Provider<Directory> =
            project.layout.buildDirectory.dir("dokka")
        outputDirectory.set(dokkaDirectory)
        dokkaSourceSets.configureEach { skipEmptyPackages.set(true) }
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            customAssets = listOf(project.logoIcon)
            footerMessage = project.copyrightNotice
        }
        pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
            version = project.version.toString()
            olderVersionsDir = project.archivedApiReferences
        }
    }

private val Project.copyrightNotice: String
    get() = rootDir.resolve("LICENSE.txt").useLines { lines: Sequence<String> ->
        lines.first { it.startsWith("Copyright (c)") }
    }

private val Project.logoIcon: File
    get() = rootDir.resolve("dokka/logo-icon.svg")

private val Project.archivedApiReferences: File
    get() = project.layout.buildDirectory.dir("api-references")
        .map(Directory::getAsFile)
        .get()

private fun TaskContainer.apiReferenceJar(project: Project) {
    val apiReferenceJar: TaskProvider<Jar> = register<Jar>("apiReferenceJar") {
        group(TaskGroup.BUILD)
        description("Archives the API reference in a JAR file.")
        from(dokkaHtml)
        archiveClassifier.set("javadoc")
    }
    named("assemble").configure { dependsOn += apiReferenceJar }
    project.includeInPublication(apiReferenceJar)
}

private val TaskContainer.dokkaHtml: TaskProvider<DokkaTask>
    get() = named<DokkaTask>("dokkaHtml")

private fun Project.includeInPublication(jar: TaskProvider<Jar>): Unit =
    extensions.getByType<PublishingExtension>()
        .publications
        .withType<MavenPublication>()
        .configureEach { artifact(jar) }

private fun TaskContainer.saveApiReference(project: Project) {
    val saveApiReference: TaskProvider<Copy> =
        register<Copy>("saveApiReference") {
            group(TaskGroup.DOCUMENTATION)
            description("Archives the API reference.")
            from(dokkaHtml)
            exclude("older/**")
            into("${project.archivedApiReferences}/${project.version}")
        }
    assembleApiReferenceForWebsite(saveApiReference)
}

private fun TaskContainer.assembleApiReferenceForWebsite(
    saveApiReference: TaskProvider<Copy>
): Unit = register("assembleApiReferenceForWebsite").configure {
    group(TaskGroup.BUILD)
    description("Assembles the API reference for our website.")
    dependsOn += saveApiReference
}

private fun TaskContainer.cleanDokkaHtml() {
    val dokkaHtml: TaskProvider<DokkaTask> = this.dokkaHtml
    val cleanDokkaHtml: TaskProvider<Delete> =
        register<Delete>("cleanDokkaHtml") {
            group(TaskGroup.BUILD)
            description(
                "Deletes the output directory of the '${dokkaHtml.name}' task."
            )
            val target: DirectoryProperty = dokkaHtml.get().outputDirectory
            setDelete(target)
        }
    named<Delete>("clean").configure { dependsOn += cleanDokkaHtml }
}
