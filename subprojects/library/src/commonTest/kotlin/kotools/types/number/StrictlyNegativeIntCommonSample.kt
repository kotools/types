package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

internal class StrictlyNegativeIntCommonSample {
    @Test
    fun serialization() {
        val number: StrictlyNegativeInt = (-123).toStrictlyNegativeInt()
            .getOrThrow()
        val encoded: String = Json.encodeToString(number)
        assertEquals(expected = "-123", actual = encoded)
        val decoded: StrictlyNegativeInt = Json.decodeFromString(encoded)
        assertEquals(expected = number, actual = decoded)
    }
}
