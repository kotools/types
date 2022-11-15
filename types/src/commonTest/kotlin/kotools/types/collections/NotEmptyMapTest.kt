package kotools.types.collections

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotools.types.core.RandomValueHolder
import kotlin.test.Test

class NotEmptyMapTest : RandomValueHolder {
    // ---------- Builders ----------

    @Test
    fun notEmptyMapOf_should_pass() {
        // GIVEN
        val head: Pair<Int, String> = randomInt to randomString
        val tail: Array<Pair<Int, String>> = arrayOf(randomInt to randomString)
        // WHEN
        val result: NotEmptyMap<Int, String> = notEmptyMapOf(head, *tail)
        // THEN
        val expectedResult: Map<Int, String> = mutableMapOf(head)
            .apply { putAll(tail) }
        result.size assertEquals expectedResult.size
        result.head assertEquals head
        val expectedEntries: Set<Map.Entry<Int, String>> =
            expectedResult.entries
        result.entries.forEachIndexed { index: Int,
                                        entry: Map.Entry<Int, String> ->
            val expectedEntry: Map.Entry<Int, String> =
                expectedEntries.elementAt(index)
            entry.key assertEquals expectedEntry.key
            entry.value assertEquals expectedEntry.value
        }
    }

    @Test
    fun map_toNotEmptyMap_should_pass_with_a_not_empty_Map() {
        // GIVEN
        val map: Map<Int, String> = mapOf(randomInt to randomString)
        // WHEN
        val result: NotEmptyMap<Int, String> = map.toNotEmptyMap()
        // THEN
        result.size assertEquals map.size
        result.head assertEquals map.entries.first().toPair()
    }

    @Test
    fun map_toNotEmptyMap_should_throw_an_error_with_an_empty_Map(): Unit =
        assertFailsWith<IllegalArgumentException>(
            emptyMap<Int, String>()::toNotEmptyMap
        )
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()

    // ---------- Conversions ----------

    @Test
    fun toString_should_behave_like_a_Map() {
        // GIVEN
        val head: Pair<Int, String> = randomInt to randomString
        val map: NotEmptyMap<Int, String> = notEmptyMapOf(head)
        // WHEN
        val result: String = map.toString()
        // THEN
        result assertEquals mapOf(head).toString()
    }
}
