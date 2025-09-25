package convention.publication

import convention.base.TaskGroup
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

private val dokkaHtmlMultiModule: TaskProvider<DokkaMultiModuleTask> =
    tasks.named<DokkaMultiModuleTask>("dokkaHtmlMultiModule")

private val apiReferenceBuildDirectory: Provider<Directory> =
    layout.buildDirectory.dir("api-reference")

private val archiveParentDirectory: Directory =
    layout.projectDirectory.dir("documentation/api-reference")

private val archiveApiReference: TaskProvider<Copy> by tasks
    .registering(Copy::class) {
        description = "Archives the API reference in the project directory."
        group = TaskGroup.Documentation.toString()
        from(apiReferenceBuildDirectory) { exclude("older/**") }
        into(archiveParentDirectory.dir("archive/${project.version}"))
        dependsOn += dokkaHtmlMultiModule
    }

private val setCurrentApiReference: TaskProvider<Copy> by tasks
    .registering(Copy::class) {
        description = "Sets the current API reference in the project directory."
        group = TaskGroup.Documentation.toString()
        from(apiReferenceBuildDirectory)
        into(archiveParentDirectory.dir("current"))
        dependsOn += dokkaHtmlMultiModule
    }

private val publishApiReference: TaskProvider<Task> by tasks.registering
publishApiReference.configure {
    description = "Publishes the API reference in the root project directory."
    group = TaskGroup.Root.toString()
    dependsOn(archiveApiReference, setCurrentApiReference)
}

private val tag: TaskProvider<Exec> by tasks.registering(Exec::class)
tag.configure {
    description = "Creates a Git annotated tag for the current version."
    group = TaskGroup.Root.toString()
    val gitmoji = "\uD83D\uDD16"
    val moduleName = "Kotools Types"
    val version = "$version"
    val tagMessage = "$gitmoji $moduleName $version"
    this.setCommandLine("git", "tag", version, "-s", "-m", tagMessage)
}
