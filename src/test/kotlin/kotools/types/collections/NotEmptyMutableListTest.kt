package kotools.types.collections

import io.github.kotools.assert.assertEquals
import io.github.kotools.assert.assertNotEquals
import io.github.kotools.assert.assertNotNull
import io.github.kotools.assert.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertFailsWith

class NotEmptyMutableListTest {
    @Nested
    inner class Constructor {
        @Test
        fun `should return a singleton list`() {
            // GIVEN
            val head = 1
            // WHEN
            val list: NotEmptyMutableList<Int> = NotEmptyMutableList(head)
            // THEN
            list.head assertEquals head
        }

        @Test
        fun `should return a list with 3 elements`() {
            // GIVEN
            val expectedList: List<Int> = listOf(1, 2, 3)
            val head: Int = expectedList.first()
            val tail: Array<Int> = expectedList.subList(1, expectedList.size)
                .toTypedArray()
            // WHEN
            val list: NotEmptyMutableList<Int> =
                NotEmptyMutableList(head, *tail)
            // THEN
            list.run {
                size assertEquals expectedList.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals expectedList[index]
                }
            }
        }
    }

    // ---------- Positional Access Operations ----------

    @Nested
    inner class Add {
        @Ignore
        @Test
        fun `should insert the element to the beginning of the list with an index that equals 0`() {
            // GIVEN
            val expectedList: NotEmptyList<String> =
                NotEmptyList("one", "two", "three")
            val list: NotEmptyMutableList<String> = expectedList
                .subList(1, expectedList.size)
                .toNotEmptyMutableList()
            val index = 0
            val element: String = expectedList.head
            // WHEN
            assertDoesNotThrow { list.add(index, element) }
            // THEN
            list.forEachIndexed { index2: Int, element2: String ->
                element2 assertEquals expectedList[index2]
            }
            TODO()
        }
        // TODO: 21/07/2022 Should insert the element to the end of the list with an index that equals the list's size
        // TODO: 21/07/2022 Should insert the element in the middle of the list with an index between 0 and the list's size excluded
        // TODO: 21/07/2022 Should throw an error with an index that is out of bounds
    }

    @Nested
    inner class Get {
        @Test
        fun `should return the first element with an int that equals 0`() {
            // GIVEN
            val head = 1
            val list: NotEmptyMutableList<Int> = NotEmptyMutableList(head, 2)
            val index = 0
            // WHEN
            val element: Int = assertDoesNotThrow { list[index] }
            // THEN
            element assertEquals head
        }

        @Test
        fun `should return the second element with an int that equals 1`() {
            // GIVEN
            val tail = 2
            val list: NotEmptyMutableList<Int> = NotEmptyMutableList(1, tail)
            val index = 1
            // WHEN
            val element: Int = assertDoesNotThrow { list[index] }
            // THEN
            element assertEquals tail
        }

        @Test
        fun `should throw an error with an int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableList<Int> = NotEmptyMutableList(1)
            val index = 10
            // WHEN & THEN
            assertFailsWith<IndexOutOfBoundsException> { list[index] }
        }
    }

    @Nested
    inner class RemoveAt // TODO: 21/07/2022 Not implemented yet

    @Nested
    inner class Set // TODO: 21/07/2022 Not implemented yet

    // ---------- Conversions ----------

    @Nested
    inner class ArrayToNotEmptyMutableList {
        @Test
        fun `should pass with an array containing at least 1 element`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            // WHEN
            val list: NotEmptyMutableList<Int> =
                assertDoesNotThrow(array::toNotEmptyMutableList)
            // THEN
            list.run {
                size assertEquals array.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals array[index]
                }
            }
        }

        @Test
        fun `should throw an error with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                block = array::toNotEmptyMutableList
            )
        }
    }

    @Nested
    inner class CollectionToNotEmptyMutableList {
        @Test
        fun `should pass with a collection containing at least 1 element`() {
            // GIVEN
            val collection: Collection<Int> = listOf(1, 2, 3)
            // WHEN
            val list: NotEmptyMutableList<Int> =
                assertDoesNotThrow(collection::toNotEmptyMutableList)
            // THEN
            list.run {
                size assertEquals collection.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals collection.elementAt(index)
                }
            }
        }

        @Test
        fun `should throw an error with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptyList()
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                block = collection::toNotEmptyMutableList
            )
        }
    }

    @Nested
    inner class ArrayToNotEmptyMutableListOrNull {
        @Test
        fun `should pass with an array containing at least 1 element`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            // WHEN
            val list: NotEmptyMutableList<Int>? =
                array.toNotEmptyMutableListOrNull()
            // THEN
            list.assertNotNull().run {
                size assertEquals array.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals array[index]
                }
            }
        }

        @Test
        fun `should return null with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            // WHEN
            val list: NotEmptyMutableList<Int>? =
                array.toNotEmptyMutableListOrNull()
            // THEN
            list.assertNull()
        }
    }

    @Nested
    inner class CollectionToNotEmptyMutableListOrNull {
        @Test
        fun `should pass with a collection containing at least 1 element`() {
            // GIVEN
            val collection: Collection<Int> = listOf(1, 2, 3)
            // WHEN
            val list: NotEmptyMutableList<Int>? =
                collection.toNotEmptyMutableListOrNull()
            // THEN
            list.assertNotNull().run {
                size assertEquals collection.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals collection.elementAt(index)
                }
            }
        }

        @Test
        fun `should return null with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptyList()
            // WHEN
            val list: NotEmptyMutableList<Int>? =
                collection.toNotEmptyMutableListOrNull()
            // THEN
            list.assertNull()
        }
    }

    @Nested
    inner class ArrayToNotEmptyMutableListOrElse {
        @Test
        fun `should pass with an array containing at least 1 element`() {
            // GIVEN
            val array: Array<Int> = arrayOf(1, 2, 3)
            val defaultValue: NotEmptyMutableList<Int> = array.map { -it }
                .toNotEmptyMutableList()
            // WHEN
            val list: NotEmptyMutableList<Int> =
                array toNotEmptyMutableListOrElse { defaultValue }
            // THEN
            list.run {
                size assertEquals array.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals array.elementAt(index)
                    element assertNotEquals defaultValue[index]
                }
            }
        }

        @Test
        fun `should return the default value with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            val defaultValue: NotEmptyMutableList<Int> =
                NotEmptyMutableList(-1, -2, -3)
            // WHEN
            val list: NotEmptyMutableList<Int> =
                array toNotEmptyMutableListOrElse { defaultValue }
            // THEN
            list.run {
                size assertEquals defaultValue.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals defaultValue[index]
                }
            }
        }
    }

    @Nested
    inner class CollectionToNotEmptyMutableListOrElse {
        @Test
        fun `should pass with a collection containing at least 1 element`() {
            // GIVEN
            val collection: Collection<Int> = listOf(1, 2, 3)
            val defaultValue: NotEmptyMutableList<Int> = collection.map { -it }
                .toNotEmptyMutableList()
            // WHEN
            val list: NotEmptyMutableList<Int> =
                collection toNotEmptyMutableListOrElse { defaultValue }
            // THEN
            list.run {
                size assertEquals collection.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals collection.elementAt(index)
                    element assertNotEquals defaultValue[index]
                }
            }
        }

        @Test
        fun `should return the default value with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptyList()
            val defaultValue: NotEmptyMutableList<Int> =
                NotEmptyMutableList(-1, -2, -3)
            // WHEN
            val list: NotEmptyMutableList<Int> =
                collection toNotEmptyMutableListOrElse { defaultValue }
            // THEN
            list.run {
                size assertEquals defaultValue.size
                forEachIndexed { index: Int, element: Int ->
                    element assertEquals defaultValue[index]
                }
            }
        }
    }
}
