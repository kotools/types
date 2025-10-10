plugins {
    alias(libs.plugins.kotools.types.base.module)
    alias(libs.plugins.kotlin.multiplatform)
    `maven-publish`
    signing
    alias(libs.plugins.kotools.types.kotlin.multiplatform)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotools.types.documentation.module)
    alias(libs.plugins.kotools.types.publication)
}

documentation.excludeFromParentApiReference = true

dependencies.commonMainImplementation(libs.kotlinx.serialization.core)
