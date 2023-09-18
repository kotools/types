rootProject.name = "kotools-types"

pluginManagement { includeBuild(rootDir.resolve("modules/build-logic")) }

dependencyResolutionManagement {
    repositories.mavenCentral()
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

"library".let {
    include(it)
    project(":$it").projectDir = rootDir.resolve("modules/$it")
}
