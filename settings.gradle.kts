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
