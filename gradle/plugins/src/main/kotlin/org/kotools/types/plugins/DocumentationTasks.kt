package org.kotools.types.plugins

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

internal class DocumentationTasks(project: Project) {
    private val properties: DocumentationProperties =
        DocumentationProperties(project)
    private val tasks: TaskContainer = project.tasks

    fun apiReferenceMultiModule(): TaskProvider<Task> =
        tasks.register("apiReferenceMultiModule") {
            description = "Generates the API reference for this project and " +
                    "its subprojects."
            group = "recommended"
        }

    fun cleanApiReference(): TaskProvider<Delete> =
        tasks.register<Delete>("cleanApiReference") {
            description = "Deletes the API reference from the build directory."
            setDelete(properties.apiReferenceDirectory)
        }

    fun dokkaHtmlMultiModule(): TaskProvider<DokkaMultiModuleTask> =
        tasks.named<DokkaMultiModuleTask>("dokkaHtmlMultiModule") {
            // Common task configuration
            project.subprojects
                .mapNotNull { it.tasks.findByName("restoreMainSources") }
                .takeIf(List<Task>::isNotEmpty)
                ?.let(this::setFinalizedBy)
            // Specific task configuration
            moduleName.set("Kotools Types")
            outputDirectory.set(properties.apiReferenceDirectory)
            pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                customAssets = listOf(properties.logoIcon.asFile)
                footerMessage = properties.copyrightNotice
            }
        }
}
