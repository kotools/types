package org.kotools.types.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import java.io.File

/** Task responsible for inlining samples in KDoc comments. */
@DisableCachingByDefault(because = "Writes directly in files to read.")
public abstract class InlineKDocSamples : DefaultTask() {
    /**
     * The directories containing sources that may reference files within
     * the [extractedSamplesDirectory].
     */
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val sourceDirectories: Property<FileCollection>

    /**
     * The directory containing extracted samples that may be referenced from
     * files within [sourceDirectories].
     */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val extractedSamplesDirectory: DirectoryProperty

    @TaskAction
    private fun execute(): Unit = this.sourceDirectories.get()
        .asFileTree
        .asSequence()
        .filterNotNull()
        .filter { it.name.endsWith(".kt") }
        .forEach(this::inlineSamples)

    private fun inlineSamples(file: File): Unit = file.useLines {
        it.map(this::sampleOrOriginal)
            .joinToString("\n")
    }.let { file.writeText("$it\n") }

    private fun sampleOrOriginal(line: String): String {
        val keyword = "INLINE:"
        return if (keyword in line) {
            val prefix: String = line.substringBefore(keyword)
            val path: String = line.substringAfter('[')
                .substringBefore(']')
                .replace('.', '/')
                .plus(".md")
            this.sampleText(prefix, path)
        } else line
    }

    private fun sampleText(prefix: String, path: String): String = this
        .extractedSamplesDirectory
        .file(path)
        .get()
        .asFile
        .useLines { lines: Sequence<String> ->
            lines.joinToString(separator = "\n") { "$prefix$it" }
        }
}
