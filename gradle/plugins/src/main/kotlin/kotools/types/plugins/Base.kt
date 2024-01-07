package kotools.types.plugins

import kotools.types.tasks.PrintTask
import kotools.types.tasks.TaskGroup
import kotools.types.tasks.description
import kotools.types.tasks.group
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

/** Plugin defining common conventions for Kotools Types projects. */
public class BasePlugin : Plugin<Project> {
    /** Applies this plugin to the given [project]. */
    override fun apply(project: Project): Unit = project.tasks
        .register<PrintTask>("version")
        .configure {
            this.group(TaskGroup.HELP)
            this.description("Displays the project's version.")
            this.message.set(project.version)
        }
}
