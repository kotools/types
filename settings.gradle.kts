rootProject.name = "types"

val buildLogicDir: File = rootDir.resolve("modules/build-logic")
includeBuild(buildLogicDir)

"library".let {
    include(it)
    project(":$it").projectDir = rootDir.resolve("modules/$it")
}
