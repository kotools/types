package org.kotools.types.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault

/** Task responsible for extracting KDoc samples from sources. */
@DisableCachingByDefault(because = "Generating files doesn't worth caching.")
public abstract class ExtractKDocSamples : DefaultTask() {
    /** The source directories to extract KDoc samples from. */
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val sourceDirectories: Property<FileCollection>

    /** The directory that will contain the extracted KDoc samples. */
    @get:OutputDirectory
    public abstract val outputDirectory: DirectoryProperty

    @TaskAction
    private fun execute(): Unit = TODO()
}
