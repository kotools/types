import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl

plugins {
    val kotlinVersion = "1.5.31"
    kotlin("multiplatform") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("org.jetbrains.dokka") version kotlinVersion
}

group = "kotools"
version = "4.0.0-SNAPSHOT"

repositories(RepositoryHandler::mavenCentral)

dependencies {
    // Kotlin
    commonMainImplementation(platform(kotlin("bom")))
    commonTestImplementation(kotlin("test"))

    // Serialization
    fun serialization(module: String): String =
        "org.jetbrains.kotlinx:kotlinx-serialization-$module:1.3.1"
    commonMainImplementation(serialization("core"))
    commonTestImplementation(serialization("json"))
}

kotlin {
    explicitApi = ExplicitApiMode.Strict
    jvm {
        compilations.all { kotlinOptions.jvmTarget = "1.8" }
        testRuns["test"].executionTask { useJUnitPlatform() }
    }
    js(IR, KotlinJsTargetDsl::browser)
    linuxX64()
    macosX64()
    mingwX64()
}
