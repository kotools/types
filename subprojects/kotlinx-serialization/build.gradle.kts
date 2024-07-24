import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    `maven-publish`
    signing
    alias(libs.plugins.kotools.types.dev.tasks)
    alias(libs.plugins.kotools.types.kotlin.multiplatform)
    alias(libs.plugins.kotools.types.documentation)
    alias(libs.plugins.kotools.samples)
    alias(libs.plugins.kotools.types.publication)
}

// ----------------------------- Plugin extensions -----------------------------

apiValidation.apiDumpDirectory = "src/api"

devTasks.list(tasks.apiDump, tasks.checkJs, tasks.checkJvm)

documentation.packages = layout.projectDirectory.file("packages.md").asFile

kotlin.sourceSets.configureEach {
    languageSettings.optIn("kotools.types.internal.InternalKotoolsTypesApi")
}

// ------------------------------- Dependencies --------------------------------

dependencies {
    commonMainApi(libs.kotlinx.serialization.core)

    commonMainImplementation(platform(libs.kotlin.bom))
    commonMainImplementation(project(":types"))
    commonMainImplementation(project(":types-internal"))

    commonSampleImplementation(libs.kotlin.test.common)
    commonSampleImplementation(libs.kotlin.test.common.annotations)
    commonSampleImplementation(libs.kotlinx.serialization.json)

    jvmTestImplementation(libs.kotlin.test.junit5)

    jsTestImplementation(libs.kotlin.test.js)
}

// ----------------------------------- Tasks -----------------------------------

tasks.withType<AbstractDokkaLeafTask>().configureEach {
    dokkaSourceSets.configureEach {
        val url = "https://kotlinlang.org/api/kotlinx.serialization/"
        externalDocumentationLink(url)
    }
}
