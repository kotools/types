package org.kotools.types

import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

private val apiReferenceDirectory: Provider<Directory> =
    layout.buildDirectory.dir("api-reference")

private val cleanApiReference: TaskProvider<Delete> by tasks
    .registering(Delete::class) {
        // Common task configuration
        description = "Deletes the API reference from the build directory."
        // Specific task configuration
        setDelete(apiReferenceDirectory)
    }

private val dokkaHtmlMultiModule: TaskProvider<DokkaMultiModuleTask> =
    tasks.named<DokkaMultiModuleTask>("dokkaHtmlMultiModule") {
        // Common task configuration
        dependsOn += cleanApiReference
        subprojects.mapNotNull { it.tasks.findByName("restoreMainSources") }
            .takeIf(List<Task>::isNotEmpty)
            ?.let(this::setFinalizedBy)
        // Specific task configuration
        moduleName = "Kotools Types"
        outputDirectory = apiReferenceDirectory
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
            customAssets = layout.projectDirectory.file("dokka/logo-icon.svg")
                .asFile
                .let(::listOf)
            footerMessage = layout.projectDirectory.file("LICENSE.txt")
                .asFile
                .useLines { lines: Sequence<String> ->
                    lines.first { it.startsWith("Copyright (c)") }
                }
        }
    }

tasks.register("apiReferenceMultiModule").configure {
    description =
        "Generates the API reference for this project and its subprojects."
    group = "recommended"
    dependsOn += dokkaHtmlMultiModule
}
