plugins {
    id("org.kotools.types.gradle.plugins.kotlin.multiplatform")
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
    id("org.kotools.types.documentation")
    id("kotools.types.publication")
}

documentation.excludeFromParentApiReference = true

publishing.publications.named<MavenPublication>("kotlinMultiplatform")
    .configure {
        groupId = "${project.group}"
        artifactId = project.name
        version = "${project.version}"
    }

dependencies {
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonTestImplementation(libs.kotlin.test.common)
    commonTestImplementation(libs.kotlin.test.common.annotations)

    jvmTestImplementation(libs.kotlin.test.junit5)

    jsTestImplementation(libs.kotlin.test.js)
}
