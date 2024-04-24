import org.jetbrains.dokka.gradle.AbstractDokkaLeafTask
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    id("org.kotools.types.gradle.plugins.base")
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
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

documentation.packages = layout.projectDirectory.file("packages.md").asFile

kotlin.sourceSets {
    all {
        languageSettings.optIn("kotools.types.internal.InternalKotoolsTypesApi")
    }
    val commonMain: KotlinSourceSet by getting
    val jvmAndNativeMain: KotlinSourceSet by creating { dependsOn(commonMain) }
    val jvmMain: KotlinSourceSet by getting { dependsOn(jvmAndNativeMain) }
    val nativeMain: KotlinSourceSet by creating { dependsOn(jvmAndNativeMain) }
    val linuxMain: KotlinSourceSet by getting { dependsOn(nativeMain) }
    val macosMain: KotlinSourceSet by getting { dependsOn(nativeMain) }
    val macosArm64Main: KotlinSourceSet by getting { dependsOn(nativeMain) }
    val windowsMain: KotlinSourceSet by getting { dependsOn(nativeMain) }
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

tasks.compileTestDevelopmentExecutableKotlinJs.configure {
    dependsOn += tasks.compileTestKotlinJs
}
