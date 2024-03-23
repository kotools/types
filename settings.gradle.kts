rootProject.name = "types"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

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

// ------------------------------ Kotools Types 5 ------------------------------

private val types5 = "types5"
include(types5)
private val v5Directory: File = rootDir.resolve("v5")
project(":$types5").projectDir = v5Directory

private val types5Samples: String = "$types5-samples"
include(types5Samples)
project(":$types5Samples").projectDir = v5Directory.resolve("samples")
