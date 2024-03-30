package org.kotools.types.samples

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import java.io.File

/** Task responsible for inlining samples in KDoc comments. */
@DisableCachingByDefault
public abstract class InlineSamples : DefaultTask() {
    /** The directory containing the sources to work on. */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val sources: DirectoryProperty

    /** The directory containing the samples. */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val samples: DirectoryProperty

    @TaskAction
    private fun execute() {
        val mainSourceSetRegex = Regex("/[a-z]+Main/")
        sources.asFileTree.asSequence()
            .filter { it != null && mainSourceSetRegex in it.path }
            .forEach(this::inlineSamplesIn)
    }

    private fun inlineSamplesIn(file: File) {
        val text: String = file.useLines { lines: Sequence<String> ->
            val keyword = "INLINE: "
            lines.map { line: String ->
                if (keyword !in line) line
                else {
                    val prefix: String = line.substringBefore(keyword)
                    val identifier: String = line.substringAfter(keyword)
                    samples.file(identifier)
                        .get()
                        .asFile
                        .useLines { sampleLines: Sequence<String> ->
                            sampleLines.joinToString(separator = "\n") {
                                "$prefix$it"
                            }
                        }
                }
            }.joinToString("\n").plus("\n")
        }
        file.writeText(text)
    }
}
