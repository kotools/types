plugins {
    id("org.kotools.types.gradle.plugins.base")
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.dokka)
    id("org.kotools.types.documentation")
}

private val projectCommercialName: String = "Kotools Types"

documentation.moduleName = projectCommercialName

dependencies.dokkaHtmlMultiModulePlugin(libs.dokka.versioning)

tasks.register("cleanAll").configure {
    description = "Deletes all build directories."
    group = "recommended"
    allprojects.map { it.tasks.named("clean") }
        .let(this::setDependsOn)
}

tasks.register<Exec>("tag").configure {
    description = "Creates a Git annotated tag for the current version."
    group = "release"
    val projectVersion = "$version"
    val gitmoji = "\uD83D\uDD16"
    val tagMessage = "$gitmoji $projectCommercialName $projectVersion"
    setCommandLine("git", "tag", projectVersion, "-s", "-m", tagMessage)
}

allprojects {
    group = "org.kotools"
    repositories.mavenCentral()
}
