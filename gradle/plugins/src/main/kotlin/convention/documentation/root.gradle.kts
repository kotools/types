package convention.documentation

import convention.base.TaskGroup
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.versioning.VersioningConfiguration
import org.jetbrains.dokka.versioning.VersioningPlugin

plugins {
    base
    id("org.jetbrains.dokka")
}

private val dokkaMultiModuleTasks: TaskCollection<DokkaMultiModuleTask> =
    tasks.withType()
dokkaMultiModuleTasks.configureEach {
    this.moduleName = "Kotools Types"
    this.outputDirectory = project.layout.buildDirectory.dir("api-reference")
    this.pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        this.customAssets = project.rootProject.layout.projectDirectory
            .file("documentation/api-reference/logo-icon.svg")
            .asFile
            .let(::listOf)
        this.footerMessage = project.rootProject.layout.projectDirectory
            .file("LICENSE.txt")
            .asFile
            .useLines { lines: Sequence<String> ->
                lines.first { it.startsWith("Copyright (c)") }
            }
    }
    this.pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
        this.version = project.version.toString()
        this.olderVersionsDir = project.layout.projectDirectory
            .dir("documentation/api-reference/archive")
            .asFile
        this.renderVersionsNavigationOnAllPages = false
    }
}

private val dokkaHtmlMultiModule: TaskProvider<DokkaMultiModuleTask> =
    dokkaMultiModuleTasks.named("dokkaHtmlMultiModule")

private val assembleApiReference: TaskProvider<Task> by tasks.registering
assembleApiReference.configure {
    this.description = "Assembles the API reference of this project."
    this.group = TaskGroup.Root.toString()
    this.dependsOn(dokkaHtmlMultiModule)
}

tasks.named("assemble")
    .configure { this.dependsOn(assembleApiReference) }
