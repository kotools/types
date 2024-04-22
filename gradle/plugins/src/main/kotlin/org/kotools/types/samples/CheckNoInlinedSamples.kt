package org.kotools.types.samples

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import java.io.File

/**
 * Task responsible for checking the existence of already inlined samples in
 * main sources.
 * Fails if the specified [sources] have KDoc samples, indicating the
 * [solutionTaskPath] to run for fixing them.
 */
@DisableCachingByDefault(because = "Only reading files doesn't worth caching.")
public abstract class CheckNoInlinedSamples : DefaultTask() {
    /** The directory containing the sources to check. */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val sources: DirectoryProperty

    /** The path of the task to run in case of code samples in KDoc. */
    @get:Input
    public abstract val solutionTaskPath: Property<String>

    private val tokens: List<String> = listOf("```", "```kotlin", "```java")

    @TaskAction
    private fun execute() {
        val mainSourceSetRegex = Regex("/[A-Za-z]+Main/")
        this.sources.asFileTree.asSequence()
            .filter { it != null && mainSourceSetRegex in it.path }
            .forEach(this::checkContent)
    }

    private fun checkContent(file: File) {
        val hasSample: Boolean = file.useLines { lines: Sequence<String> ->
            lines.any { tokens.any(it::endsWith) }
        }
        if (!hasSample) return
        val errorMessage = "The following file shouldn't have inlined KDoc " +
                "samples: ${file.path}."
        val solution: String = this.solutionTaskPath.get()
        val solutionMessage =
            "Fix it by running the './gradlew $solution' command."
        error("$errorMessage\n$solutionMessage")
    }
}
