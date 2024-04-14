package org.kotools.types.plugins

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.Directory
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.register
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.AbstractDokkaTask
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.versioning.VersioningConfiguration
import org.jetbrains.dokka.versioning.VersioningPlugin

internal class DocumentationTasks(project: Project) {
    private val tasks: TaskContainer = project.tasks

    // ---------------------------- Internal tasks -----------------------------

    fun apiReference(): TaskProvider<Task> = tasks.register("apiReference") {
        description = "Generates the API reference for this project."
        group = "recommended"
    }

    fun apiReferenceJar(dokkaTask: TaskProvider<DokkaTask>): TaskProvider<Jar> =
        tasks.register<Jar>("apiReferenceJar") {
            description = "Archives the API reference in a JAR file."
            from(dokkaTask)
            archiveClassifier.set("javadoc")
        }

    fun archiveApiReference(
        extension: DocumentationExtension,
        dokkaTask: TaskProvider<DokkaMultiModuleTask>
    ): Unit = tasks.register<Copy>("archiveApiReference").configure {
        description = "Archives the API reference in the project directory."
        onlyIf { !"${project.version}".endsWith("SNAPSHOT") }
        dependsOn += dokkaTask
        from(extension.outputDirectory) {
            exclude(
                "older/**",
                "${project.name}-internal",
                "${project.name}*/**/scripts/"
            )
        }
        project.layout.projectDirectory
            .dir("documentation/api-reference/${project.version}")
            .let(this::into)
    }

    fun cleanApiReference(
        extension: DocumentationExtension
    ): TaskProvider<Delete> = tasks.register<Delete>("cleanApiReference") {
        description = "Deletes the API reference from the build directory."
        setDelete(extension.outputDirectory)
    }

    // --------------------- External task configurations ----------------------

    fun dokkaTaskConfiguration(
        extension: DocumentationExtension
    ): DokkaTask.() -> Unit = {
        extension.moduleName.orNull?.let(moduleName::set)
        failOnWarning.set(true)
        dokkaSourceSets.configureEach {
            extension.packages.orNull?.let { includes.setFrom(it) }
            reportUndocumented.set(true)
            skipEmptyPackages.set(true)
        }
        pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
            version = project.version.toString()
            olderVersionsDir = project.layout.buildDirectory
                .dir("api-references")
                .map(Directory::getAsFile)
                .get()
        }
        commonConfiguration(extension)
    }

    fun dokkaMultiModuleTaskConfiguration(
        extension: DocumentationExtension
    ): DokkaMultiModuleTask.() -> Unit = {
        moduleName.set(extension.moduleName)
        commonConfiguration(extension)
    }
}

private fun AbstractDokkaTask.commonConfiguration(
    extension: DocumentationExtension
) {
    outputDirectory.set(extension.outputDirectory)
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        extension.logoIcon.orNull?.let { customAssets = listOf(it) }
        extension.copyrightNotice.orNull?.let { footerMessage = it }
    }
}
