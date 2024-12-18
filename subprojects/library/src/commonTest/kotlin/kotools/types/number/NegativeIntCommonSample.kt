package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NegativeIntCommonSample {
    @Test
    fun serialization() {
        val number: NegativeInt = (-123).toNegativeInt()
            .getOrThrow()
        val encoded: String = Json.encodeToString(number)
        assertEquals(expected = "-123", actual = encoded)
        val decoded: NegativeInt = Json.decodeFromString(encoded)
        assertEquals(expected = number, actual = decoded)
    }
}
