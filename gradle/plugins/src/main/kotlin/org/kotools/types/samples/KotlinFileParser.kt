package org.kotools.types.samples

import java.io.File

internal object KotlinFileParser {
    fun parse(file: File): KotlinFile {
        val functions: List<Function> = file
            .useLines(block = Sequence<String>::getRawFunctions)
            .map { Function(name = it.key, body = it.value) }
        return KotlinFile(file.name, functions)
    }
}

private fun Sequence<String>.getRawFunctions(): Map<String, List<String>> {
    val functions: MutableMap<String, MutableList<String>> = mutableMapOf()
    var latestFunctionDetected: String? = null
    var read = false
    val functionHeaderRegex = Regex("fun [A-Za-z_]+\\(")
    filter(String::isNotBlank).forEach {
        if (functionHeaderRegex in it) {
            val functionName: String = it
                .substringAfter("fun ")
                .substringBefore('(')
            functions += functionName to mutableListOf()
            latestFunctionDetected = functionName
            read = true
        } else if (it.endsWith("} // END")) {
            read = false
            latestFunctionDetected = null
        } else if (read) {
            val line: String = if (Regex("TABS: \\d$") in it) buildString {
                val numberOfTabs: Int = it.substringAfter("TABS: ")
                    .toInt()
                repeat(numberOfTabs) { append("    ") }
                val code: String = it.substringBefore("// TABS:")
                    .trim()
                append(code)
            } else it.trim()
            functions[latestFunctionDetected]?.add(line)
        }
    }
    return functions
}
