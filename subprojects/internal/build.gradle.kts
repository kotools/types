plugins {
    alias(libs.plugins.convention.base.module)
    alias(libs.plugins.convention.kotlin.multiplatform)
    alias(libs.plugins.kotools.compatibility)
    alias(libs.plugins.convention.documentation.module)
    alias(libs.plugins.convention.publication.module)
}

documentation.excludeFromParentApiReference = true

dependencies.commonMainImplementation(libs.kotlinx.serialization.core)
