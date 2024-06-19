package org.kotools.types.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import java.io.File

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
    private fun execute() {
        val directory: Directory = outputDirectory.get()
        this.sourceDirectories.get()
            .asFileTree
            .asSequence()
            .filterNotNull()
            .filter { it.name.endsWith(".kt") }
            .flatMap(File::toSamples)
            .forEach { it.saveIn(directory) }
    }
}

private data class Sample(val identifier: List<String>, val body: List<String>)

private fun File.toSamples(): List<Sample> =
    this.useLines { lines: Sequence<String> ->
        val packageRegex = Regex("^package [a-z]+(?:\\.[a-z]+)*\$")
        val classRegex = Regex("class [A-Z][A-Za-z]*")
        val functionRegex = Regex("fun [a-z][A-Za-z]*\\(\\) \\{\$")
        var identifier: MutableList<String> = mutableListOf()
        val body: MutableList<String> = mutableListOf()
        val samples: MutableList<Sample> = mutableListOf()
        var numberOfUnclosedBracketsInSample = 0
        var readBody = false
        lines.forEach {
            when {
                it matches packageRegex -> identifier +=
                    it.substringAfter("package ")
                classRegex in it -> identifier += it.substringAfter("class ")
                    .substringBefore(" {")
                functionRegex in it -> {
                    identifier += it.substringBefore('(')
                        .substringAfter("fun ")
                    numberOfUnclosedBracketsInSample++
                    readBody = true
                }
                readBody -> {
                    if ('{' in it) numberOfUnclosedBracketsInSample++
                    if ('}' in it) numberOfUnclosedBracketsInSample--
                    if (numberOfUnclosedBracketsInSample > 0) body += it
                    else {
                        readBody = false
                        samples += Sample(identifier.toList(), body.toList())
                        identifier = identifier.dropLast(1)
                            .toMutableList()
                        body.clear()
                    }
                }
            }
        }
        samples
    }

private fun Sample.saveIn(directory: Directory) {
    val body: String = this.body.joinToString(separator = "\n")
        .trimIndent()
    val text: String = listOf("```kotlin", body, "```")
        .joinToString(separator = "\n")
    val path: String = this.identifier.joinToString(separator = "/") + ".md"
    directory.file(path)
        .asFile
        .also(File::ensureParentDirsCreated)
        .writeText(text)
}
