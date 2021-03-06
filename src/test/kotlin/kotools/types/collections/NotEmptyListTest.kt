package kotools.types.collections

import io.github.kotools.assert.assertEquals
import io.github.kotools.assert.assertNotEquals
import io.github.kotools.assert.assertNotNull
import io.github.kotools.assert.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertFailsWith

class NotEmptyListTest {
    @Nested
    inner class Constructor {
        @Test
        fun `should return a singleton list`() {
            // GIVEN
            val head = 1
            // WHEN
            val list: NotEmptyList<Int> = NotEmptyList(head)
            // THEN
            list.head assertEquals head
        }

        @Test
        fun `should return a list with 3 elements`() {
            // GIVEN
            val expectedList: List<Int> = listOf(1, 2, 3)
            val head: Int = expectedList[0]
            val tail: Array<Int> = expectedList.subList(1, expectedList.size)
                .toTypedArray()
            // WHEN
            val list: NotEmptyList<Int> = NotEmptyList(head, *tail)
            // THEN
            list.forEachIndexed { index: Int, element: Int ->
                element assertEquals expectedList[index]
            }
        }
    }

    // ---------- Positional Access Operations ----------

    @Nested
    inner class Get {
        // ---------- Int ----------

        @Test
        fun `should return the first element with an int that equals 0`() {
            // GIVEN
            val head = 1
            val list: NotEmptyList<Int> = NotEmptyList(head, 2)
            val index = 0
            // WHEN
            val element: Int = assertDoesNotThrow { list[index] }
            // THEN
            element assertEquals head
        }

        @Test
        fun `should return the second element with an int that equals 1`() {
            // GIVEN
            val expectedElement = 2
            val list: NotEmptyList<Int> = NotEmptyList(1, expectedElement)
            val index = 1
            // WHEN
            val element: Int = assertDoesNotThrow { list[index] }
            // THEN
            element assertEquals expectedElement
        }

        @Test
        fun `should throw an error with an int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyList<Int> = NotEmptyList(1)
            val index = 10
            // WHEN & THEN
            assertFailsWith<IndexOutOfBoundsException> { list[index] }
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class ArrayToNotEmptyList {
        @Test
        fun `should pass with an array containing 3 elements`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            // WHEN
            val list: NotEmptyList<Int> =
                assertDoesNotThrow(array::toNotEmptyList)
            // THEN
            list.forEachIndexed { index: Int, element: Int ->
                element assertEquals array[index]
            }
        }

        @Test
        fun `should throw an error with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                block = array::toNotEmptyList
            )
        }
    }

    @Nested
    inner class CollectionToNotEmptyList {
        @Test
        fun `should pass with a collection containing 3 elements`() {
            // GIVEN
            val collection: Collection<Int> = listOf(1, 2, 3)
            // WHEN
            val list: NotEmptyList<Int> =
                assertDoesNotThrow(collection::toNotEmptyList)
            // THEN
            collection.forEachIndexed { index: Int, element: Int ->
                element assertEquals list[index]
            }
        }

        @Test
        fun `should throw an error with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptyList()
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                block = collection::toNotEmptyList
            )
        }
    }

    @Nested
    inner class ArrayToNotEmptyListOrNull {
        @Test
        fun `should pass with an array containing 3 elements`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            // WHEN
            val list: NotEmptyList<Int>? = array.toNotEmptyListOrNull()
            // THEN
            list.assertNotNull().forEachIndexed { index: Int, element: Int ->
                element assertEquals array[index]
            }
        }

        @Test
        fun `should return null with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            // WHEN
            val list: NotEmptyList<Int>? = array.toNotEmptyListOrNull()
            // THEN
            list.assertNull()
        }
    }

    @Nested
    inner class CollectionToNotEmptyListOrNull {
        @Test
        fun `should pass with a collection containing 3 elements`() {
            // GIVEN
            val collection: Collection<Int> = setOf(1, 2, 3)
            // WHEN
            val list: NotEmptyList<Int>? = collection.toNotEmptyListOrNull()
            // THEN
            list.assertNotNull().let {
                collection.forEachIndexed { index: Int, element: Int ->
                    element assertEquals it[index]
                }
            }
        }

        @Test
        fun `should return null with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptySet()
            // WHEN
            val list: NotEmptyList<Int>? = collection.toNotEmptyListOrNull()
            // THEN
            list.assertNull()
        }
    }

    @Nested
    inner class ArrayToNotEmptyListOrElse {
        @Test
        fun `should pass with an array containing 3 elements`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            val defaultValue = NotEmptyList(-1, -2, -3)
            // WHEN
            val list: NotEmptyList<Int> =
                array toNotEmptyListOrElse { defaultValue }
            // THEN
            list.forEachIndexed { index: Int, element: Int ->
                element assertEquals array[index]
                element assertNotEquals defaultValue[index]
            }
        }

        @Test
        fun `should return the default value with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            val defaultValue = NotEmptyList(-1, -2, -3)
            // WHEN
            val list: NotEmptyList<Int> =
                array toNotEmptyListOrElse { defaultValue }
            // THEN
            list.forEachIndexed { index: Int, element: Int ->
                element assertEquals defaultValue[index]
            }
        }
    }

    @Nested
    inner class CollectionToNotEmptyListOrElse {
        @Test
        fun `should pass with a collection containing 3 elements`() {
            // GIVEN
            val collection: Collection<Int> = listOf(1, 2, 3)
            val defaultValue = NotEmptyList(-1, -2, -3)
            // WHEN
            val list: NotEmptyList<Int> =
                collection toNotEmptyListOrElse { defaultValue }
            // THEN
            collection.forEachIndexed { index: Int, element: Int ->
                element assertEquals list[index]
                element assertNotEquals defaultValue[index]
            }
        }

        @Test
        fun `should return the default value with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptyList()
            val defaultValue = NotEmptyList(-1, -2, -3)
            // WHEN
            val list: NotEmptyList<Int> =
                collection toNotEmptyListOrElse { defaultValue }
            // THEN
            list.forEachIndexed { index: Int, element: Int ->
                element assertEquals defaultValue[index]
            }
        }
    }
}
