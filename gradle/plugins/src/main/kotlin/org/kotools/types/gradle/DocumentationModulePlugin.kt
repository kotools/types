package org.kotools.types.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.kotools.types.gradle.internal.TaskGroup

/** Gradle convention plugin that configures the documentation of a module. */
public class DocumentationModulePlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        val extension: DocumentationModuleExtension =
            project.extensions.create("documentation")
        extension.excludeFromParentApiReference.convention(false)
        project.pluginManager.withPlugin("org.jetbrains.dokka") {
            project.tasks.withType<AbstractDokkaLeafTask>().configureEach {
                this.failOnWarning.set(true)
                this.dokkaSourceSets.configureEach {
                    extension.packages.orNull?.let { this.includes.setFrom(it) }
                    this.reportUndocumented.set(true)
                    this.skipEmptyPackages.set(true)
                    extension.externalLinks.orNull?.filterNotNull()
                        ?.forEach(::externalDocumentationLink)
                }
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
            }
            project.tasks.withType<DokkaTaskPartial>().configureEach {
                this.onlyIf { !extension.excludeFromParentApiReference.get() }
            }
            val dokkaHtml: TaskProvider<DokkaTask> =
                project.tasks.named<DokkaTask>("dokkaHtml")
            val apiReferenceJar: TaskProvider<Jar> =
                project.tasks.register<Jar>("apiReferenceJar") {
                    this.description =
                        "Archives the API reference in a JAR file."
                    this.group = TaskGroup.Documentation.toString()
                    this.from(dokkaHtml)
                    this.archiveClassifier.set("javadoc")
                }
            project.pluginManager.withPlugin("base") {
                project.tasks.named(BasePlugin.ASSEMBLE_TASK_NAME)
                    .configure { this.dependsOn(apiReferenceJar) }
            }
        }
    }
}
