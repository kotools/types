package org.kotools.types.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import java.io.File

/** Task responsible for checking the validity of sample sources. */
@DisableCachingByDefault(because = "Only reading files doesn't worth caching.")
public abstract class CheckSampleSources : DefaultTask() {
    /** The source directories to read files from. */
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val sourceDirectories: Property<FileCollection>

    @TaskAction
    private fun execute(): Unit = this.sourceDirectories.get()
        .asFileTree
        .asSequence()
        .filterNotNull()
        .filter { it.name.endsWith(".kt") }
        .forEach(File::checkHasSingleClass)
}

private fun File.checkHasSingleClass() {
    TODO()
}
