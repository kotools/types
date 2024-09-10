package org.kotools.samples.internal

import org.gradle.api.Project

/**
 * Error indicating that the Kotlin/JVM Gradle plugin wasn't applied to the
 * specified [project].
 *
 * @constructor Creates an instance of [KotlinJvmPluginNotFound] with the
 * specified [project].
 */
internal class KotlinJvmPluginNotFound(private val project: Project) {
    /** Returns the string representation of this error. */
    override fun toString(): String =
        "Kotlin/JVM plugin wasn't applied to ${this.project}."
}
