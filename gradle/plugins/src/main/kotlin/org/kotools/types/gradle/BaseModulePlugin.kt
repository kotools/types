package org.kotools.types.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.diagnostics.TaskReportTask
import org.gradle.kotlin.dsl.named
import org.kotools.types.gradle.internal.TaskGroup

/** Gradle convention plugin that configures the basics of a module. */
public class BaseModulePlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        project.group = "org.kotools"
        project.repositories.mavenCentral()
        project.tasks.named<TaskReportTask>("tasks")
            .configure { this.displayGroup = TaskGroup.Module.toString() }
        project.tasks.register("coordinates").configure {
            this.description =
                "Prints the coordinates (group, name and version)."
            this.group = TaskGroup.Module.toString()
            this.doLast {
                println("${project.group}:${project.name}:${project.version}")
            }
        }
        project.pluginManager.withPlugin("base") {
            val tasks: Set<String> =
                setOf("clean", "check", "assemble", "build")
            project.tasks.named(tasks::contains)
                .configureEach { this.group = TaskGroup.Module.toString() }
        }
    }
}
