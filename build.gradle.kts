import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
    id("kotools.types.base")
    id("kotools.types.multiplatform")
    id("kotools.types.documentation")
    id("kotools.types.publication")
}

apiValidation.apiDumpDirectory = "src/api"

publishing.publications.named<MavenPublication>("kotlinMultiplatform")
    .configure {
        groupId = "${project.group}"
        artifactId = project.name
        version = "${project.version}"
    }

dependencies {
    commonMainImplementation(platform(libs.kotlin.bom))
    commonMainImplementation(projects.typesInternal)
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonTestImplementation(libs.kotlin.test)
    commonTestImplementation(libs.kotlinx.serialization.json)

    dokkaHtmlPlugin(libs.dokka.versioning)
}

tasks.jsBrowserTest.configure { useMocha() }

tasks.withType<DokkaTask>().configureEach {
    moduleName.set("Kotools Types")
    failOnWarning.set(true)
    dokkaSourceSets.configureEach {
        includes.setFrom("dokka/packages.md")
        reportUndocumented.set(true)
    }
}
