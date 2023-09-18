package kotools.types

import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import org.jetbrains.kotlin.gradle.targets.jvm.tasks.KotlinJvmTest

plugins {
    id("kotools.types.base")
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

kotlin {
    explicitApi()
    js(IR) { browser() }
    jvm {
        compilations.configureEach { kotlinOptions.jvmTarget = "17" }
        testRuns["test"].executionTask.configure { useJUnitPlatform() }
    }
    linuxX64("linux")
    macosX64("macos")
    mingwX64("windows")
}

rootProject.plugins.withType<YarnPlugin>().configureEach {
    rootProject.the<YarnRootExtension>().lockFileDirectory = projectDir
}

tasks.withType<Jar>().configureEach {
    fun key(suffix: String): String = "Implementation-$suffix"
    val name: Pair<String, String> = key("Title") to project.name
    val version: Pair<String, Any> = key("Version") to project.version
    manifest.attributes(name, version)
}

tasks.register<Task>("test") {
    group(TaskGroup.RECOMMENDED)
    description("Tests this project on the JVM platform.")
    dependsOn += tasks.named<KotlinJvmTest>("jvmTest")
}
