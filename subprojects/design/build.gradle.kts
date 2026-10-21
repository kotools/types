plugins {
    alias(libs.plugins.convention.base.module)
    alias(libs.plugins.convention.kotlin.multiplatform)
    alias(libs.plugins.kotools.compatibility)
}

compatibility {
    java = libs.versions.java
    kotlin = libs.versions.kotlin
}
