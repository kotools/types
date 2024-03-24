package org.kotools.types.base

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * The base plugin responsible for applying common configurations to all
 * projects.
 */
public class BasePlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project): Unit = Unit
}
