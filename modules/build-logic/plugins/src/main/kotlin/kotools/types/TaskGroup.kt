package kotools.types

import org.gradle.api.Task

internal enum class TaskGroup {
    BUILD, INFORMATION, VERIFICATION;

    override fun toString(): String = name.toLowerCase()

    companion object {
        val all: List<TaskGroup> by lazy { values().toList() }
    }
}

internal fun Task.group(value: TaskGroup) {
    group = "$value"
}
