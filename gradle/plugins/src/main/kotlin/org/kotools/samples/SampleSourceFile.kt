package org.kotools.samples

import java.io.File

internal class SampleSourceFile private constructor(private val file: File) {
    init {
        require("Sample/" in this.file.path) {
            "'${this.file.name}' file should be in a sample source set."
        }
        val fileTypeIsSupported: Boolean = listOf(".kt", ".java")
            .any(this.file.name::endsWith)
        require(fileTypeIsSupported) {
            "'${this.file.extension}' files are not supported."
        }
    }

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
            val classRegex = Regex("class [A-Z][A-Za-z]*")
            lines.count { classRegex in it }
        }

    companion object {
        fun orNull(file: File): SampleSourceFile? = try {
            SampleSourceFile(file)
        } catch (exception: IllegalArgumentException) {
            null
        }
    }
}
