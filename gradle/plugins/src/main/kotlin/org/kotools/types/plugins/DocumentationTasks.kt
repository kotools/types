package org.kotools.types.plugins

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.Directory
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.versioning.VersioningConfiguration
import org.jetbrains.dokka.versioning.VersioningPlugin

internal class DocumentationTasks(project: Project) {
    private val tasks: TaskContainer = project.tasks

    fun apiReference(): TaskProvider<Task> = tasks.register("apiReference") {
        description = "Generates the API reference for this project."
        group = "recommended"
    }

    fun cleanApiReference(
        documentation: DocumentationExtension
    ): TaskProvider<Delete> = tasks.register<Delete>("cleanApiReference") {
        description = "Deletes the API reference from the build directory."
        setDelete(documentation.outputDirectory)
    }

    fun dokkaHtml(
        documentation: DocumentationExtension
    ): TaskProvider<DokkaTask> = tasks.named<DokkaTask>("dokkaHtml") {
        documentation.moduleName.orNull?.let(moduleName::set)
        outputDirectory.set(documentation.outputDirectory)
        failOnWarning.set(true)
        dokkaSourceSets.configureEach {
            documentation.packages.orNull?.let { includes.setFrom(it) }
            reportUndocumented.set(true)
            skipEmptyPackages.set(true)
        }
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            customAssets = documentation.logoIcon.orNull?.let(::listOf)
                ?: emptyList()
            documentation.copyrightNotice.orNull?.let { footerMessage = it }
        }
        pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
            version = project.version.toString()
            olderVersionsDir = project.layout.buildDirectory
                .dir("api-references")
                .map(Directory::getAsFile)
                .get()
        }
    }

    fun dokkaHtmlMultiModule(
        documentation: DocumentationExtension
    ): TaskProvider<DokkaMultiModuleTask> =
        tasks.named<DokkaMultiModuleTask>("dokkaHtmlMultiModule") {
            moduleName.set(documentation.moduleName)
            outputDirectory.set(documentation.outputDirectory)
            pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                customAssets = documentation.logoIcon.orNull?.let(::listOf)
                    ?: emptyList()
                documentation.copyrightNotice.orNull?.let { footerMessage = it }
            }
        }

    fun dokkaHtmlMultiModuleExists(): Boolean =
        tasks.findByName("dokkaHtmlMultiModule") != null
}
