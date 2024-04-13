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
    private val tasks: TaskContainer = project.tasks

    fun apiReferenceMultiModule(): TaskProvider<Task> =
        tasks.register("apiReferenceMultiModule") {
            description = "Generates the API reference for this project and " +
                    "its subprojects."
            group = "recommended"
        }

    fun cleanApiReference(
        documentation: DocumentationExtension
    ): TaskProvider<Delete> = tasks.register<Delete>("cleanApiReference") {
        description = "Deletes the API reference from the build directory."
        setDelete(documentation.outputDirectory)
    }

    fun dokkaHtmlMultiModule(
        documentation: DocumentationExtension
    ): TaskProvider<DokkaMultiModuleTask> =
        tasks.named<DokkaMultiModuleTask>("dokkaHtmlMultiModule") {
            // Common task configuration
            project.subprojects
                .mapNotNull { it.tasks.findByName("restoreMainSources") }
                .takeIf(List<Task>::isNotEmpty)
                ?.let(this::setFinalizedBy)
            // Specific task configuration
            moduleName.set(documentation.moduleName)
            outputDirectory.set(documentation.outputDirectory)
            pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                customAssets = documentation.logoIcon.orNull?.let(::listOf)
                    ?: emptyList()
                documentation.copyrightNotice.orNull?.let { footerMessage = it }
            }
        }
}
