import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
    id("org.kotools.types.base")
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
    val jvmAndNativeMain: KotlinSourceSet by creating {
        commonMain.get()
            .let(this::dependsOn)
    }
    val jvmAndNativeTest: KotlinSourceSet by creating {
        commonTest.get()
            .let(this::dependsOn)
    }
    jvmMain.get()
        .dependsOn(jvmAndNativeMain)
    jvmTest.get()
        .dependsOn(jvmAndNativeTest)
    val nativeMain: KotlinSourceSet by creating { dependsOn(jvmAndNativeMain) }
    val nativeTest: KotlinSourceSet by creating { dependsOn(jvmAndNativeTest) }
    listOf(linuxMain, macosMain, macosArm64Main, windowsMain)
        .map(NamedDomainObjectProvider<KotlinSourceSet>::get)
        .forEach { it.dependsOn(nativeMain) }
    listOf(linuxTest, macosTest, macosArm64Test, windowsTest)
        .map(NamedDomainObjectProvider<KotlinSourceSet>::get)
        .forEach { it.dependsOn(nativeTest) }
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
    commonMainImplementation(project(":types"))
    commonMainImplementation(project(":types-internal"))
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonTestImplementation(libs.kotlin.test)
    commonTestImplementation(libs.kotlinx.serialization.json)
}

// ----------------------------------- Tasks -----------------------------------

tasks.compileTestKotlinJs.configure {
    dependsOn += tasks.compileTestDevelopmentExecutableKotlinJs
}
