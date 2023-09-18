package kotools.types

import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider

internal enum class TaskGroup {
    INFORMATION, LIFECYCLE;

    override fun toString(): String = name.toLowerCase()

    companion object {
        val all: List<TaskGroup> by lazy { values().toList() }
    }
}

internal operator fun <T : Task> TaskGroup.plusAssign(
    tasks: List<TaskProvider<T>>
) {
    val groupName = "$this"
    tasks.forEach {
        it.configure { group = groupName }
    }
}
