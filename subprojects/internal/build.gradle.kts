plugins {
    alias(libs.plugins.convention.base.module)
    alias(libs.plugins.convention.kotlin.multiplatform)
    alias(libs.plugins.kotools.compatibility)
    alias(libs.plugins.convention.documentation.module)

    // Publication
    `maven-publish`
    signing
    alias(libs.plugins.nmcp)
    alias(libs.plugins.convention.publication.module)
}

compatibility {
    java = libs.versions.java
    kotlin = libs.versions.kotlin
}

documentation.excludeFromParentApiReference = true

dependencies.commonMainImplementation(libs.kotlinx.serialization.core)
