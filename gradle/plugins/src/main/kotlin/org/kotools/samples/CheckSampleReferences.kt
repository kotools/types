package org.kotools.samples

import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import java.io.File

/** Gradle task responsible for checking sample references from main sources. */
@DisableCachingByDefault(because = "Only reading files doesn't worth caching.")
public abstract class CheckSampleReferences : DefaultTask() {
    /**
     * The directory containing main sources to check that may reference files
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

    @TaskAction
    private fun execute() {
        val samplesDirectory: Directory = this.extractedSamplesDirectory.get()
        this.sourceDirectory.asFileTree.asSequence()
            .filterNotNull()
            .filter { "Main/" in it.path }
            .filter { it.name.endsWith(".kt") }
            .forEach { it.checkSampleResolutions(samplesDirectory) }
    }
}

private fun File.checkSampleResolutions(directory: Directory) {
    val identifiers: Set<String> = this.useLines { lines: Sequence<String> ->
        lines.filter { it.contains("SAMPLE: [") }
            .map { it.substringAfter('[') }
            .map { it.substringBefore(']') }
            .toSet()
    }
    val unresolvedIdentifier: String = identifiers.firstOrNull {
        val path: String = it.replace(oldChar = '.', newChar = '/') + ".md"
        !directory.file(path)
            .asFile
            .exists()
    } ?: return
    val message: String = listOf(
        "'${this.name}' wants to inline a non-existent " +
                "'$unresolvedIdentifier' function in documentation.",
        "File location: ${this.path}"
    ).joinToString(separator = "\n")
    error(message)
}
