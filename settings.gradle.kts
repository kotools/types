rootProject.name = "types"

private val gradlePluginsDirectory: File = rootDir.resolve("gradle/plugins")
includeBuild(gradlePluginsDirectory)

// -------------------------------- Subprojects --------------------------------

private val subprojects: File = rootDir.resolve("subprojects")

private val internal: String = "internal"
include("${rootProject.name}-$internal")
project(":${rootProject.name}-$internal").projectDir =
    subprojects.resolve(internal)

private val javaCompatibility: String = "java-compatibility"
include(javaCompatibility)
project(":$javaCompatibility").projectDir =
    subprojects.resolve(javaCompatibility)

private val kotlinxSerialization: String = "kotlinx-serialization"
include("${rootProject.name}-$kotlinxSerialization")
project(":${rootProject.name}-$kotlinxSerialization").projectDir =
    subprojects.resolve(kotlinxSerialization)

include(rootProject.name)
project(":${rootProject.name}").projectDir = subprojects.resolve("library")

private val samples: String = "samples"
include(samples)
project(":$samples").projectDir = subprojects.resolve(samples)
