package kotools.types.text

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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
}
