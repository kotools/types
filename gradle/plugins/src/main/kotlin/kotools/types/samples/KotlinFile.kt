package kotools.types.samples

import java.io.File

internal class KotlinFile(
    private val name: String,
    private val functions: List<KotlinFunction>
) {
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

    fun samples(): List<KotlinSampleFile> = functions.map {
        val name: String = this.name.substringBefore(FILE_EXTENSION)
            .plus('.')
            .plus(it.name)
            .plus(FILE_EXTENSION)
        KotlinSampleFile(name, function = it)
    }

    companion object {
        private const val FILE_EXTENSION: String = ".kt"

        fun parse(file: File): KotlinFile {
            val functions: List<KotlinFunction> = file
                .useLines(block = Sequence<String>::getRawFunctions)
                .map(::KotlinFunction)
            return KotlinFile(file.name, functions)
        }
    }
}

private fun Sequence<String>.getRawFunctions(): Map<String, List<String>> {
    val rawFunctions: MutableMap<String, MutableList<String>> = mutableMapOf()
    var latestFunctionDetected: String? = null
    var read = false
    filter(String::isNotBlank).forEach {
        if (KotlinFunction.headerRegex in it) {
            val functionName: String = it
                .substringAfter("${KotlinFunction.KEYWORD} ")
                .substringBefore('(')
            rawFunctions += functionName to mutableListOf()
            latestFunctionDetected = functionName
            read = true
        } else if (it.endsWith('}')) {
            read = false
            latestFunctionDetected = null
        } else if (read) {
            val line: String = it.trim()
            rawFunctions[latestFunctionDetected]?.add(line)
        }
    }
    return rawFunctions
}
