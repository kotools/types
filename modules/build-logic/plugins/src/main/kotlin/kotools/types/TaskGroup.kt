package kotools.types

import org.gradle.api.Task

internal enum class TaskGroup {
    BUILD, INFORMATION, OTHER, VERIFICATION;

    override fun toString(): String = name.toLowerCase()

    companion object {
        val all: List<TaskGroup> by lazy {
            values().filterNot { it == OTHER }
        }
    }
}

internal fun Task.group(value: TaskGroup) {
    group = "$value"
}
