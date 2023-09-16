rootProject.name = "types"

val gradlePluginsDir: File = rootDir.resolve("gradle/plugins")
includeBuild(gradlePluginsDir)

include("library")
