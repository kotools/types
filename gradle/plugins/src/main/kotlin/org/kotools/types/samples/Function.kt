package org.kotools.types.samples

internal class Function(val name: String, private val body: List<String>) {
    init {
        val hasValidName: Boolean = this.name.isNotBlank()
        require(hasValidName) { BLANK_NAME_ERROR_MESSAGE }
    }

    val bodyText: String = this.body.joinToString("\n")

    companion object {
        const val BLANK_NAME_ERROR_MESSAGE: String =
            "Function shouldn't have a blank name."
    }
}
