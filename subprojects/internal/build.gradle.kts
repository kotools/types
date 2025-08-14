// ---------------------------------- Plugins ----------------------------------

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
    alias(libs.plugins.kotools.types.kotlin.multiplatform)
    alias(libs.plugins.kotools.types.documentation)
    alias(libs.plugins.kotools.types.publication)
}

// ----------------------------- Plugin extensions -----------------------------

documentation.excludeFromParentApiReference = true

// ------------------------------- Dependencies --------------------------------

dependencies.commonMainImplementation(libs.kotlinx.serialization.core)
