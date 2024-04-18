package org.kotools.types.samples

import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFile
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import java.io.File

/**
 * Task responsible for checking that the sample references from the [sources]
 * target an existing file from the [samples] build directory.
 */
@DisableCachingByDefault(because = "Only reading files doesn't worth caching.")
public abstract class CheckSamplesResolution : DefaultTask() {
    /** The directory containing the extracted samples. */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val samples: DirectoryProperty

    /** The directory containing the sources to check. */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val sources: DirectoryProperty

    @TaskAction
    private fun execute() {
        val mainSourceSetRegex = Regex("/[A-Za-z]+Main/")
        this.sources.asFileTree.asSequence()
            .filter { it != null && mainSourceSetRegex in it.path }
            .forEach(this::checkContent)
    }

    private fun checkContent(file: File) {
        val sampleNotFound: File = this.sampleNotFoundOrNull(file) ?: return
        val message = "The '${file.name}' file targets a " +
                "'${sampleNotFound.name}' sample that doesn't exist."
        error(message)
    }

    private fun sampleNotFoundOrNull(file: File): File? =
        file.useLines { lines: Sequence<String> ->
            val keyword = "SAMPLE: "
            val buildDirectory: Directory = this.samples.get()
            lines.filter { keyword in it }
                .map { it.substringAfter(keyword) }
                .map(buildDirectory::file)
                .map(RegularFile::getAsFile)
                .firstOrNull { !it.exists() }
        }
}
