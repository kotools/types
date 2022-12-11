package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

class NotEmptyListTest {
    @Test
    fun toString_should_behave_like_a_Collection() {
        val collection: Collection<Int> = List(3) { NonZeroInt.random() }
            .map(NonZeroInt::toInt)
        collection.toNotEmptyList()
            .getOrThrow()
            .toString() assertEquals "$collection"
    }

    @Test
    fun notEmptyListOf_should_pass() {
        val head: Int = NonZeroInt.random()
            .toInt()
        val tail: Array<Int> = List(2) { NonZeroInt.random() }
            .map(NonZeroInt::toInt)
            .toTypedArray()
        val result: NotEmptyList<Int> = notEmptyListOf(head, *tail)
        val expected: List<Int> = listOf(head) + tail
        result.size assertEquals expected.size
        result.forEachIndexed { index: Int, element: Int ->
            element assertEquals expected[index]
        }
    }

    @Test
    fun collection_toNotEmptyList_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = List(3) { NonZeroInt.random() }
            .map(NonZeroInt::toInt)
        val result: NotEmptyList<Int> = collection.toNotEmptyList()
            .getOrThrow()
        result.size assertEquals collection.size
        result.forEachIndexed { index: Int, element: Int ->
            element assertEquals collection.elementAt(index)
        }
    }

    @Test
    fun collection_toNotEmptyList_should_fail_with_an_empty_Collection() {
        val result: Result<NotEmptyList<Int>> = emptyList<Int>()
            .toNotEmptyList()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
