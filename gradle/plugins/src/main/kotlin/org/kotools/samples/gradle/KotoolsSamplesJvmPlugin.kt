package org.kotools.samples.gradle

/**
 * Gradle plugin responsible for integrating tested samples in the API
 * reference of Kotlin/JVM projects.
 *
 * @constructor Creates an instance of this plugin.
 */
@Suppress("EqualsOrHashCode")
public class KotoolsSamplesJvmPlugin {
    // -------------------- Structural equality operations ---------------------

    /**
     * Returns `true` if the [other] object is an instance of
     * [KotoolsSamplesJvmPlugin], or returns `false` otherwise.
     */
    override fun equals(other: Any?): Boolean =
        other is KotoolsSamplesJvmPlugin

    // ------------------------------ Conversions ------------------------------

    /** Returns the string representation of this plugin. */
    override fun toString(): String =
        "Kotools Samples Gradle plugin for Kotlin/JVM projects"
}
