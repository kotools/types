package convention.documentation

import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial

// ----------------------------- Plugin extension ------------------------------

/**
 * The extension responsible for configuring the
 * `convention.documentation.module` Gradle plugin.
 */
public interface DocumentationModuleExtension {
    /**
     * Flag for excluding this subproject from the API reference of the parent
     * project. Set to `false` by default.
     */
    public val excludeFromParentApiReference: Property<Boolean>

    /**
     * Links to use for resolving this project's dependencies in the
     * documentation.
     */
    public val externalLinks: ListProperty<String>

    /** The file containing module and package documentation. */
    public val packages: Property<File>
}

private val extension: DocumentationModuleExtension =
    extensions.create("documentation")

extension.excludeFromParentApiReference.convention(false)

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

// ----------------------------------- Tasks -----------------------------------

private val apiReference: TaskProvider<Task> by tasks.registering {
    description = "Generates the API reference for this project."
    group = "documentation"
}

private val apiReferenceJar: TaskProvider<Jar> by tasks
    .registering(Jar::class) {
        description = "Archives the API reference in a JAR file."
        group = "documentation"
    }

// ----------------------------- Dokka integration -----------------------------

pluginManager.apply(DokkaPlugin::class)

tasks.withType<AbstractDokkaLeafTask>().configureEach {
    group = ""
    failOnWarning.set(true)
    dokkaSourceSets.configureEach {
        extension.packages.orNull?.let { includes.setFrom(it) }
        reportUndocumented.set(true)
        skipEmptyPackages.set(true)
        extension.externalLinks.orNull?.filterNotNull()
            ?.forEach { externalDocumentationLink(it) }
    }
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        customAssets = listOf(logoIcon)
        footerMessage = copyrightNotice
    }
}

tasks.withType<DokkaTask>()
    .configureEach { outputDirectory.set(buildDirectory) }

tasks.withType<DokkaTaskPartial>().configureEach {
    onlyIf { !extension.excludeFromParentApiReference.get() }
}

private val dokkaHtml: TaskProvider<DokkaTask> =
    tasks.named<DokkaTask>("dokkaHtml")

apiReference.configure { dependsOn += dokkaHtml }

apiReferenceJar.configure {
    from(dokkaHtml)
    archiveClassifier.set("javadoc")
}

// ---------------------- Gradle Base Plugin integration -----------------------

pluginManager.apply(BasePlugin::class)

tasks.named(BasePlugin.ASSEMBLE_TASK_NAME)
    .configure { dependsOn += apiReferenceJar }
