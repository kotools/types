package kotools.types

plugins {
    id("kotools.types.base")
    kotlin("multiplatform")
}

kotlin.jvm {
    compilations.configureEach { kotlinOptions.jvmTarget = "17" }
    testRuns["test"].executionTask { useJUnitPlatform() }
}

tasks.allMetadataJar { group(TaskGroup.OTHER) }
tasks.buildDependents { group(TaskGroup.OTHER) }
tasks.buildKotlinToolingMetadata { group(TaskGroup.OTHER) }
tasks.buildNeeded { group(TaskGroup.OTHER) }
kotlin.targets.forEach {
    tasks.findByName("${it.name}Jar")?.group(TaskGroup.OTHER)
    tasks.findByName("${it.name}MainClasses")?.group(TaskGroup.OTHER)
    tasks.findByName("${it.name}TestClasses")?.group(TaskGroup.OTHER)
}
