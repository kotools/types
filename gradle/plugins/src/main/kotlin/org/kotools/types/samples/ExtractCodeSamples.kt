package org.kotools.types.samples

import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import java.io.File

/** Task responsible for extracting code samples from Kotlin and Java code. */
@DisableCachingByDefault(
    because = "Extracting code from sources doesn't worth caching."
)
public abstract class ExtractCodeSamples : DefaultTask() {
    /** The directory containing the sources to extract code samples from. */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val sourceDirectory: DirectoryProperty

    /** The directory that will contain the code samples. */
    @get:OutputDirectory
    public abstract val outputDirectory: DirectoryProperty

    @TaskAction
    private fun execute() {
        val directory: Directory = this.outputDirectory.get()
        this.sourceDirectory.asFileTree.asSequence()
            .mapNotNull(File::parseOrNull)
            .flatMap(ParsedFile::samples)
            .forEach { it.saveIn(directory) }
    }
}

private fun File.parseOrNull(): ParsedFile? =
    if (name.endsWith(KotlinFile.FILE_EXTENSION)) KotlinFileParser.parse(this)
    else null
