package org.kotools.samples.internal

import java.io.File

internal class SampleSourceFile private constructor(private val file: File) {
    init {
        require("Sample/" in this.file.path || "sample/" in this.file.path) {
            "'${this.file.name}' file should be in a sample source set."
        }
    }

    private val language: ProgrammingLanguage = ProgrammingLanguage(this.file)

    fun checkSingleClass() {
        val numberOfClasses: Int = this.countClasses()
        if (numberOfClasses == 1) return
        val message: String = listOf(
            "The '${this.file.name}' file should have a single class.",
            "File location: ${this.file.path}"
        ).joinToString(separator = "\n")
        error(message)
    }

    private fun countClasses(): Int =
        this.file.useLines { lines: Sequence<String> ->
            lines.count { this.language.classHeaderRegex in it }
        }

    fun samples(): Set<Sample> {
        var identifier: MutableList<String> = mutableListOf()
        val body: MutableList<String> = mutableListOf()
        val samples: MutableList<Sample> = mutableListOf()
        var numberOfUnclosedBracketsInSample = 0
        var readBody = false
        this.file.useLines { lines: Sequence<String> ->
            lines.forEach {
                when {
                    it matches this.language.packageRegex -> identifier +=
                        it.substringAfter("${this.language.packageKeyword} ")
                            .substringBefore(';')
                            .split('.')
                    this.language.classHeaderRegex in it -> identifier += it
                        .substringAfter("${this.language.classKeyword} ")
                        .substringBefore(" {")
                    this.language.functionHeaderRegex in it -> {
                        identifier += it.substringBefore('(')
                            .substringAfter("${this.language.functionKeyword} ")
                        numberOfUnclosedBracketsInSample++
                        readBody = true
                    }
                    readBody -> {
                        if ('{' in it) numberOfUnclosedBracketsInSample++
                        if ('}' in it) numberOfUnclosedBracketsInSample--
                        if (numberOfUnclosedBracketsInSample > 0) body += it
                        else {
                            readBody = false
                            samples += Sample(
                                identifier.toList(),
                                body.toList(),
                                this.language
                            )
                            identifier = identifier.dropLast(1)
                                .toMutableList()
                            body.clear()
                        }
                    }
                }
            }
        }
        return samples.toSet()
    }

    companion object {
        fun orNull(file: File): SampleSourceFile? = try {
            SampleSourceFile(file)
        } catch (exception: IllegalArgumentException) {
            null
        }
    }
}
