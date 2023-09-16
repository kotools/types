package kotools.types

plugins {
    id("kotools.types.base")
    kotlin("multiplatform")
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

dependencies { commonMainImplementation(platform(kotlin("bom"))) }
