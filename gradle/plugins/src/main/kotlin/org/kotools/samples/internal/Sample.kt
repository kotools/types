package org.kotools.samples.internal

import org.gradle.api.file.Directory
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import java.io.File

internal class Sample(
    private val identifier: List<String>,
    private val body: List<String>,
    private val language: ProgrammingLanguage
) {
    init {
        val identifierIsValid: Boolean = this.identifier.isNotEmpty()
                && this.identifier.all(String::isNotBlank)
        require(identifierIsValid) {
            val joinedIdentifier: String =
                this.identifier.joinToString(separator = ".")
            "'$joinedIdentifier' is an invalid sample's identifier."
        }
        val bodyIsValid: Boolean = this.body.isNotEmpty()
        require(bodyIsValid) { "Sample shouldn't have an empty body." }
    }

    fun saveFileIn(directory: Directory) {
        val body: String = this.body.joinToString(separator = "\n")
            .trimIndent()
        val text: String = listOf(
            "```${this.language.markdownIdentifier}",
            body,
            "```"
        ).joinToString(separator = "\n")
        val path: String = this.identifier.joinToString(separator = "/") + ".md"
        directory.file(path)
            .asFile
            .also(File::ensureParentDirsCreated)
            .writeText(text)
    }
}
