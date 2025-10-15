package org.kotools.types.gradle.internal

/** Represents a Gradle task group. */
internal enum class TaskGroup {
    /** The `CI` Gradle task group. */
    CI,

    /** The `documentation` Gradle task group. */
    Documentation,

    /** The `module` Gradle task group. */
    Module,

    /** The `root` Gradle task group. */
    Root;

    /** Returns the name of this Gradle task group in lowercase. */
    override fun toString(): String = this.name.lowercase()
}
