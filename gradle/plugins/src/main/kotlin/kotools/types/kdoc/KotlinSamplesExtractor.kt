package kotools.types.kdoc

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileTree
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

/** Task responsible for extracting samples from Kotlin code. */
public abstract class KotlinSamplesExtractor : DefaultTask() {
    @get:InputFiles
    public abstract val sources: Property<FileTree>

    @get:OutputDirectory
    public abstract val output: DirectoryProperty

    @TaskAction
    internal fun execute(): Unit = this.sources.get()
        .flatMap(this::extractKotlinSamplesFrom)
        .forEach(this::save)

    private fun extractKotlinSamplesFrom(
        file: File
    ): List<KotlinSampleFunction> = file.useLines { lines: Sequence<String> ->
        val functionRegex = Regex("fun [A-Za-z]+Sample\\(\\)")
        val functions: MutableMap<String, MutableList<String>> = mutableMapOf()
        var latestFunctionDetected: String? = null
        var read = false
        lines.forEach {
            if (functionRegex in it) {
                read = true
                val functionName: String = it.substringAfter("fun ")
                    .substringBefore('(')
                functions += functionName to mutableListOf()
                latestFunctionDetected = functionName
            } else if (it.endsWith('}')) {
                read = false
                latestFunctionDetected = null
            } else if (read) {
                val line: String = it.trim()
                functions[latestFunctionDetected]?.add(line)
            }
        }
        functions.map {
            KotlinSampleFunction(name = it.key, body = it.value, origin = file)
        }
    }

    private fun save(sample: KotlinSampleFunction) {
        val fileName = "${sample.origin.name}#${sample.name}"
        val text: String = sample.body.joinToString("\n")
        this.output.file(fileName)
            .get()
            .asFile
            .writeText(text)
    }
}

private class KotlinSampleFunction(
    val name: String,
    val body: List<String>,
    val origin: File
) {
    override fun toString(): String = buildString {
        append("fun $name() {\n")
        body.forEach { append("\t$it\n") }
        append('}')
    }
}
