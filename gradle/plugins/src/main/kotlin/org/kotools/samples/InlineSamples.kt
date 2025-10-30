package org.kotools.samples

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import java.io.File

/**
 * Gradle task responsible for inlining samples referenced from KDoc comments in
 * main sources.
 */
@DisableCachingByDefault(because = "Writes directly in files to read.")
public abstract class InlineSamples : DefaultTask() {
    /**
     * The directory containing main sources to read that may reference files
     * within the [extractedSamplesDirectory].
     */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val sourceDirectory: DirectoryProperty

    /**
     * The directory containing extracted samples that may be referenced from
     * files within the [sourceDirectory].
     */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val extractedSamplesDirectory: DirectoryProperty

    /** The directory that will contain the inlined main sources. */
    @get:OutputDirectory
    public abstract val outputDirectory: DirectoryProperty

    @TaskAction
    internal fun execute(): Unit = this.sourceDirectory.asFileTree.asSequence()
        .filterNotNull()
        .filter { "Main/" in it.path || "main/" in it.path }
        .filter { it.name.endsWith(".kt") }
        .forEach(this::inlineSamples)

    private fun inlineSamples(file: File) {
        val content: String = file.useLines {
            it.map(this::sampleOrOriginal)
                .joinToString("\n")
        }
        val relativePath: Provider<String> = this.sourceDirectory.map {
            file.path.removePrefix("${it.asFile.path}/")
        }
        this.outputDirectory.file(relativePath)
            .get()
            .asFile
            .also(File::ensureParentDirsCreated)
            .writeText(content)
    }

    private fun sampleOrOriginal(line: String): String {
        val keyword = "SAMPLE: ["
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
