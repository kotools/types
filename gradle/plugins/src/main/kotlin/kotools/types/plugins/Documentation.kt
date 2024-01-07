package kotools.types.plugins

import kotools.types.tasks.TaskGroup
import kotools.types.tasks.description
import kotools.types.tasks.group
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
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
        saveApiReference()
        cleanDokkaHtml()
    }
}

private fun TaskContainer.dokkaTasks(project: Project): Unit =
    this.withType<DokkaTask>().configureEach {
        this.moduleName.set("Kotools Types")
        this.failOnWarning.set(true)
        project.layout.buildDirectory.dir("dokka")
            .map { it.asFile }
            .let { this.outputDirectory.set(it) }
        this.dokkaSourceSets.configureEach {
            this.includes.setFrom("src/packages.md")
            this.reportUndocumented.set(true)
            this.skipEmptyPackages.set(true)
        }
        this.pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            this.customAssets = listOf(project.logoIcon)
            this.footerMessage = project.copyrightNotice
        }
        this.pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
            this.version = project.version.toString()
            this.olderVersionsDir = project.file("api/references")
        }
    }

private val Project.copyrightNotice: String
    get() = this.rootDir.resolve("LICENSE.txt")
        .useLines { lines: Sequence<String> ->
            lines.first { it.startsWith("Copyright (c)") }
        }

private val Project.logoIcon: File
    get() = this.rootDir.resolve("assets/logo-icon.svg")

private fun TaskContainer.apiReferenceJar(project: Project) {
    val apiReferenceJar: TaskProvider<Jar> =
        this.register<Jar>("apiReferenceJar") {
            this.group(TaskGroup.BUILD)
            this.description("Archives the API reference in a JAR file.")
            this.from(dokkaHtml)
            this.archiveClassifier.set("javadoc")
        }
    this.named("assemble").configure { this.dependsOn += apiReferenceJar }
    project.includeInPublication(apiReferenceJar)
}

private val TaskContainer.dokkaHtml: TaskProvider<DokkaTask>
    get() = this.named<DokkaTask>("dokkaHtml")

private fun Project.includeInPublication(jar: TaskProvider<Jar>): Unit = this
    .extensions
    .getByType<PublishingExtension>()
    .publications
    .withType<MavenPublication>()
    .configureEach { this.artifact(jar) }

private fun TaskContainer.saveApiReference() {
    val saveApiReference: TaskProvider<Copy> =
        this.register<Copy>("saveApiReference") {
            this.group(TaskGroup.DOCUMENTATION)
            this.description("Archives the API reference.")
            this.from(dokkaHtml)
            this.exclude("older/**")
            this.into("api/references/${project.version}")
        }
    this.assembleApiReferenceForWebsite(saveApiReference)
}

private fun TaskContainer.assembleApiReferenceForWebsite(
    saveApiReference: TaskProvider<Copy>
): Unit = this.register("assembleApiReferenceForWebsite").configure {
    this.group(TaskGroup.BUILD)
    this.description("Assembles the API reference for our website.")
    this.dependsOn += saveApiReference
}

private fun TaskContainer.cleanDokkaHtml() {
    val dokkaHtml: TaskProvider<DokkaTask> = this.dokkaHtml
    val cleanDokkaHtml: TaskProvider<Delete> =
        this.register<Delete>("cleanDokkaHtml") {
            this.group(TaskGroup.BUILD)
            this.description(
                "Deletes the output directory of the '${dokkaHtml.name}' task."
            )
            val target: Property<File> = dokkaHtml.get().outputDirectory
            this.setDelete(target)
        }
    this.named<Delete>("clean").configure { this.dependsOn += cleanDokkaHtml }
}
