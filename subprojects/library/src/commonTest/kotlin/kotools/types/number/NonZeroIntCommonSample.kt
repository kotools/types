package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NonZeroIntCommonSample {
    @Test
    fun serialization() {
        val number: NonZeroInt = 123.toNonZeroInt()
            .getOrThrow()
        val encoded: String = Json.encodeToString(number)
        assertEquals(expected = "123", actual = encoded)
        val decoded: NonZeroInt = Json.decodeFromString(encoded)
        assertEquals(expected = number, actual = decoded)
    }
}
