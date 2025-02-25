package org.kotools.samples

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import org.kotools.samples.internal.SampleSourceFile

/** Task responsible for checking the content of sample sources. */
@DisableCachingByDefault(because = "Only reading files doesn't worth caching.")
public abstract class CheckSampleSources : DefaultTask() {
    /** The directory containing sample sources. */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val sourceDirectory: DirectoryProperty

    @TaskAction
    internal fun execute(): Unit = this.sourceDirectory.asFileTree.asSequence()
        .filterNotNull()
        .mapNotNull(SampleSourceFile.Companion::orNull)
        .forEach(SampleSourceFile::checkSingleClass)
}
