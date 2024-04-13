package org.kotools.types.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

/** Plugin configuring the documentation of Kotools Types. */
public class DocumentationPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        val properties = DocumentationProperties(project)
        val cleanApiReference: TaskProvider<Delete> =
            project.tasks.register<Delete>("cleanApiReference") {
                description =
                    "Deletes the API reference from the build directory."
                setDelete(properties.apiReferenceDirectory)
            }
        val dokkaHtmlMultiModule: TaskProvider<DokkaMultiModuleTask> =
            project.tasks.named<DokkaMultiModuleTask>("dokkaHtmlMultiModule") {
                // Common task configuration
                dependsOn += cleanApiReference
                project.subprojects
                    .mapNotNull { it.tasks.findByName("restoreMainSources") }
                    .takeIf(List<Task>::isNotEmpty)
                    ?.let(this::setFinalizedBy)
                // Specific task configuration
                moduleName = "Kotools Types"
                outputDirectory = properties.apiReferenceDirectory
                pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                    customAssets = listOf(properties.logoIcon.asFile)
                    footerMessage = properties.copyrightNotice
                }
            }
        project.tasks.register("apiReferenceMultiModule").configure {
            description =
                "Generates the API reference for this project and its subprojects."
            group = "recommended"
            dependsOn += dokkaHtmlMultiModule
        }
    }
}
