plugins {
    alias(libs.plugins.convention.base.module)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotools.types.kotlin.multiplatform)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotools.types.documentation.module)
    alias(libs.plugins.convention.publication.module)
}

documentation.excludeFromParentApiReference = true

dependencies.commonMainImplementation(libs.kotlinx.serialization.core)
