rootProject.name = "gradle-plugins"

@Suppress("UnstableApiUsage")
dependencyResolutionManagement.versionCatalogs.register("libs") {
    val files: ConfigurableFileCollection = files("../libs.versions.toml")
    from(files)
}
