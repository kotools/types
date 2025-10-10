package org.kotools.types.gradle

import convention.base.TaskGroup
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.diagnostics.TaskReportTask
import org.gradle.kotlin.dsl.named

/** Gradle convention plugin that configures the basics of a root project. */
public class BaseRootPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        project.repositories.mavenCentral()
        project.tasks.named<TaskReportTask>("tasks")
            .configure { this.displayGroup = TaskGroup.Root.toString() }
        project.withBasePlugin()
    }
}

private fun Project.withBasePlugin(): Unit = this.pluginManager
    .withPlugin("base") {
        val tasks: Set<String> = setOf("clean", "check", "assemble", "build")
        this@withBasePlugin.tasks.named(tasks::contains)
            .configureEach { this.group = TaskGroup.Root.toString() }
    }
