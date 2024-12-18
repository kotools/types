allprojects {
    group = "org.kotools"
    repositories.mavenCentral()
}

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotools.types.documentation)
}

private val projectCommercialName: String = "Kotools Types"

documentation.moduleName = projectCommercialName

dependencies.dokkaHtmlMultiModulePlugin(libs.dokka.versioning)

private val tag: TaskProvider<Exec> by tasks.registering(Exec::class) {
    description = "Creates a Git annotated tag for the current version."
    group = "release"
    val projectVersion = "$version"
    val gitmoji = "\uD83D\uDD16"
    val tagMessage = "$gitmoji $projectCommercialName $projectVersion"
    setCommandLine("git", "tag", projectVersion, "-s", "-m", tagMessage)
}
