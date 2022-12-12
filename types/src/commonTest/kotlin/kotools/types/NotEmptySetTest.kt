package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContentEquals

class NotEmptySetTest {
    @Test
    fun toString_should_behave_like_a_Set() {
        val elements: Set<Int> = List(8) { Random.nextInt() }
            .toSet()
        elements.toNotEmptySet()
            .getOrThrow()
            .toString() assertEquals "$elements"
    }

    @Test
    fun notEmptySetOf_should_pass() {
        val head: Int = Random.nextInt()
        val tail: Array<Int> = List(7) { Random.nextInt() }
            .toTypedArray()
        val result: NotEmptySet<Int> = notEmptySetOf(head, *tail)
        val expected: List<Int> = listOf(head) + tail
        assertContentEquals(expected, result)
    }

    @Test
    fun collection_toNotEmptySet_should_pass_with_a_not_empty_Collection() {
        val elements: List<Int> = List(8) { Random.nextInt() }
        val result: NotEmptySet<Int> = elements.toNotEmptySet()
            .getOrThrow()
        assertContentEquals(elements, result)
    }

    @Test
    fun collection_toNotEmptySet_should_fail_with_an_empty_Collection() {
        val result: Result<NotEmptySet<Int>> = emptySet<Int>()
            .toNotEmptySet()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
