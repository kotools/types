package org.kotools.types.samples

import java.io.File

internal object KotlinFileParser {
    fun parse(file: File): KotlinFile {
        require(file.name.endsWith(KotlinFile.FILE_EXTENSION)) {
            val parser: String? = this::class.simpleName
            "'.${file.extension}' files are not supported by '$parser'."
        }
        val functions: List<KotlinFunction> = file
            .useLines(block = Sequence<String>::getRawFunctions)
            .map(::KotlinFunction)
        return KotlinFile(file.name, functions)
    }
}

private fun Sequence<String>.getRawFunctions(): Map<String, List<String>> {
    val functions: MutableMap<String, MutableList<String>> = mutableMapOf()
    var latestFunctionDetected: String? = null
    var read = false
    filter(String::isNotBlank).forEach {
        if (KotlinFunction.headerRegex in it) {
            val functionName: String = it
                .substringAfter("${KotlinFunction.KEYWORD} ")
                .substringBefore('(')
            functions += functionName to mutableListOf()
            latestFunctionDetected = functionName
            read = true
        } else if (it.endsWith('}')) {
            read = false
            latestFunctionDetected = null
        } else if (read) {
            val line: String = it.trim()
            functions[latestFunctionDetected]?.add(line)
        }
    }
    return functions
}
