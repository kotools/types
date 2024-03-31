package org.kotools.types.samples

import java.io.File

internal object JavaFileParser {
    fun parse(file: File): JavaFile {
        val functions: List<JavaSampleFunction> = file
            .useLines(block = Sequence<String>::getRawFunctions)
            .map(::JavaSampleFunction)
        return JavaFile(file.name, functions)
    }
}

private fun Sequence<String>.getRawFunctions(): Map<String, List<String>> {
    val functions: MutableMap<String, MutableList<String>> = mutableMapOf()
    var latestFunctionDetected: String? = null
    var read = false
    filter(String::isNotBlank).forEach {
        if (JavaSampleFunction.headerRegex in it) {
            val functionName: String = it
                .substringAfter("${JavaSampleFunction.KEYWORD} ")
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
