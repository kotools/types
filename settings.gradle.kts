rootProject.name = "types"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

private val gradlePluginsDir: File = rootDir.resolve("gradle/plugins")
includeBuild(gradlePluginsDir)

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

include("types5")
project(":types5").projectDir = rootDir.resolve("v5")

private val v5Samples: String = "v5-samples"
include(v5Samples)
project(":$v5Samples").projectDir = v5Samples.replace('-', '/')
    .let(rootDir::resolve)
