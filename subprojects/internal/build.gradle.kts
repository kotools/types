@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    `maven-publish`
    signing
    id("kotools.types.base")
    id("kotools.types.multiplatform")
    id("kotools.types.publication")
}

group = "org.kotools"

repositories.mavenCentral()

publishing.publications.named<MavenPublication>("kotlinMultiplatform")
    .configure {
        groupId = "${project.group}"
        artifactId = "${rootProject.name}-${project.name}"
        version = "${project.version}"
    }

dependencies {
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonTestImplementation(libs.kotlin.test)
}
