package org.kotools.types.samples

internal class ParsedFile(
    private val name: String,
    private val language: ProgrammingLanguage,
    private val functions: List<Function>
) {
    fun samples(): List<SampleFile> = this.functions.map {
        val name: String = this.name
            .substringBefore(this.language.fileExtension)
            .plus('.')
            .plus(it.name)
            .plus(".md")
        when (this.language) {
            ProgrammingLanguage.Java -> JavaSampleFile(name, function = it)
            ProgrammingLanguage.Kotlin -> KotlinSampleFile(name, function = it)
        }
    }
}
