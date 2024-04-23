rootProject.name = "types"

pluginManagement {
    val gradlePlugins: File = rootDir.resolve("gradle/plugins")
    includeBuild(gradlePlugins)
}

// -------------------------------- Subprojects --------------------------------

private val subprojects: File = rootDir.resolve("subprojects")

private val internal: String = "internal"
include("${rootProject.name}-$internal")
project(":${rootProject.name}-$internal").projectDir =
    subprojects.resolve(internal)

private val kotlinxSerialization: String = "kotlinx-serialization"
include("${rootProject.name}-$kotlinxSerialization")
project(":${rootProject.name}-$kotlinxSerialization").projectDir =
    subprojects.resolve(kotlinxSerialization)

include(rootProject.name)
project(":${rootProject.name}").projectDir = subprojects.resolve("library")

private val samples: String = "samples"
include(samples)
project(":$samples").projectDir = subprojects.resolve(samples)
