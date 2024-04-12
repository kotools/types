import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration

buildscript {
    dependencies.classpath(libs.dokka.base)
}

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.dokka)
}

dependencies.dokkaPlugin(libs.dokka.base)

tasks.dokkaHtmlMultiModule.configure {
    // ------------------------ Specific configuration -------------------------
    moduleName.set("Kotools Types")
    layout.buildDirectory.dir("dokka")
        .let(outputDirectory::set)
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
    // ----------------------- Common task configuration -----------------------
    subprojects.mapNotNull { it.tasks.findByName("restoreMainSources") }
        .let(this::setFinalizedBy)
}

allprojects {
    group = "org.kotools"
    repositories.mavenCentral()
}
