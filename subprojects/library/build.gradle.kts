import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
    id("kotools.types.multiplatform")
    id("org.kotools.types.documentation")
    id("kotools.types.publication")
    id("org.kotools.types.samples")
}

// ----------------------------- Plugin extensions -----------------------------

apiValidation.apiDumpDirectory = "src/api"

documentation {
    moduleName = "Kotools Types"
    packages = layout.projectDirectory.file("packages.md").asFile
}

kotlin.sourceSets {
    all {
        languageSettings.optIn("kotools.types.internal.InternalKotoolsTypesApi")
    }
    val commonTest: KotlinSourceSet by getting
    val nativeTest: KotlinSourceSet by creating { dependsOn(commonTest) }
    val linuxTest: KotlinSourceSet by getting { dependsOn(nativeTest) }
    val macosTest: KotlinSourceSet by getting { dependsOn(nativeTest) }
    val macosArm64Test: KotlinSourceSet by getting { dependsOn(nativeTest) }
    val windowsTest: KotlinSourceSet by getting { dependsOn(nativeTest) }
}

publishing.publications.named<MavenPublication>("kotlinMultiplatform")
    .configure {
        groupId = "${project.group}"
        artifactId = project.name
        version = "${project.version}"
    }

samples.project = project(":samples")

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

// ----------------------------------- Tasks -----------------------------------

tasks.compileTestDevelopmentExecutableKotlinJs.configure {
    dependsOn += tasks.compileTestKotlinJs
}
