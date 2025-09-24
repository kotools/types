package convention.documentation

import convention.base.TaskGroup
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.versioning.VersioningConfiguration
import org.jetbrains.dokka.versioning.VersioningPlugin

pluginManager.apply(DokkaPlugin::class)

private val extension: DocumentationRootExtension =
    extensions.create("documentation")

tasks.withType<DokkaMultiModuleTask>().configureEach {
    moduleName = extension.moduleName
    outputDirectory.set(layout.buildDirectory.dir("api-reference"))
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        customAssets = rootProject.layout.projectDirectory
            .file("documentation/api-reference/logo-icon.svg")
            .asFile
            .let(::listOf)
        footerMessage = rootProject.layout.projectDirectory.file("LICENSE.txt")
            .asFile
            .useLines { lines: Sequence<String> ->
                lines.first { it.startsWith("Copyright (c)") }
            }
    }
    pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
        version = project.version.toString()
        olderVersionsDir = layout.projectDirectory
            .dir("documentation/api-reference/archive")
            .asFile
        renderVersionsNavigationOnAllPages = false
    }
}

private val dokkaHtmlMultiModule: TaskProvider<DokkaMultiModuleTask> =
    tasks.named<DokkaMultiModuleTask>("dokkaHtmlMultiModule")

private val apiReference: TaskProvider<Task> by tasks.registering
apiReference.configure {
    description = "Generates the API reference."
    group = TaskGroup.Root.toString()
    dependsOn += dokkaHtmlMultiModule
}
