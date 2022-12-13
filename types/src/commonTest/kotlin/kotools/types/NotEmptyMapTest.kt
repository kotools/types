package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.test.Test

class NotEmptyMapTest {
    @Test
    fun toString_should_behave_like_a_Map() {
        val map: Map<String, Int> = mapOf("a" to Random.nextInt())
        map.toNotEmptyMap()
            .getOrThrow()
            .toString() assertEquals "$map"
    }

    @Test
    fun notEmptyMapOf_should_pass() {
        val head: Pair<String, Int> = "a" to Random.nextInt()
        val tail: Array<Pair<String, Int>> = arrayOf(
            "b" to Random.nextInt(),
            "c" to Random.nextInt()
        )
        val result: NotEmptyMap<String, Int> = notEmptyMapOf(head, *tail)
        val expected: Map<String, Int> = mapOf(head, *tail)
        result shouldEqual expected
    }

    @Test
    fun map_toNotEmptyMap_should_pass_with_a_not_empty_Map() {
        val map: Map<String, Int> = mapOf("a" to Random.nextInt())
        val result: NotEmptyMap<String, Int> = map.toNotEmptyMap()
            .getOrThrow()
        result shouldEqual map
    }

    @Test
    fun map_toNotEmptyMap_should_fail_with_an_empty_Map() {
        val result: Result<NotEmptyMap<String, Int>> = emptyMap<String, Int>()
            .toNotEmptyMap()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}

private infix fun <K, V> Map<K, V>.shouldEqual(other: Map<K, V>) {
    size assertEquals other.size
    forEach {
        assertTrue { other.containsKey(it.key) }
        it.value assertEquals other[it.key]
    }
}
