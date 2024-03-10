package kotools.types.tasks

import org.gradle.api.Task
import java.util.Locale

internal enum class TaskGroup {
    BUILD, DOCUMENTATION;

    override fun toString(): String {
        val locale: Locale = Locale.getDefault()
        return name.lowercase(locale)
    }
}

internal fun Task.group(value: TaskGroup) {
    group = "$value"
}
