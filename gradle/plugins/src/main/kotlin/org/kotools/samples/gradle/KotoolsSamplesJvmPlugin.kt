package org.kotools.samples.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.kotools.samples.internal.KotlinJvmPluginNotFound
import java.util.Objects

/**
 * Gradle plugin responsible for integrating tested samples in the API
 * reference of Kotlin/JVM projects.
 *
 * @constructor Creates an instance of this plugin.
 */
public class KotoolsSamplesJvmPlugin : Plugin<Project> {
    // -------------------- Structural equality operations ---------------------

    /**
     * Returns `true` if the [other] object is an instance of
     * [KotoolsSamplesJvmPlugin], or returns `false` otherwise.
     */
    override fun equals(other: Any?): Boolean =
        other is KotoolsSamplesJvmPlugin

    /** Returns a hash code value for this plugin. */
    override fun hashCode(): Int = Objects.hash("$this")

    // ------------------------- Project configuration -------------------------

    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        project.checkKotlinJvmPlugin()
        TODO("Not yet implemented")
    }

    private fun Project.checkKotlinJvmPlugin() {
        val projectHasKotlinJvmPlugin: Boolean =
            this.pluginManager.hasPlugin("org.jetbrains.kotlin.jvm")
        if (!projectHasKotlinJvmPlugin) {
            val message = KotlinJvmPluginNotFound(this)
            error(message)
        }
    }

    // ------------------------------ Conversions ------------------------------

    /** Returns the string representation of this plugin. */
    override fun toString(): String =
        "Kotools Samples Gradle plugin for Kotlin/JVM projects"
}
