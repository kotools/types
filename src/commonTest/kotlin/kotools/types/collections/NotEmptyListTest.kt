package kotools.types.collections

import kotools.assert.*
import kotlin.test.Test

@Suppress("TestFunctionName")
class NotEmptyListTest {
    @Test
    fun notEmptyListOf_should_pass() {
        val head = 1
        val tail: Array<Int> = arrayOf(2, 3)
        val result: NotEmptyList<Int> = notEmptyListOf(head, *tail)
        result.head assertEquals head
        val expectedResult: List<Int> = listOf(head) + tail
        result.forEachIndexed { index: Int, element: Int ->
            element assertEquals expectedResult[index]
        }
    }

    @Test
    fun Collection_toNotEmptyList_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = listOf(1, 2, 3)
        val result: NotEmptyList<Int> = collection.toNotEmptyList()
        result.forEachIndexed { index: Int, element: Int ->
            element assertEquals collection.elementAt(index)
        }
    }

    @Test
    fun Collection_toNotEmptyList_should_throw_an_error_with_an_empty_Collection() {
        val collection: Collection<Int> = emptyList()
        assertFailsWith<IllegalArgumentException>(collection::toNotEmptyList)
    }

    @Test
    fun Collection_toNotEmptyListOrNull_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = listOf(1, 2, 3)
        val result: NotEmptyList<Int>? = collection.toNotEmptyListOrNull()
        result.assertNotNull().forEachIndexed { index: Int, element: Int ->
            element assertEquals collection.elementAt(index)
        }
    }

    @Test
    fun Collection_toNotEmptyListOrNull_should_return_null_with_an_empty_Collection() {
        val collection: Collection<Int> = emptyList()
        collection.toNotEmptyListOrNull().assertNull()
    }

    @Test
    fun Collection_toNotEmptyListOrElse_should_pass_with_a_not_empty_Collection() {
        val collection: Collection<Int> = listOf(1, 2, 3)
        val defaultValue: NotEmptyList<Int> = notEmptyListOf(-1, -2, -3)
        val result: NotEmptyList<Int> =
            collection toNotEmptyListOrElse { defaultValue }
        result.forEachIndexed { index: Int, element: Int ->
            element assertEquals collection.elementAt(index)
            element assertNotEquals defaultValue[index]
        }
    }

    @Test
    fun Collection_toNotEmptyListOrElse_should_return_the_default_value_with_an_empty_Collection() {
        val collection: Collection<Int> = emptyList()
        val defaultValue: NotEmptyList<Int> = notEmptyListOf(-1, -2, -3)
        val result: NotEmptyList<Int> =
            collection toNotEmptyListOrElse { defaultValue }
        result.forEachIndexed { index: Int, element: Int ->
            element assertEquals defaultValue[index]
        }
    }

    @Test
    fun Array_toNotEmptyListOrElse_should_pass_with_a_not_empty_Array() {
        val array: Array<Int> = arrayOf(1, 2, 3)
        val defaultValue: NotEmptyList<Int> = notEmptyListOf(-1, -2, -3)
        val result: NotEmptyList<Int> =
            array toNotEmptyListOrElse { defaultValue }
        result.forEachIndexed { index: Int, element: Int ->
            element assertEquals array.elementAt(index)
            element assertNotEquals defaultValue[index]
        }
    }

    @Test
    fun Array_toNotEmptyListOrElse_should_return_the_default_value_with_an_empty_Array() {
        val array: Array<Int> = emptyArray()
        val defaultValue: NotEmptyList<Int> = notEmptyListOf(-1, -2, -3)
        val result: NotEmptyList<Int> =
            array toNotEmptyListOrElse { defaultValue }
        result.forEachIndexed { index: Int, element: Int ->
            element assertEquals defaultValue[index]
        }
    }
}
