import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
    id("kotools.types.multiplatform")
    id("kotools.types.documentation")
    id("kotools.types.publication")
    id("org.kotools.types.samples")
}

group = "org.kotools"

repositories.mavenCentral()

// ----------------------------- Plugin extensions -----------------------------

apiValidation.apiDumpDirectory = "src/api"

documentation {
    license = layout.projectDirectory.file("LICENSE.txt").asFile
    logo = layout.projectDirectory.file("dokka/logo-icon.svg").asFile
    moduleName = "Kotools Types"
}

publishing.publications.named<MavenPublication>("kotlinMultiplatform")
    .configure {
        groupId = "${project.group}"
        artifactId = project.name
        version = "${project.version}"
    }

project(":samples")
    .layout
    .projectDirectory
    .dir("src/main")
    .let(samples.source::set)

// ------------------------------- Dependencies --------------------------------

dependencies {
    commonMainImplementation(platform(libs.kotlin.bom))
    commonMainImplementation(project(":types-internal"))
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonTestImplementation(libs.kotlin.test)
    commonTestImplementation(libs.kotlinx.serialization.json)

    dokkaHtmlPlugin(libs.dokka.versioning)
}

// ----------------------------------- Tasks -----------------------------------

tasks.register("unit")

tasks.jsBrowserTest.configure { useMocha() }

tasks.withType<DokkaTask>().configureEach {
    failOnWarning.set(true)
    dokkaSourceSets.configureEach {
        includes.setFrom("dokka/packages.md")
        reportUndocumented.set(true)
    }
}
