package org.kotools.samples.internal

import java.io.File

internal fun ProgrammingLanguage(file: File): ProgrammingLanguage {
    val language: ProgrammingLanguage? = ProgrammingLanguage.orNull(file)
    return requireNotNull(language) {
        "'.${file.extension}' files are not supported."
    }
}

internal sealed interface ProgrammingLanguage {
    val classKeyword: String
    val classHeaderRegex: Regex
    val fileExtension: String
    val functionHeaderRegex: Regex
    val functionKeyword: String
    val markdownIdentifier: String
    val packageKeyword: String
    val packageRegex: Regex

    companion object {
        fun orNull(file: File): ProgrammingLanguage? = when (file.extension) {
            Java.fileExtension -> Java
            Kotlin.fileExtension -> Kotlin
            else -> null
        }
    }

    object Java : ProgrammingLanguage {
        override val classKeyword: String = "class"
        override val classHeaderRegex: Regex =
            Regex("${this.classKeyword} [A-Z][A-Za-z]*")
        override val fileExtension: String = "java"
        override val functionKeyword: String = "void"
        override val functionHeaderRegex: Regex =
            Regex("${this.functionKeyword} [A-Za-z_]+\\(\\) \\{\$")
        override val markdownIdentifier: String = "java"
        override val packageKeyword: String = "package"
        override val packageRegex: Regex =
            Regex("^${this.packageKeyword} [a-z]+(?:\\.[a-z]+)*;\$")
    }

    object Kotlin : ProgrammingLanguage {
        override val classKeyword: String = "class"
        override val classHeaderRegex: Regex =
            Regex("${this.classKeyword} [A-Z][A-Za-z]*")
        override val fileExtension: String = "kt"
        override val functionKeyword: String = "fun"
        override val functionHeaderRegex: Regex =
            Regex("$functionKeyword [A-Za-z_]+\\(\\) \\{\$")
        override val markdownIdentifier: String = "kotlin"
        override val packageKeyword: String = "package"
        override val packageRegex: Regex =
            Regex("^${this.packageKeyword} [a-z]+(?:\\.[a-z]+)*\$")
    }
}
