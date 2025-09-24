package convention.publication

import convention.documentation.DocumentationRootExtension
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import convention.documentation.RootPlugin as DocumentationRootPlugin

// ----------------------------- Script properties -----------------------------

private val apiReferenceBuildDirectory: Provider<Directory> =
    layout.buildDirectory.dir("api-reference")

private val archiveParentDirectory: Directory =
    layout.projectDirectory.dir("documentation/api-reference")

// ----------------------------------- Tasks -----------------------------------

private val archiveApiReference: TaskProvider<Copy> by tasks
    .registering(Copy::class) {
        description = "Archives the API reference in the project directory."
        from(apiReferenceBuildDirectory) { exclude("older/**") }
        into(archiveParentDirectory.dir("archive/${project.version}"))
    }

private val setCurrentApiReference: TaskProvider<Copy> by tasks
    .registering(Copy::class) {
        description = "Sets the current API reference in the project directory."
        from(apiReferenceBuildDirectory)
        into(archiveParentDirectory.dir("current"))
    }

private val publishApiReference: TaskProvider<Task> by tasks.registering
publishApiReference.configure {
    description = "Publishes the API reference in the root project directory."
    group = "publication"
    dependsOn(archiveApiReference, setCurrentApiReference)
}

private val tag: TaskProvider<Exec> by tasks.registering(Exec::class) {
    description = "Creates a Git annotated tag for the current version."
    group = "publication"
}

// ------------------- Documentation Root Plugin integration -------------------

pluginManager.apply(DocumentationRootPlugin::class)

private val documentation: DocumentationRootExtension = extensions.getByType()

private val dokkaHtmlMultiModule: TaskProvider<DokkaMultiModuleTask> =
    tasks.named<DokkaMultiModuleTask>("dokkaHtmlMultiModule")

archiveApiReference.configure { dependsOn += dokkaHtmlMultiModule }
setCurrentApiReference.configure { dependsOn += dokkaHtmlMultiModule }

tag.configure {
    val gitmoji = "\uD83D\uDD16"
    val moduleName: String = documentation.moduleName.get()
    val version = "$version"
    val tagMessage = "$gitmoji $moduleName $version"
    this.setCommandLine("git", "tag", version, "-s", "-m", tagMessage)
}
