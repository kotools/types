package kotools.types

import org.gradle.api.Task

internal fun Task.description(value: String) {
    require(value.isNotBlank()) {
        "The '$name' task shouldn't have a blank description."
    }
    description = value
}
