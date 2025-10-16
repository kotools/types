package convention.documentation

import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial

plugins {
    base
    id("org.jetbrains.dokka")
}

private val documentation: DocumentationModuleExtension =
    extensions.create("documentation")
documentation.excludeFromParentApiReference.convention(false)

tasks.withType<AbstractDokkaLeafTask>().configureEach {
    this.failOnWarning.set(true)
    this.dokkaSourceSets.configureEach {
        documentation.packages.orNull?.let { this.includes.setFrom(it) }
        this.reportUndocumented.set(true)
        this.skipEmptyPackages.set(true)
        documentation.externalLinks.orNull?.filterNotNull()
            ?.forEach(this::externalDocumentationLink)
    }
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
}

tasks.withType<DokkaTaskPartial>().configureEach {
    this.onlyIf { !documentation.excludeFromParentApiReference.get() }
}

private val dokkaHtml: TaskProvider<DokkaTask> =
    tasks.named<DokkaTask>("dokkaHtml")

private val apiReferenceJar: TaskProvider<Jar> by tasks.registering(Jar::class)
apiReferenceJar.configure {
    this.from(dokkaHtml)
    this.archiveClassifier.set("javadoc")
}

tasks.withType<PublishToMavenRepository>()
    .configureEach { this.dependsOn(apiReferenceJar) }

tasks.named("assemble")
    .configure { this.dependsOn(apiReferenceJar) }
