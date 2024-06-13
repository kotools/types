package org.kotools.types.samples

internal class Function(val name: String, private val body: List<String>) {
    init {
        val hasValidName: Boolean = this.name.isNotBlank()
        require(hasValidName) { BLANK_NAME_ERROR_MESSAGE }
    }

    val bodyText: String = this.body.joinToString("\n")

    fun formatBody(): Function {
        val spaces: Int = this.body.filter(String::isNotBlank)
            .minOf(String::countIndentationSpaces)
        val formattedBody: List<String> = this.body.map { it.drop(spaces) }
        return Function(this.name, formattedBody)
    }

    companion object {
        const val BLANK_NAME_ERROR_MESSAGE: String =
            "Function shouldn't have a blank name."
    }
}

private fun String.countIndentationSpaces(): Int {
    var count = 0
    var index = 0
    while (this[index] == ' ') {
        count++
        index++
    }
    return count
}
