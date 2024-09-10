package org.kotools.samples

import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import org.kotools.samples.internal.SampleSourceFile

/** Gradle task responsible for extracting samples for KDoc. */
@DisableCachingByDefault(because = "Generating files doesn't worth caching.")
public abstract class ExtractSamples : DefaultTask() {
    /** The directory containing sample sources. */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val sourceDirectory: DirectoryProperty

    /** The directory that will contain the extracted KDoc samples. */
    @get:OutputDirectory
    public abstract val outputDirectory: DirectoryProperty

    @TaskAction
    private fun execute() {
        val directory: Directory = this.outputDirectory.get()
        this.sourceDirectory.asFileTree.asSequence()
            .filterNotNull()
            .mapNotNull(SampleSourceFile.Companion::orNull)
            .flatMap(SampleSourceFile::samples)
            .forEach { it.saveFileIn(directory) }
    }
}
