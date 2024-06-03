package kotools.types.text

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal class NotBlankStringKotlinSample {
    fun serialization() {
        val string: NotBlankString = "hello world".toNotBlankString()
            .getOrThrow() // TABS: 1
        val encoded: String = Json.encodeToString(string)
        println(encoded) // "hello world"
        val decoded: NotBlankString = Json.decodeFromString(encoded)
        println(decoded == string) // true
    } // END

    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun plusOperator() {
        val text: NotBlankString = "hello".toNotBlankString()
            .getOrThrow() // TABS: 1
        val message: NotBlankString = text + " world"
        println(message) // hello world
    } // END
}
