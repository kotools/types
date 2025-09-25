package org.kotools.types.gradle

import convention.base.TaskGroup
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.diagnostics.TaskReportTask
import org.gradle.kotlin.dsl.named

/** Gradle convention plugin that configures the basics of a root project. */
public class BaseRootPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        project.repositories.mavenCentral()
        project.pluginManager.withPlugin("base") {
            project.tasks.named<TaskReportTask>("tasks")
                .configure { this.displayGroup = TaskGroup.Root.toString() }
            project.tasks.named(BasePlugin.CLEAN_TASK_NAME)
                .configure { this.group = TaskGroup.Root.toString() }
            project.tasks.named("check")
                .configure { this.group = TaskGroup.Root.toString() }
            project.tasks.named(BasePlugin.ASSEMBLE_TASK_NAME)
                .configure { this.group = TaskGroup.Root.toString() }
            project.tasks.named("build")
                .configure { this.group = TaskGroup.Root.toString() }
        }
    }
}
