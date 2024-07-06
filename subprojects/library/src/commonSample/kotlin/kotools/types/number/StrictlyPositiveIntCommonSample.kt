package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

internal class StrictlyPositiveIntCommonSample {
    @Test
    fun serialization() {
        val number: StrictlyPositiveInt = 123.toStrictlyPositiveInt()
            .getOrThrow()
        val encoded: String = Json.encodeToString(number)
        assertEquals(expected = "123", actual = encoded)
        val decoded: StrictlyPositiveInt = Json.decodeFromString(encoded)
        assertEquals(expected = number, actual = decoded)
    }
}
