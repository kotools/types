rootProject.name = "types"

pluginManagement { includeBuild(rootDir.resolve("modules/build-logic")) }

dependencyResolutionManagement.repositories.mavenCentral()

"library".let {
    include(it)
    project(":$it").projectDir = rootDir.resolve("modules/$it")
}
