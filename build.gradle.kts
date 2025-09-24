allprojects {
    this.group = "org.kotools"
    this.repositories.mavenCentral()
}

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.convention.documentation.root)
    alias(libs.plugins.convention.publication.root)
}

documentation.moduleName = "Kotools Types"

dependencies.dokkaHtmlMultiModulePlugin(libs.dokka.versioning)

tasks.register("checkAll").configure {
    this.description = "Checks all projects."
    this.group = "verification"
    this.project.subprojects.mapNotNull { it?.tasks?.findByName("check") }
        .let(this::setDependsOn)
}

tasks.register<Exec>("tag").configure {
    this.description = "Creates a Git annotated tag for the current version."
    this.group = "release"
    val gitmoji = "\uD83D\uDD16"
    val moduleName: String = documentation.moduleName.get()
    val version: String = this.project.version.toString()
    val tagMessage = "$gitmoji $moduleName $version"
    this.setCommandLine("git", "tag", version, "-s", "-m", tagMessage)
}
