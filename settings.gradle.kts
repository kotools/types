// ------------------------------- Root project --------------------------------

rootProject.name = "types"

// -------------------------------- Subprojects --------------------------------

private fun subproject(
    path: String,
    name: String = "${rootProject.name}-$path"
) {
    include(name)
    project(":$name").projectDir = rootDir.resolve("subprojects/$path")
}

subproject("internal")
subproject("kotlinx-serialization")
subproject(path = "library", name = rootProject.name)

// ------------------------------ Included builds ------------------------------

private val gradlePluginsDirectory: File = rootDir.resolve("gradle/plugins")
includeBuild(gradlePluginsDirectory)
