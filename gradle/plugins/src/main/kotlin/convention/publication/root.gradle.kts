package convention.publication

import convention.base.TaskGroup
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

// ------------------------------- API reference -------------------------------

private val apiReferenceBuildDirectory: Provider<Directory> =
    layout.buildDirectory.dir("api-reference")

private val apiReferenceArchiveDirectory: Directory =
    layout.projectDirectory.dir("documentation/api-reference")

private val dokkaHtmlMultiModule: TaskProvider<DokkaMultiModuleTask> =
    tasks.named<DokkaMultiModuleTask>("dokkaHtmlMultiModule")

private val archiveApiReference: TaskProvider<Copy> by tasks
    .registering(Copy::class) {
        from(apiReferenceBuildDirectory) { exclude("older/**") }
        into(apiReferenceArchiveDirectory.dir("archive/${project.version}"))
        dependsOn(dokkaHtmlMultiModule)
    }

private val setCurrentApiReference: TaskProvider<Copy> by tasks
    .registering(Copy::class) {
        from(apiReferenceBuildDirectory)
        into(apiReferenceArchiveDirectory.dir("current"))
        dependsOn(dokkaHtmlMultiModule)
    }

tasks.register("publishApiReference").configure {
    description = "Publishes the API reference in the root project directory."
    group = TaskGroup.Root.toString()
    dependsOn(archiveApiReference, setCurrentApiReference)
}

// ------------------------------------ Git ------------------------------------

tasks.register<Exec>("tag").configure {
    description = "Creates a Git annotated tag for the current version."
    group = TaskGroup.Root.toString()
    val gitmoji = "\uD83D\uDD16"
    val moduleName = "Kotools Types"
    val version = "$version"
    val tagMessage = "$gitmoji $moduleName $version"
    setCommandLine("git", "tag", version, "-s", "-m", tagMessage)
}

// ---------------------------- Sources publication ----------------------------

private val zipSources: TaskProvider<Zip> by tasks.registering(Zip::class) {
    archiveBaseName = "kotools-types"
    archiveVersion = project.version.toString()
    from(layout.buildDirectory.dir("maven/"))
    subprojects
        .map { it.tasks.named("publishAllPublicationsToProjectRepository") }
        .let(::setDependsOn)
}

private val assembleSources: TaskProvider<Task> by tasks.registering {
    description = "Assembles the sources of the subprojects."
    group = TaskGroup.CI.name
    dependsOn(zipSources)
}

tasks.named { it == "assemble" }
    .configureEach { dependsOn(assembleSources) }
