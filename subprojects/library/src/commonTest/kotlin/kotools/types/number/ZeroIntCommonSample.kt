package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

internal class ZeroIntCommonSample {
    @Test
    fun serialization() {
        val encoded: String = Json.encodeToString(ZeroInt)
        assertEquals(expected = "0", actual = encoded)
        val decoded: ZeroInt = Json.decodeFromString(encoded)
        assertSame(expected = ZeroInt, actual = decoded)
    }
}
