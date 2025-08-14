// ------------------------------- Root project --------------------------------

rootProject.name = "gradle-plugins"

// ----------------------------- Version catalogs ------------------------------

dependencyResolutionManagement.versionCatalogs.register("libs") {
    val files: ConfigurableFileCollection = files("../libs.versions.toml")
    from(files)
}
