package org.kotools.types.samples

import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import java.io.File

/** Task responsible for extracting code samples from Kotlin and Java code. */
@DisableCachingByDefault(
    because = "Extracting code from sources doesn't worth caching."
)
public abstract class ExtractCodeSamples : DefaultTask() {
    /** The directory containing the sources to extract code samples from. */
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.NONE)
    public abstract val sourceDirectory: DirectoryProperty

    /** The directory that will contain the code samples. */
    @get:OutputDirectory
    public abstract val outputDirectory: DirectoryProperty

    @TaskAction
    private fun execute() {
        val directory: Directory = this.outputDirectory.get()
        this.sourceDirectory.asFileTree.asSequence()
            .mapNotNull(File::parseOrNull)
            .flatMap(ParsedFile::samples)
            .forEach { it.saveIn(directory) }
    }
}

// ---------------------------------- Parsing ----------------------------------

private fun File.parseOrNull(): ParsedFile? {
    val language: ProgrammingLanguage = ProgrammingLanguage.orNull(this)
        ?: return null
    val functions: List<Function> = this
        .useLines { it.getRawFunctions(language) }
        .map { Function(name = it.key, body = it.value) }
    return when (language) {
        ProgrammingLanguage.Java -> JavaFile(this.name, functions)
        ProgrammingLanguage.Kotlin -> KotlinFile(this.name, functions)
    }
}

private fun Sequence<String>.getRawFunctions(
    language: ProgrammingLanguage
): Map<String, List<String>> {
    val functions: MutableMap<String, MutableList<String>> = mutableMapOf()
    var latestFunctionDetected: String? = null
    var read = false
    forEach {
        if (language.functionHeaderRegex in it) {
            val functionName: String = it.substringBefore('(')
                .substringAfter("${language.functionKeyword} ")
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
