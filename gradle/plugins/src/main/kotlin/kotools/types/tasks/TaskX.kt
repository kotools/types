/*
 * Copyright 2023 Loïc Lamarque.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.tasks

import org.gradle.api.Task

internal fun Task.description(value: String) {
    require(value.isNotBlank()) {
        "The task '$path' shouldn't have a blank description."
    }
    description = value
}
