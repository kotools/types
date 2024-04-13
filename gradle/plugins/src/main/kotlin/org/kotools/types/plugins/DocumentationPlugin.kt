package org.kotools.types.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskProvider
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask

/** Plugin configuring the documentation of Kotools Types. */
public class DocumentationPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        val documentation = DocumentationExtension(project)
        val tasks = DocumentationTasks(project)
        val cleanApiReference: TaskProvider<Delete> =
            tasks.cleanApiReference(documentation)
        val dokkaHtmlMultiModule: TaskProvider<DokkaMultiModuleTask> =
            tasks.dokkaHtmlMultiModule(documentation)
        dokkaHtmlMultiModule.configure { dependsOn += cleanApiReference }
        tasks.apiReferenceMultiModule()
            .configure { dependsOn += dokkaHtmlMultiModule }
    }
}
