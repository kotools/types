package convention.publication

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
    group = "documentation"
    dependsOn(archiveApiReference, setCurrentApiReference)
}

// ------------------- Documentation Root Plugin integration -------------------

pluginManager.apply(DocumentationRootPlugin::class)

private val dokkaHtmlMultiModule: TaskProvider<DokkaMultiModuleTask> =
    tasks.named<DokkaMultiModuleTask>("dokkaHtmlMultiModule")

archiveApiReference.configure { dependsOn += dokkaHtmlMultiModule }
setCurrentApiReference.configure { dependsOn += dokkaHtmlMultiModule }
