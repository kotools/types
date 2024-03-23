rootProject.name = "types5"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement.versionCatalogs.register("libs") {
    val files: ConfigurableFileCollection = files("libs.versions.toml")
    from(files)
}

include("library", "samples")
