package org.kotools.types.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

/** Gradle plugin for configuring a Kotlin JVM project. */
public class KotlinJvmPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        project.pluginManager.apply("org.jetbrains.kotlin.jvm")
        project.extensions.getByType<KotlinJvmProjectExtension>()
            .explicitApi()
        val taskManager = KotlinJvmTaskManager(project)
        taskManager.configureKotlinCompile()
        taskManager.configureTest()
    }
}
