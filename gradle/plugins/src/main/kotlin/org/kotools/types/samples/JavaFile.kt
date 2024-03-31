package org.kotools.types.samples

internal class JavaFile(
    private val name: String,
    private val functions: List<JavaSampleFunction>
) : ParsedFile {
    init {
        require(name.endsWith(FILE_EXTENSION)) {
            "Java files should have the '$FILE_EXTENSION' extension."
        }
    }

    // ----------------------- Overrides from kotlin.Any -----------------------

    override fun equals(other: Any?): Boolean =
        other is JavaFile && this.name == other.name

    override fun hashCode(): Int {
        val prime = 31
        return prime + name.hashCode()
    }

    override fun toString(): String = name

    // -------------------------------------------------------------------------

    override fun samples(): List<JavaSampleFile> = functions.map {
        val name: String = this.name.substringBefore(FILE_EXTENSION)
            .plus('.')
            .plus(it.name)
            .plus(JavaSampleFile.FILE_EXTENSION)
        JavaSampleFile(name, function = it)
    }

    companion object {
        const val FILE_EXTENSION: String = ".java"
    }
}
