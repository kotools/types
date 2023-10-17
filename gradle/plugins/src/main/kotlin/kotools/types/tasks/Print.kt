/*
 * Copyright 2023 Loïc Lamarque.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault

/** Task that prints a [message] to the standard output stream. */
@DisableCachingByDefault(because = "Printing to the terminal is not cacheable.")
public abstract class PrintTask : DefaultTask() {
    /** The message to print. */
    @get:Input
    public abstract val message: Property<Any?>

    @TaskAction
    internal fun print(): Unit = println(message.orNull)
}
