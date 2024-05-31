package kotools.types.text

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal class NotBlankStringKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun plusOperator() {
        val text: NotBlankString = "hello".toNotBlankString()
            .getOrThrow() // TABS: 1
        val message: NotBlankString = text + " world"
        println(message) // hello world
    } // END
}
