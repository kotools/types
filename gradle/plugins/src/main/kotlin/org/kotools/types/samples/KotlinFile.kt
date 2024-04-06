package org.kotools.types.samples

internal class KotlinFile(
    private val name: String,
    private val functions: List<Function>
) : ParsedFile {
    init {
        require(name.endsWith(FILE_EXTENSION)) {
            "Kotlin files should have the '$FILE_EXTENSION' extension."
        }
    }

    // ----------------------- Overrides from kotlin.Any -----------------------

    override fun equals(other: Any?): Boolean =
        other is KotlinFile && this.name == other.name

    override fun hashCode(): Int {
        val prime = 31
        return prime + name.hashCode()
    }

    override fun toString(): String = name

    // -------------------------------------------------------------------------

    override fun samples(): List<KotlinSampleFile> = functions.map {
        val name: String = this.name.substringBefore(FILE_EXTENSION)
            .plus('.')
            .plus(it.name)
            .plus(KotlinSampleFile.FILE_EXTENSION)
        KotlinSampleFile(name, function = it)
    }

    companion object {
        const val FILE_EXTENSION: String = ".kt"
    }
}
