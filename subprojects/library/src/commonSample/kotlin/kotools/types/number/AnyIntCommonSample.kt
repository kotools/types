package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

internal class AnyIntCommonSample {
    @Test
    fun serialization() {
        val number: AnyInt = 123.toNonZeroInt()
            .getOrThrow()
        val encoded: String = Json.encodeToString(number)
        assertEquals(expected = "123", actual = encoded)
        val decoded: AnyInt = Json.decodeFromString(encoded)
        assertEquals(expected = number, actual = decoded)
    }
}
