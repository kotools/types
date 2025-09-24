package convention.base

/** Represents a Gradle task group. */
internal enum class TaskGroup {
    /** The `module` Gradle task group. */
    Module,

    /** The `root` Gradle task group. */
    Root;

    /** Returns the name of this Gradle task group in lowercase. */
    override fun toString(): String = this.name.lowercase()
}
