package org.kotools.samples

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import java.io.File

/** Task responsible for checking the content of sample sources. */
@DisableCachingByDefault(because = "Only reading files doesn't worth caching.")
public abstract class CheckSampleSources : DefaultTask() {
    /** The directory containing sample sources. */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val sourceDirectory: DirectoryProperty

    @TaskAction
    private fun execute() {
        this.sourceDirectory.asFileTree.asSequence()
            .filterNotNull()
            .filter { it.path.contains("Sample/") }
            .filter { it.name.endsWith(".kt") }
            .firstOrNull { it.countClasses() != 1 }
            ?.fail()
    }
}

private fun File.countClasses(): Int =
    this.useLines { lines: Sequence<String> ->
        val classRegex = Regex("class [A-Z][A-Za-z]*")
        lines.count { classRegex in it }
    }

private fun File.fail(): Nothing {
    val message: String = listOf(
        "The '${this.name}' file should have a single class.",
        "File location: ${this.path}"
    ).joinToString(separator = "\n")
    error(message)
}
