rootProject.name = "gradle-plugins"

dependencyResolutionManagement.versionCatalogs.register("libs") {
    val files: ConfigurableFileCollection = files("../libs.versions.toml")
    from(files)
}

gradle.parent?.let {
    gradle.startParameter.isDryRun = it.startParameter.isDryRun
}
