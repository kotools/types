package org.kotools.samples.internal

import kotlinx.ast.common.klass.KlassDeclaration
import java.io.File

internal class SampleSourceFile private constructor(private val file: File) {
    private val language: ProgrammingLanguage = ProgrammingLanguage(this.file)

    init {
        val fileIsInTestSourceSet: Boolean =
            this.file.path.contains("test/", ignoreCase = true)
        require(fileIsInTestSourceSet) {
            "'${this.file.name}' file should be in a test source set."
        }
        val suffix = "Sample.${this.language.fileExtension}"
        val fileNameHasValidSuffix: Boolean = this.file.name.endsWith(suffix)
        require(fileNameHasValidSuffix) {
            "'${this.file.name}' file's name should be suffixed by '$suffix'."
        }
    }

    fun checkContent(): Unit = when (this.language) {
        ProgrammingLanguage.Java -> Unit
        ProgrammingLanguage.Kotlin -> this.checkAbsenceOfTopLevelFunction()
    }

    private fun checkAbsenceOfTopLevelFunction() {
        val topLevelFunctionFound: Boolean = this.file.parseNodes()
            .filterIsInstance<KlassDeclaration>()
            .any { it.keyword == this.language.functionKeyword }
        if (topLevelFunctionFound) throw FileSystemException(
            file = this.file,
            reason = "Top-level function found in Kotlin sample source."
        )
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
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}
