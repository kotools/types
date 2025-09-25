package org.kotools.types.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.TaskCollection
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.versioning.VersioningConfiguration
import org.jetbrains.dokka.versioning.VersioningPlugin

/**
 * Gradle convention plugin that configures the documentation of a root project.
 */
public class DocumentationRootPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project): Unit =
        project.pluginManager.withPlugin("org.jetbrains.dokka") {
            val dokkaMultiModuleTasks: TaskCollection<DokkaMultiModuleTask> =
                project.tasks.withType<DokkaMultiModuleTask>()
            dokkaMultiModuleTasks.configureEach {
                this.moduleName.set("Kotools Types")
                this.outputDirectory.set(
                    project.layout.buildDirectory.dir("api-reference")
                )
                this.pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                    this.customAssets = project.rootProject.layout
                        .projectDirectory
                        .file("documentation/api-reference/logo-icon.svg")
                        .asFile
                        .let(::listOf)
                    this.footerMessage = project.rootProject.layout
                        .projectDirectory
                        .file("LICENSE.txt")
                        .asFile
                        .useLines { lines: Sequence<String> ->
                            lines.first { it.startsWith("Copyright (c)") }
                        }
                }
                pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
                    this.version = project.version.toString()
                    this.olderVersionsDir = project.layout.projectDirectory
                        .dir("documentation/api-reference/archive")
                        .asFile
                    this.renderVersionsNavigationOnAllPages = false
                }
            }
            val dokkaHtmlMultiModule: TaskProvider<DokkaMultiModuleTask> =
                dokkaMultiModuleTasks.named("dokkaHtmlMultiModule")
            project.pluginManager.withPlugin("base") {
                project.tasks.named(BasePlugin.ASSEMBLE_TASK_NAME)
                    .configure { this.dependsOn(dokkaHtmlMultiModule) }
            }
        }
}
