package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PositiveIntCommonSample {
    @Test
    fun serialization() {
        val number: PositiveInt = 123.toPositiveInt()
            .getOrThrow()
        val encoded: String = Json.encodeToString(number)
        assertEquals(expected = "123", actual = encoded)
        val decoded: PositiveInt = Json.decodeFromString(encoded)
        assertEquals(expected = number, actual = decoded)
    }
}
