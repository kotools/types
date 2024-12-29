package kotools.types.text

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

class NotBlankStringCommonSample {
    @Test
    fun serialization() {
        val string: NotBlankString = "hello world".toNotBlankString()
            .getOrThrow()
        val encoded: String = Json.encodeToString(string)
        assertEquals(expected = "\"hello world\"", actual = encoded)
        val decoded: NotBlankString = Json.decodeFromString(encoded)
        assertEquals(expected = string, actual = decoded)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun plusOperator() {
        val text: NotBlankString = "hello".toNotBlankString()
            .getOrThrow()
        val actual: NotBlankString = text + " world"
        val expected: NotBlankString = "hello world".toNotBlankString()
            .getOrThrow()
        assertEquals(expected, actual)
    }
}
