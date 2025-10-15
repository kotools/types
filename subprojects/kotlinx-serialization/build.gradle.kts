plugins {
    alias(libs.plugins.convention.base.module)
    alias(libs.plugins.convention.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.convention.documentation.module)
    alias(libs.plugins.kotools.samples.multiplatform)
    alias(libs.plugins.convention.publication.module)
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
