package org.kotools.types.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

/** Gradle plugin for configuring a Kotlin JVM project. */
public class KotlinJvmPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        val pluginManager = PluginManager(project)
        pluginManager.apply(PluginIdentifier.KotlinJvm)
        project.extensions.getByType<KotlinJvmProjectExtension>()
            .explicitApi()
        val taskManager = KotlinJvmTaskManager(project)
        taskManager.configureKotlinCompile()
        taskManager.configureTest()
    }
}
