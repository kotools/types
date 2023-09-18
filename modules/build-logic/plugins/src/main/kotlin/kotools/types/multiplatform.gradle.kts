package kotools.types

plugins {
    id("kotools.types.base")
    kotlin("multiplatform")
}

kotlin.jvm {
    compilations.configureEach { kotlinOptions.jvmTarget = "17" }
    testRuns["test"].executionTask { useJUnitPlatform() }
}
