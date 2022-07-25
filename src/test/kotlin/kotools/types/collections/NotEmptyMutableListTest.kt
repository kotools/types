package kotools.types.collections

import io.github.kotools.assert.assertEquals
import io.github.kotools.assert.assertNotEquals
import io.github.kotools.assert.assertNotNull
import io.github.kotools.assert.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
        // ---------- Int ----------
        @Test
        fun `should insert an element at the beginning of the list with an index as an int that equals 0`() {
            // GIVEN
            val list: NotEmptyMutableList<String> = NotEmptyMutableList("two")
            val index = 0
            val element = "one"
            // WHEN
            assertDoesNotThrow { list.add(index, element) }
            // THEN
            list.head assertEquals element
            list[index] assertEquals element
        }

        @Test
        fun `should insert an element at the end of the list with an index as an int that equals the list's size`() {
            // GIVEN
            val list: NotEmptyMutableList<String> = NotEmptyMutableList("one")
            val index: Int = list.size
            val element = "two"
            // WHEN
            assertDoesNotThrow { list.add(index, element) }
            // THEN
            list[index] assertEquals element
        }

        @Test
        fun `should insert an element into the list with an index as an int in 1 until the list's size`() {
            // GIVEN
            val list: NotEmptyMutableList<String> =
                NotEmptyMutableList("one", "three")
            val index = 1
            val element = "two"
            // WHEN
            assertDoesNotThrow { list.add(index, element) }
            // THEN
            list[index] assertEquals element
        }

        @Test
        fun `should throw an error with an index as an int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableList<String> = NotEmptyMutableList("one")
            val index: Int = list.size + 1
            val element = "two"
            // WHEN & THEN
            assertFailsWith<IndexOutOfBoundsException> {
                list.add(index, element)
            }
        }
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
    inner class RemoveAt {
        @Test
        fun `should remove the head from a list containing several elements and with an index as an int that equals 0`() {
            // GIVEN
            val expectedList: NotEmptyList<String> = NotEmptyList("one", "two")
            val index = 0
            val target = "target"
            val list: NotEmptyMutableList<String> = expectedList
                .toNotEmptyMutableList()
                .apply { add(index, target) }
            // WHEN
            val element: String = assertDoesNotThrow { list removeAt index }
            // THEN
            element assertEquals target
            list.run {
                assertFalse { element in this }
                head assertNotEquals element
                size assertEquals expectedList.size
                head assertEquals expectedList.head
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedList[index2]
                }
            }
        }

        @Test
        fun `shouldn't remove the head from a singleton list and with an index as an int that equals 0`() {
            // GIVEN
            val expectedList: NotEmptyList<String> = NotEmptyList("one")
            val list: NotEmptyMutableList<String> =
                expectedList.toNotEmptyMutableList()
            val index = 0
            // WHEN
            val element: String = assertDoesNotThrow { list removeAt index }
            // THEN
            element assertEquals expectedList.head
            assertTrue { element in list }
            list.size assertEquals expectedList.size
            list.head assertEquals expectedList.head
        }

        @Test
        fun `should remove an element with an index as an int in 1 until the list's size`() {
            // GIVEN
            val expectedList: NotEmptyList<String> = NotEmptyList("one", "two")
            val target = "three"
            val list: NotEmptyMutableList<String> = expectedList
                .toNotEmptyMutableList()
                .also { it += target }
            val index: Int = list.size - 1
            // WHEN
            val element: String = assertDoesNotThrow { list removeAt index }
            // THEN
            element assertEquals target
            list.run {
                assertFalse { element in this }
                size assertEquals expectedList.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedList[index2]
                }
            }
        }

        @Test
        fun `should throw an error with an index as an int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyMutableList<String> = NotEmptyMutableList("one")
            val index: Int = list.size
            // WHEN & THEN
            assertFailsWith<IndexOutOfBoundsException> { list removeAt index }
        }
    }

    @Nested
    inner class Set {
        @Test
        fun `should replace the head with an index that equals 0`() {
            // GIVEN
            val expectedList: NotEmptyList<String> =
                NotEmptyList("one", "two", "three")
            val index = 0
            val previousElement = "four"
            val list: NotEmptyMutableList<String> = expectedList
                .subList(1, expectedList.size)
                .toNotEmptyMutableList()
                .apply { add(index, previousElement) }
            val element: String = expectedList.head
            // WHEN
            assertDoesNotThrow { list[index] = element }
            // THEN
            list.run {
                head assertEquals element
                head assertNotEquals previousElement
                this[index] assertEquals element
                this[index] assertNotEquals previousElement
                size assertEquals expectedList.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedList[index2]
                }
            }
        }

        @Test
        fun `should replace a tail's element with an index in 1 until the list's size`() {
            // GIVEN
            val expectedList: NotEmptyList<String> =
                NotEmptyList("one", "two", "three")
            val previousElement = "four"
            val list: NotEmptyMutableList<String> = NotEmptyMutableList(
                expectedList.head,
                previousElement,
                expectedList.last()
            )
            val index = 1
            val element: String = expectedList[index]
            // WHEN
            assertDoesNotThrow { list[index] = element }
            // THEN
            list.run {
                this[index] assertEquals element
                this[index] assertNotEquals previousElement
                size assertEquals expectedList.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedList[index2]
                }
            }
        }

        @Test
        fun `should throw an error with an index that is out of bounds`() {
            // GIVEN
            val expectedList: NotEmptyList<String> =
                NotEmptyList("one", "two", "three")
            val list: NotEmptyMutableList<String> =
                expectedList.toNotEmptyMutableList()
            val index = list.size
            val element = "error"
            // WHEN
            assertFailsWith<IndexOutOfBoundsException> { list[index] = element }
            // THEN
            list.run {
                size assertEquals expectedList.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedList[index2]
                }
            }
        }
    }

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
