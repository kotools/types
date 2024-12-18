package kotools.types.collection

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NotEmptySetCommonSample {
    @Test
    fun serialization() {
        val string: NotEmptySet<Int> = notEmptySetOf(1, 2, 3)
        val encoded: String = Json.encodeToString(string)
        assertEquals(expected = "[1,2,3]", actual = encoded)
        val decoded: NotEmptySet<Int> = Json.decodeFromString(encoded)
        assertEquals(expected = string, actual = decoded)
    }

    @Test
    fun toSet() {
        val integers: NotEmptySet<Int> = notEmptySetOf(1, 2, 3, 1)
        val actual: Set<Int> = integers.toSet()
        val expected: Set<Int> = setOf(1, 2, 3)
        assertEquals(expected, actual)
    }
}
