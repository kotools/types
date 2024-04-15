plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
    id("kotools.types.multiplatform")
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

    commonTestImplementation(libs.kotlin.test)
}

tasks.compileTestDevelopmentExecutableKotlinJs.configure {
    dependsOn += tasks.compileTestKotlinJs
}
