import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins {
    alias(libs.plugins.convention.base.module)
    alias(libs.plugins.convention.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotools.compatibility)
    alias(libs.plugins.convention.documentation.module)
    alias(libs.plugins.kotools.samples.multiplatform)
    alias(libs.plugins.convention.publication.module)
    alias(libs.plugins.nmcp)
}

apiValidation.apiDumpDirectory = "src/api"

compatibility {
    java = libs.versions.java
    kotlin = libs.versions.kotlin
}

documentation.packages = layout.projectDirectory.file("packages.md").asFile

kotlin.jvm(KotlinJvmTarget::withJava)
kotlin.sourceSets.configureEach {
    languageSettings.optIn("org.kotools.types.internal.InternalKotoolsTypesApi")
}

dependencies {
    commonMainImplementation(project(":types-internal"))
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonTestImplementation(libs.kotlin.test)
    commonTestImplementation(libs.kotlinx.serialization.json)

    nativeMainImplementation(libs.bignum)
}
