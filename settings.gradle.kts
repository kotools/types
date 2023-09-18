rootProject.name = "kotools-types"

val modulesDir: File = rootDir.resolve("modules")
includeBuild(modulesDir.resolve("build-logic"))
"library".let {
    include(it)
    project(":$it").projectDir = modulesDir.resolve(it)
}
