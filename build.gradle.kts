plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.convention.documentation.root)
    alias(libs.plugins.convention.publication.root)
}

repositories.mavenCentral()

documentation.moduleName = "Kotools Types"

dependencies.dokkaHtmlMultiModulePlugin(libs.dokka.versioning)
