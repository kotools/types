package kotools.types.samples

import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileTree
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/** Task responsible for extracting samples from Kotlin code. */
public abstract class KotlinSamplesExtractor : DefaultTask() {
    @get:InputFiles
    public abstract val sources: Property<FileTree>

    @get:OutputDirectory
    public abstract val output: DirectoryProperty

    @TaskAction
    private fun execute() {
        val directory: Directory = this.output.get()
        this.sources.get()
            .asSequence()
            .map(KotlinFile.Companion::parse)
            .flatMap(KotlinFile::samples)
            .forEach { it.saveIn(directory) }
    }
}
