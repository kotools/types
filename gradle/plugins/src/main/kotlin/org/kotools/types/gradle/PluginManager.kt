package org.kotools.types.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.hasPlugin
import kotlin.reflect.KClass

internal class PluginManager(private val project: Project) {
    operator fun plusAssign(identifier: PluginIdentifier): Unit =
        this.project.pluginManager.apply("$identifier")

    operator fun contains(identifier: PluginIdentifier): Boolean =
        this.project.pluginManager.hasPlugin("$identifier")

    operator fun <T : Plugin<Project>> contains(type: KClass<T>): Boolean =
        this.project.plugins.hasPlugin(type)
}
