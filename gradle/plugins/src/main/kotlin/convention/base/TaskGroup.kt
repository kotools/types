package convention.base

/** Represents a Gradle task group. */
internal enum class TaskGroup {
    /** The `module` Gradle task group. */
    Module,

    /** Returns the name of this Gradle task group in lowercase. */
    override fun toString(): String = this.name.lowercase()
}
