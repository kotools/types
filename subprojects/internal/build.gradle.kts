plugins {
    alias(libs.plugins.convention.base.module)
    alias(libs.plugins.kotlin.multiplatform)
    `maven-publish`
    signing
    alias(libs.plugins.kotools.types.kotlin.multiplatform)
    alias(libs.plugins.convention.documentation.module)
    alias(libs.plugins.kotools.types.publication)
}

documentation.excludeFromParentApiReference = true

dependencies.commonMainImplementation(libs.kotlinx.serialization.core)
