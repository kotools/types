package kotools.types.tasks

import org.gradle.api.Task

internal fun Task.description(value: String) {
    require(value.isNotBlank()) {
        "The task '$path' shouldn't have a blank description."
    }
    description = value
}
