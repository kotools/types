package kotools.types.base

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.kotlin.dsl.apply
import org.gradle.language.base.plugins.LifecycleBasePlugin

/** Plugin applying common configurations to projects. */
public class BasePlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        project.apply<BasePlugin>()
        val recommendation = TaskRecommendation(project.tasks)
        recommendation += listOf(
            LifecycleBasePlugin.ASSEMBLE_TASK_NAME,
            LifecycleBasePlugin.BUILD_TASK_NAME,
            LifecycleBasePlugin.CHECK_TASK_NAME,
            LifecycleBasePlugin.CLEAN_TASK_NAME
        )
    }
}
