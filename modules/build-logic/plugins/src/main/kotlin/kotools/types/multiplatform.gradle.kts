package kotools.types

import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

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
        testRuns["test"].executionTask { useJUnitPlatform() }
    }
    linuxX64("linux")
    macosX64("macos")
    mingwX64("windows")
}

rootProject.plugins.withType<YarnPlugin> {
    rootProject.the<YarnRootExtension>().lockFileDirectory = projectDir
}

tasks {
    val test: TaskProvider<Task> = register("test") {
        group(TaskGroup.LIFECYCLE)
        description = "Tests this project on the JVM platform."
        val jvmTest: Task = findByName("jvmTest")
            ?: error("Can't find the 'jvmTest' task.")
        dependsOn(jvmTest)
    }
    check { dependsOn += test }
}
