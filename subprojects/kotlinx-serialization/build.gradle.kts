plugins {
    alias(libs.plugins.kotools.types.base.module)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    `maven-publish`
    signing
    alias(libs.plugins.kotools.types.kotlin.multiplatform)
    alias(libs.plugins.convention.documentation.module)
    alias(libs.plugins.kotools.samples.multiplatform)
    alias(libs.plugins.kotools.types.publication)
}

apiValidation.apiDumpDirectory = "src/api"

documentation {
    externalLinks = listOf("https://kotlinlang.org/api/kotlinx.serialization/")
    packages = layout.projectDirectory.file("packages.md").asFile
}

kotlin.sourceSets.configureEach {
    languageSettings.optIn("org.kotools.types.internal.InternalKotoolsTypesApi")
}

dependencies {
    commonMainApi(libs.kotlinx.serialization.core)

    commonMainImplementation(project(":types"))
    commonMainImplementation(project(":types-internal"))

    commonTestImplementation(libs.kotlin.test)
    commonTestImplementation(libs.kotlinx.serialization.json)
}
