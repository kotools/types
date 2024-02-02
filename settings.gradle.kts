rootProject.name = "types"

val gradlePluginsDir: File = rootDir.resolve("gradle/plugins")
includeBuild(gradlePluginsDir)

// -------------------------------- Subprojects --------------------------------

val subprojects: File = rootDir.resolve("subprojects")

val internal: String = "internal"
include("${rootProject.name}-$internal")
project(":${rootProject.name}-$internal").projectDir =
    subprojects.resolve(internal)

val javaCompatibility: String = "java-compatibility"
include(javaCompatibility)
project(":$javaCompatibility").projectDir =
    subprojects.resolve(javaCompatibility)
