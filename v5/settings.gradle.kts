rootProject.name = "types5"

dependencyResolutionManagement.versionCatalogs.register("libs") {
    val files: ConfigurableFileCollection = files("libs.versions.toml")
    from(files)
}

include("samples")
