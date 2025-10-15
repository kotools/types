import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins {
    alias(libs.plugins.kotools.types.base.module)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotools.types.kotlin.multiplatform)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotools.types.documentation.module)
    alias(libs.plugins.kotools.samples.multiplatform)
    alias(libs.plugins.convention.publication.module)
}

apiValidation.apiDumpDirectory = "src/api"

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
}
