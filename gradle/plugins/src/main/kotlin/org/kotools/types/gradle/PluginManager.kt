package org.kotools.types.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.hasPlugin
import kotlin.reflect.KClass

internal class PluginManager(private val project: Project) {
    fun apply(identifier: PluginIdentifier): Unit =
        this.project.pluginManager.apply("$identifier")

    fun has(identifier: PluginIdentifier): Boolean =
        this.project.pluginManager.hasPlugin("$identifier")

    fun <T : Plugin<Project>> has(type: KClass<T>): Boolean =
        this.project.plugins.hasPlugin(type)
}
