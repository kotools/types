plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
    id("org.kotools.types.base")
    id("kotools.types.multiplatform")
    id("kotools.types.publication")
    id("org.kotools.types.samples")
}

apiValidation.apiDumpDirectory = "src/api"

publishing.publications.named<MavenPublication>("kotlinMultiplatform")
    .configure {
        groupId = "${project.group}"
        artifactId = project.name
        version = "${project.version}"
    }

samples.project = project(":samples")

dependencies {
    commonMainImplementation(platform(libs.kotlin.bom))
    commonMainImplementation(project(":types"))
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonTestImplementation(libs.kotlin.test)
    commonTestImplementation(libs.kotlinx.serialization.json)
}
