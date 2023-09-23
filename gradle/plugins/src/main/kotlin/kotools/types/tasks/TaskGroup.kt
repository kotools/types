package kotools.types.tasks

import org.gradle.api.Task

internal enum class TaskGroup {
    DOCUMENTATION, HELP;

    override fun toString(): String = name.lowercase()
}

internal fun Task.group(value: TaskGroup) {
    group = "$value"
}
