package convention.documentation

import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.versioning.VersioningConfiguration
import org.jetbrains.dokka.versioning.VersioningPlugin

// ----------------------------- Plugin extension ------------------------------

/**
 * The extension responsible for configuring the `convention.documentation.root`
 * Gradle plugin.
 */
public interface DocumentationRootExtension {
    /** The name of the module to document. */
    public val moduleName: Property<String>
}

private val extension: DocumentationRootExtension =
    extensions.create("documentation")

// ----------------------------- Script properties -----------------------------

private val buildDirectory: Provider<Directory> =
    layout.buildDirectory.dir("api-reference")

private val copyrightNotice: String = rootProject.layout.projectDirectory
    .file("LICENSE.txt")
    .asFile
    .useLines { lines: Sequence<String> ->
        lines.first { it.startsWith("Copyright (c)") }
    }

private val logoIcon: File = rootProject.layout.projectDirectory
    .file("documentation/api-reference/logo-icon.svg")
    .asFile

private val archiveParentDirectory: Directory =
    layout.projectDirectory.dir("documentation/api-reference")

// ----------------------------------- Tasks -----------------------------------

private val apiReference: TaskProvider<Task> by tasks.registering {
    description = "Generates the API reference for this project."
    group = "documentation"
}

// ----------------------------- Dokka integration -----------------------------

pluginManager.apply(DokkaPlugin::class)

tasks.withType<DokkaMultiModuleTask>().configureEach {
    moduleName = extension.moduleName
    outputDirectory.set(buildDirectory)
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        customAssets = listOf(logoIcon)
        footerMessage = copyrightNotice
    }
    pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
        version = project.version.toString()
        olderVersionsDir = archiveParentDirectory.dir("archive").asFile
        renderVersionsNavigationOnAllPages = false
    }
}

private val dokkaHtmlMultiModule: TaskProvider<DokkaMultiModuleTask> =
    tasks.named<DokkaMultiModuleTask>("dokkaHtmlMultiModule")

apiReference.configure { dependsOn += dokkaHtmlMultiModule }
