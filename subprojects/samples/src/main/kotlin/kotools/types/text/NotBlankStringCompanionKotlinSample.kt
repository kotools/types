package kotools.types.text

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal class NotBlankStringCompanionKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun create() {
        val text: NotBlankString = NotBlankString.create("Kotools Types")
        println(text) // Kotools Types
    } // END
}
