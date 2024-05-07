package org.kotools.types.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.diagnostics.TaskReportTask
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register

/** Gradle convention plugin for listing development tasks. */
public class DevTasksPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        val extension: DevTasksExtension = project.extensions.create("devTasks")
        project.tasks.register<TaskReportTask>("devTasks").configure {
            this.description = "Displays the recommended tasks for development."
            val evaluatedGroupName: String = extension.groupName.get()
            this.group = evaluatedGroupName
            this.displayGroup = evaluatedGroupName
        }
    }
}
