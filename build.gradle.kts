plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.convention.base.root)
    alias(libs.plugins.convention.documentation.root)
    alias(libs.plugins.convention.publication.root)
}

dependencies.dokkaHtmlMultiModulePlugin(libs.dokka.versioning)
