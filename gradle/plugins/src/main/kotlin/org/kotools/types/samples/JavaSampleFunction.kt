package org.kotools.types.samples

internal class JavaSampleFunction(
    val name: String,
    private val body: List<String>
) {
    init {
        require(name.isNotBlank()) {
            "Java function name shouldn't be blank."
        }
    }

    constructor(entry: Map.Entry<String, List<String>>) : this(
        name = entry.key,
        body = entry.value
    )

    val bodyText: String = body.joinToString("\n")

    // ----------------------- Overrides from kotlin.Any -----------------------

    override fun equals(other: Any?): Boolean =
        other is JavaSampleFunction && this.name == other.name

    override fun hashCode(): Int {
        val prime = 31
        return prime + name.hashCode()
    }

    override fun toString(): String = buildString {
        append("$KEYWORD $name() {\n")
        body.forEach { append("\t$it\n") }
        append('}')
    }

    // -------------------------------------------------------------------------

    companion object {
        const val KEYWORD: String = "void"
        val headerRegex = Regex("$KEYWORD [A-Za-z]+\\(")
    }
}
