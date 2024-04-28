import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask

plugins {
    id("org.kotools.types.gradle.tasks")
    id("org.kotools.types.gradle.kotlin.multiplatform")
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
    id("org.kotools.types.documentation")
    id("kotools.types.publication")
    id("org.kotools.types.samples")
}

// ----------------------------- Plugin extensions -----------------------------

apiValidation.apiDumpDirectory = "src/api"

devTasks.list(tasks.apiCheck, tasks.apiDump, tasks.checkJs, tasks.checkJvm)

documentation.packages = layout.projectDirectory.file("packages.md").asFile

kotlin.sourceSets.configureEach {
    languageSettings.optIn("kotools.types.internal.InternalKotoolsTypesApi")
}

publishing.publications.named<MavenPublication>("kotlinMultiplatform")
    .configure {
        groupId = "${project.group}"
        artifactId = project.name
        version = "${project.version}"
    }

samples {
    project = project(":samples")
    sourcesWithoutInlinedSamples()
}

// ------------------------------- Dependencies --------------------------------

dependencies {
    commonMainImplementation(platform(libs.kotlin.bom))
    commonMainImplementation(project(":types"))
    commonMainImplementation(project(":types-internal"))
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonTestImplementation(libs.kotlin.test.common)
    commonTestImplementation(libs.kotlin.test.common.annotations)
    commonTestImplementation(libs.kotlinx.serialization.json)

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
