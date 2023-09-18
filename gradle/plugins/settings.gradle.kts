dependencyResolutionManagement.versionCatalogs.create("libs") {
    val files: ConfigurableFileCollection = files("../libs.versions.toml")
    from(files)
}
