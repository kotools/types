package org.kotools.types.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.hasPlugin
import kotlin.reflect.KClass

internal class PluginManager(private val project: Project) {
    operator fun plusAssign(identifier: PluginIdentifier): Unit =
        this.project.pluginManager.apply("$identifier")

    fun has(identifier: PluginIdentifier): Boolean =
        this.project.pluginManager.hasPlugin("$identifier")

    fun <ProjectPlugin : Plugin<Project>> has(
        type: KClass<ProjectPlugin>
    ): Boolean = this.project.plugins.hasPlugin(type)
}
