package convention.documentation

import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.kotools.types.gradle.internal.TaskGroup

pluginManager.apply(BasePlugin::class)
pluginManager.apply(DokkaPlugin::class)

private val extension: DocumentationModuleExtension =
    extensions.create("documentation")
extension.excludeFromParentApiReference.convention(false)

tasks.withType<AbstractDokkaLeafTask>().configureEach {
    failOnWarning.set(true)
    dokkaSourceSets.configureEach {
        extension.packages.orNull?.let { includes.setFrom(it) }
        reportUndocumented.set(true)
        skipEmptyPackages.set(true)
        extension.externalLinks.orNull?.filterNotNull()
            ?.forEach { externalDocumentationLink(it) }
    }
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        customAssets = rootProject.layout.projectDirectory
            .file("documentation/api-reference/logo-icon.svg")
            .asFile
            .let(::listOf)
        footerMessage = rootProject.layout.projectDirectory
            .file("LICENSE.txt")
            .asFile
            .useLines { lines: Sequence<String> ->
                lines.first { it.startsWith("Copyright (c)") }
            }
    }
}

tasks.withType<DokkaTask>().configureEach {
    outputDirectory.set(layout.buildDirectory.dir("api-reference"))
}

tasks.withType<DokkaTaskPartial>().configureEach {
    onlyIf { !extension.excludeFromParentApiReference.get() }
}

private val dokkaHtml: TaskProvider<DokkaTask> =
    tasks.named<DokkaTask>("dokkaHtml")

private val apiReference: TaskProvider<Task> by tasks.registering
apiReference.configure {
    description = "Generates the API reference."
    group = TaskGroup.Module.toString()
    dependsOn += dokkaHtml
}

private val apiReferenceJar: TaskProvider<Jar> by tasks
    .registering(Jar::class) {
        description = "Archives the API reference in a JAR file."
        group = TaskGroup.Documentation.toString()
        from(dokkaHtml)
        archiveClassifier.set("javadoc")
    }

tasks.named(BasePlugin.ASSEMBLE_TASK_NAME)
    .configure { dependsOn += apiReferenceJar }
