package org.kotools.types.samples

import org.gradle.api.file.Directory

internal class SampleFile(
    private val name: String,
    private val language: ProgrammingLanguage,
    private val function: Function
) {
    fun saveIn(directory: Directory) {
        val text: String = listOf(
            "```${this.language.markdownIdentifier}",
            this.function.bodyText,
            "```"
        ).joinToString(separator = "\n")
        directory.file(this.name)
            .asFile
            .writeText(text)
    }
}
