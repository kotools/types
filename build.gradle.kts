plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.convention.base.root)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotools.types.documentation.root)
    alias(libs.plugins.convention.publication.root)
}

dependencies.dokkaHtmlMultiModulePlugin(libs.dokka.versioning)
