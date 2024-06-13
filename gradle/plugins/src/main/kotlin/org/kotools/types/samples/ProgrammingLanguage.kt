package org.kotools.types.samples

import java.io.File

internal sealed interface ProgrammingLanguage {
    val fileExtension: String
    val functionHeaderRegex: Regex
    val functionKeyword: String
    val markdownIdentifier: String

    companion object {
        fun orNull(file: File): ProgrammingLanguage? = when {
            file.name.endsWith(Java.fileExtension) -> Java
            file.name.endsWith(Kotlin.fileExtension) -> Kotlin
            else -> null
        }
    }

    object Java : ProgrammingLanguage {
        override val fileExtension: String = ".java"
        override val functionKeyword: String = "void"
        override val functionHeaderRegex: Regex =
            Regex("$functionKeyword [A-Za-z_]+\\(")
        override val markdownIdentifier: String = "java"
    }

    object Kotlin : ProgrammingLanguage {
        override val fileExtension: String = ".kt"
        override val functionKeyword: String = "fun"
        override val functionHeaderRegex: Regex =
            Regex("$functionKeyword [A-Za-z_]+\\(")
        override val markdownIdentifier: String = "kotlin"
    }
}
