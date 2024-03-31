package org.kotools.types.samples

import org.gradle.api.file.Directory

internal class JavaSampleFile(
    private val name: String,
    private val function: JavaSampleFunction
) : SampleFile {
    init {
        require(name matches nameRegex) {
            "Java sample file name should match '$nameRegex'."
        }
    }

    // ----------------------- Overrides from kotlin.Any -----------------------

    override fun equals(other: Any?): Boolean =
        other is JavaSampleFile && this.name == other.name

    override fun hashCode(): Int {
        val prime = 31
        return prime + name.hashCode()
    }

    override fun toString(): String = name

    // -------------------------------------------------------------------------

    override fun saveIn(directory: Directory) {
        val text: String = buildString {
            appendLine("```java")
            appendLine(function.bodyText)
            append("```")
        }
        directory.file(name)
            .asFile
            .writeText(text)
    }

    companion object {
        const val FILE_EXTENSION: String = ".md"
        private val nameRegex = Regex("^[A-Za-z][A-Za-z._]+[A-Za-z]\\.md$")
    }
}
