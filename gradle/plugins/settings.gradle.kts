rootProject.name = "gradle-plugins"

dependencyResolutionManagement.versionCatalogs.register("libs") {
    val files: ConfigurableFileCollection = files("../libs.versions.toml")
    from(files)
}
