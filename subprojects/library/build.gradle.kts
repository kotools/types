plugins {
    alias(libs.plugins.kotools.types.dev.tasks)
    alias(libs.plugins.kotools.types.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotools.types.documentation)
    alias(libs.plugins.kotools.types.samples)
    `maven-publish`
    signing
    alias(libs.plugins.kotools.types.publication)
}

// ----------------------------- Plugin extensions -----------------------------

apiValidation.apiDumpDirectory = "src/api"

devTasks.list(tasks.apiDump, tasks.checkJs, tasks.checkJvm)

documentation {
    moduleName = "Kotools Types"
    packages = layout.projectDirectory.file("packages.md").asFile
}

kotlin.sourceSets.configureEach {
    languageSettings.optIn("kotools.types.internal.InternalKotoolsTypesApi")
}

samples {
    project = project(":samples")
    sourcesWithoutInlinedSamples()
}

// ------------------------------- Dependencies --------------------------------

dependencies {
    commonMainImplementation(platform(libs.kotlin.bom))
    commonMainImplementation(project(":types-internal"))
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonTestImplementation(libs.kotlin.test.common)
    commonTestImplementation(libs.kotlin.test.common.annotations)
    commonTestImplementation(libs.kotlinx.serialization.json)

    jvmTestImplementation(libs.kotlin.test.junit5)

    jsTestImplementation(libs.kotlin.test.js)
}
