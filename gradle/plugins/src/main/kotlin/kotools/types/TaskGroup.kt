package kotools.types

import org.gradle.api.Task
import org.gradle.api.tasks.diagnostics.TaskReportTask

internal enum class TaskGroup {
    DOCUMENTATION, INFORMATION, RECOMMENDED;

    override fun toString(): String = name.toLowerCase()
}

internal fun Task.group(value: TaskGroup) {
    group = "$value"
}

internal fun TaskReportTask.display(group: TaskGroup) {
    displayGroup = "$group"
}
