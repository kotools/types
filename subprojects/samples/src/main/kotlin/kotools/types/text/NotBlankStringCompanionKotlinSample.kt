package kotools.types.text

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal class NotBlankStringCompanionKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun create() {
        val text: NotBlankString = NotBlankString.create("Kotools Types")
        println(text) // Kotools Types
    } // END

    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun createOrNull() {
        val text: NotBlankString? = NotBlankString.createOrNull("Kotools Types")
        println(text) // Kotools Types
    } // END
}
