package org.kotools.types.samples

import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileTree
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault

/** Task responsible for extracting samples from Kotlin code. */
@DisableCachingByDefault(
    because = "Extracting code from sources doesn't worth caching."
)
public abstract class KotlinSamplesExtractor : DefaultTask() {
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val sources: Property<FileTree>

    @get:OutputDirectory
    public abstract val output: DirectoryProperty

    @TaskAction
    private fun execute() {
        val directory: Directory = this.output.get()
        this.sources.get()
            .asSequence()
            .map(KotlinFileParser::parse)
            .flatMap(KotlinFile::samples)
            .forEach { it.saveIn(directory) }
    }
}
