package org.kotools.types.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

/** Gradle convention plugin for configuring Kotlin Multiplatform projects. */
public class KotlinMultiplatformPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        val plugins = PluginManager(project)
        plugins += PluginIdentifier.KotlinMultiplatform
        val extensions = KotlinMultiplatformExtensionManager(project)
        extensions.kotlinMultiplatform()
        extensions.publishing()
        val tasks = KotlinMultiplatformTaskManager(project)
        tasks.compileTestDevelopmentExecutableKotlinJs()
        tasks.checkJs()
        tasks.checkJvm(plugins)
        tasks.jars()
    }
}
