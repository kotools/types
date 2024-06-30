package kotools.types.collection

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

internal class NotEmptyListCommonSample {
    @Test
    fun serialization() {
        val string: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        val encoded: String = Json.encodeToString(string)
        assertEquals(expected = "[1,2,3]", actual = encoded)
        val decoded: NotEmptyList<Int> = Json.decodeFromString(encoded)
        assertEquals(expected = string, actual = decoded)
    }

    @Test
    fun toList() {
        val notEmptyList: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        val actual: List<Int> = notEmptyList.toList()
        val expected: List<Int> = listOf(1, 2, 3)
        assertContentEquals(expected, actual)
    }
}
