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
@DisableCachingByDefault(because = "Writes directly in files to read.")
public abstract class InlineSamples : DefaultTask() {
    /** The directory containing the sources to work on. */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val sourcesDirectory: DirectoryProperty

    /** The directory containing the code samples. */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val samplesDirectory: DirectoryProperty

    @TaskAction
    private fun execute() {
        val mainSourceSetRegex = Regex("/[a-z]+Main/")
        this.sourcesDirectory.asFileTree.asSequence()
            .filter { it != null && mainSourceSetRegex in it.path }
            .forEach(this::inlineSamples)
    }

    private fun inlineSamples(file: File): Unit = file.useLines {
        it.map(this::sampleOrOriginal)
            .joinToString("\n")
    }.let { file.writeText("$it\n") }

    private fun sampleOrOriginal(line: String): String {
        val keyword = "INLINE: "
        return if (keyword in line) {
            val prefix: String = line.substringBefore(keyword)
            val identifier: String = line.substringAfter(keyword)
            this.sampleText(prefix, identifier)
        } else line
    }

    private fun sampleText(prefix: String, identifier: String): String = this
        .samplesDirectory
        .file(identifier)
        .get()
        .asFile
        .useLines { lines: Sequence<String> ->
            lines.joinToString("\n") { "$prefix$it" }
        }
}
