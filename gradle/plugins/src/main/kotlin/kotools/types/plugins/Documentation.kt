package kotools.types.plugins

import kotools.types.tasks.TaskGroup
import kotools.types.tasks.description
import kotools.types.tasks.group
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
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
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.versioning.VersioningConfiguration
import org.jetbrains.dokka.versioning.VersioningPlugin
import java.io.File

/** Plugin configuring the API reference of Kotools Types. */
public class DocumentationPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        project.tasks.configureDokkaHtml(project)
        project.tasks.registerCleanDokkaHtml()
    }
}

private fun TaskContainer.configureDokkaHtml(project: Project) {
    val apiReferencesDir: Directory =
        project.layout.projectDirectory.dir("api/references")
    val setApiReferenceLogoTask: TaskProvider<Copy> =
        register<Copy>("setApiReferenceLogo")
    val archiveApiReferenceTask: TaskProvider<Copy> =
        register<Copy>("archiveApiReference")
    val dokkaHtml: TaskProvider<DokkaTask> = named<DokkaTask>("dokkaHtml") {
        moduleName.set("Kotools Types")
        dokkaSourceSets.configureEach {
            includes.from += project.layout.projectDirectory.file("packages.md")
            reportUndocumented.set(true)
            skipEmptyPackages.set(true)
        }
        val currentVersion = "${project.version}"
        pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
            version = currentVersion
            olderVersionsDir = apiReferencesDir.asFile
        }
        val outputDir: Provider<File> = project.layout.buildDirectory
            .dir("dokka")
            .map { it.asFile }
        outputDirectory.set(outputDir)
        finalizedBy(setApiReferenceLogoTask, archiveApiReferenceTask)
    }
    setApiReferenceLogoTask.configure {
        group(TaskGroup.DOCUMENTATION)
        description("Sets the Kotools logo into the API reference.")
        val images = "images"
        val source: RegularFile =
            project.layout.projectDirectory.file("$images/logo-icon.svg")
        from(source)
        val destination: Provider<File> = dokkaHtml.get()
            .outputDirectory
            .map { it.resolve(images) }
        into(destination)
    }
    val deleteOlderDirInArchivedApiReference: TaskProvider<Delete> =
        register<Delete>("deleteOlderDirInArchivedApiReference")
    archiveApiReferenceTask.configure {
        group(TaskGroup.DOCUMENTATION)
        description("Archives the API reference.")
        onlyIf { "SNAPSHOT" !in "${project.version}" }
        dependsOn += setApiReferenceLogoTask
        from(dokkaHtml)
        val destination: Directory = apiReferencesDir.dir("${project.version}")
        into(destination)
        finalizedBy(deleteOlderDirInArchivedApiReference)
    }
    deleteOlderDirInArchivedApiReference.configure {
        group(TaskGroup.DOCUMENTATION)
        description(
            "Deletes the 'older' directory in the archived API reference."
        )
        val target: File = archiveApiReferenceTask.get()
            .destinationDir
            .resolve("older")
        setDelete(target)
    }
    val apiReferenceJar: TaskProvider<Jar> = register<Jar>("apiReferenceJar") {
        group(TaskGroup.DOCUMENTATION)
        description("Archives the API reference in a JAR file.")
        dependsOn(setApiReferenceLogoTask, archiveApiReferenceTask)
        from(dokkaHtml)
        archiveClassifier.set("javadoc")
    }
    named<DefaultTask>("assemble").configure { dependsOn += apiReferenceJar }
    project.extensions.getByType<PublishingExtension>()
        .publications
        .withType<MavenPublication>()
        .configureEach { artifact(apiReferenceJar) }
}

private fun TaskContainer.registerCleanDokkaHtml() {
    val task: TaskProvider<Delete> = register<Delete>("cleanDokkaHtml") {
        val task: DokkaTask = named<DokkaTask>("dokkaHtml").get()
        setDelete(task.outputDirectory)
    }
    named<Delete>("clean").configure { dependsOn += task }
}