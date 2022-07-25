package kotools.types.collections

import io.github.kotools.assert.*
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

class NotEmptyMutableCollectionTest {
    // ---------- Positional Access Operations ----------

    @Nested
    inner class Add {
        // ---------- PositiveInt ----------

        @Test
        fun `should insert an element at the beginning of the collection with an index as a positive int that equals 0`() {
            // GIVEN
            val element = "one"
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList(element, "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toList()
                    .subList(1, expectedCollection.size)
                    .toNotEmptyMutableList()
            val index = PositiveInt(0)
            // WHEN
            assertDoesNotThrow { collection.add(index, element) }
            // THEN
            collection.size assertEquals expectedCollection.size
            collection.head assertEquals expectedCollection.head
            collection.forEachIndexed { index2: Int, element2: String ->
                element2 assertEquals expectedCollection[index2]
            }
        }

        @Test
        fun `should insert an element at the end of the collection with an index as a positive int that equals the collection's size`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toList()
                    .subList(0, expectedCollection.size - 1)
                    .toNotEmptyMutableList()
            val index = PositiveInt(collection.size)
            val element: String = expectedCollection.last()
            // WHEN
            assertDoesNotThrow { collection.add(index, element) }
            // THEN
            collection.size assertEquals expectedCollection.size
            collection.last() assertEquals expectedCollection.last()
            collection.forEachIndexed { index2: Int, element2: String ->
                element2 assertEquals expectedCollection[index2]
            }
        }

        @Test
        fun `should insert an element into the collection with an index as a positive int in 1 until the collection's size`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.run { NotEmptyMutableList(head, last()) }
            val index = PositiveInt(1)
            val element: String = expectedCollection[index]
            // WHEN
            assertDoesNotThrow { collection.add(index, element) }
            // THEN
            collection.size assertEquals expectedCollection.size
            collection[index] assertEquals expectedCollection[index]
            collection.forEachIndexed { index2: Int, element2: String ->
                element2 assertEquals expectedCollection[index2]
            }
        }

        @Test
        fun `should throw an error with an index as a positive int that is out of bounds`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toNotEmptyMutableList()
            val index = PositiveInt(collection.size + 1)
            val element = "error"
            // WHEN
            assertFailsWith<IndexOutOfBoundsException> {
                collection.add(index, element)
            }
            // THEN
            collection.size assertEquals expectedCollection.size
            collection.forEachIndexed { index2: Int, element2: String ->
                element2 assertEquals expectedCollection[index2]
            }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should insert an element at the end of the collection with an index as a strictly positive int that equals the collection's size`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toList()
                    .subList(0, expectedCollection.size - 1)
                    .toNotEmptyMutableList()
            val index = StrictlyPositiveInt(collection.size)
            val element: String = expectedCollection.last()
            // WHEN
            assertDoesNotThrow { collection.add(index, element) }
            // THEN
            collection.size assertEquals expectedCollection.size
            collection.last() assertEquals expectedCollection.last()
            collection.forEachIndexed { index2: Int, element2: String ->
                element2 assertEquals expectedCollection[index2]
            }
        }

        @Test
        fun `should insert an element into the collection with an index as a strictly positive int in 1 until the collection's size`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.run { NotEmptyMutableList(head, last()) }
            val index = StrictlyPositiveInt(1)
            val element: String = expectedCollection[index]
            // WHEN
            assertDoesNotThrow { collection.add(index, element) }
            // THEN
            collection.size assertEquals expectedCollection.size
            collection[index] assertEquals expectedCollection[index]
            collection.forEachIndexed { index2: Int, element2: String ->
                element2 assertEquals expectedCollection[index2]
            }
        }

        @Test
        fun `should throw an error with an index as a strictly positive int that is out of bounds`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toNotEmptyMutableList()
            val index = StrictlyPositiveInt(collection.size + 1)
            val element = "error"
            // WHEN
            assertFailsWith<IndexOutOfBoundsException> {
                collection.add(index, element)
            }
            // THEN
            collection.size assertEquals expectedCollection.size
            collection.forEachIndexed { index2: Int, element2: String ->
                element2 assertEquals expectedCollection[index2]
            }
        }
    }

    @Nested
    inner class AddOrNull {
        // ---------- Int ----------

        @Test
        fun `should insert an element at the collection's head with an index as an int that equals 0`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toList()
                    .subList(1, expectedCollection.size)
                    .toNotEmptyMutableList()
            val index = 0
            val element: String = expectedCollection.head
            // WHEN
            val result: Unit? = collection.addOrNull(index, element)
            // THEN
            result.assertNotNull()
            collection.run {
                head assertEquals element
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        @Test
        fun `should insert an element at the collection's end with an index as an int that equals the collection's size`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toList()
                    .subList(0, expectedCollection.size - 1)
                    .toNotEmptyMutableList()
            val index: Int = collection.size
            val element: String = expectedCollection.last()
            // WHEN
            val result: Unit? = collection.addOrNull(index, element)
            // THEN
            result.assertNotNull()
            collection.run {
                last() assertEquals element
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        @Test
        fun `should insert an element into the collection with an index as an int in 1 until the collection's size`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.run { NotEmptyMutableList(head, last()) }
            val index = 1
            val element: String = expectedCollection[index]
            // WHEN
            val result: Unit? = collection.addOrNull(index, element)
            // THEN
            result.assertNotNull()
            collection.run {
                get(index) assertEquals element
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        @Test
        fun `should return null with an index as an int that is out of bounds`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toNotEmptyMutableList()
            val index: Int = collection.size + 1
            val element = "error"
            // WHEN
            val result: Unit? = collection.addOrNull(index, element)
            // THEN
            result.assertNull()
            collection.run {
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        // ---------- PositiveInt ----------

        @Test
        fun `should insert an element at the collection's head with an index as a positive int that equals 0`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toList()
                    .subList(1, expectedCollection.size)
                    .toNotEmptyMutableList()
            val index = PositiveInt(0)
            val element: String = expectedCollection.head
            // WHEN
            val result: Unit? = collection.addOrNull(index, element)
            // THEN
            result.assertNotNull()
            collection.run {
                head assertEquals element
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        @Test
        fun `should insert an element at the collection's end with an index as a positive int that equals the collection's size`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toList()
                    .subList(0, expectedCollection.size - 1)
                    .toNotEmptyMutableList()
            val index = PositiveInt(collection.size)
            val element: String = expectedCollection.last()
            // WHEN
            val result: Unit? = collection.addOrNull(index, element)
            // THEN
            result.assertNotNull()
            collection.run {
                last() assertEquals element
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        @Test
        fun `should insert an element into the collection with an index as a positive int in 1 until the collection's size`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.run { NotEmptyMutableList(head, last()) }
            val index = PositiveInt(1)
            val element: String = expectedCollection[index]
            // WHEN
            val result: Unit? = collection.addOrNull(index, element)
            // THEN
            result.assertNotNull()
            collection.run {
                get(index) assertEquals element
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        @Test
        fun `should return null with an index as a positive int that is out of bounds`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toNotEmptyMutableList()
            val index = PositiveInt(collection.size + 1)
            val element = "error"
            // WHEN
            val result: Unit? = collection.addOrNull(index, element)
            // THEN
            result.assertNull()
            collection.run {
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should insert an element at the collection's end with an index as a strictly positive int that equals the collection's size`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toList()
                    .subList(0, expectedCollection.size - 1)
                    .toNotEmptyMutableList()
            val index = StrictlyPositiveInt(collection.size)
            val element: String = expectedCollection.last()
            // WHEN
            val result: Unit? = collection.addOrNull(index, element)
            // THEN
            result.assertNotNull()
            collection.run {
                last() assertEquals element
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        @Test
        fun `should insert an element into the collection with an index as a strictly positive int in 1 until the collection's size`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.run { NotEmptyMutableList(head, last()) }
            val index = StrictlyPositiveInt(1)
            val element: String = expectedCollection[index]
            // WHEN
            val result: Unit? = collection.addOrNull(index, element)
            // THEN
            result.assertNotNull()
            collection.run {
                get(index) assertEquals element
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        @Test
        fun `should return null with an index as a strictly positive int that is out of bounds`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "two", "three")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toNotEmptyMutableList()
            val index = StrictlyPositiveInt(collection.size + 1)
            val element = "error"
            // WHEN
            val result: Unit? = collection.addOrNull(index, element)
            // THEN
            result.assertNull()
            collection.run {
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }
    }

    @Nested
    inner class RemoveAt {
        // ---------- PositiveInt ----------

        @Test
        fun `should remove the head from a collection containing several elements and with an index as a positive int that equals 0`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("two", "three")
            val index = PositiveInt(0)
            val expectedElement = "one"
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toNotEmptyMutableList()
                    .apply { add(index, expectedElement) }
            // WHEN
            val element: String =
                assertDoesNotThrow { collection removeAt index }
            // THEN
            element assertEquals expectedElement
            collection.run {
                head.run {
                    assertNotEquals(expectedElement)
                    assertEquals(expectedCollection.head)
                }
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        @Test
        fun `shouldn't remove the head from a singleton collection and with an index as a positive int that equals 0`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toNotEmptyMutableList()
            val index = PositiveInt(0)
            // WHEN
            val element: String =
                assertDoesNotThrow { collection removeAt index }
            // THEN
            element.run {
                assertEquals(collection.head)
                assertEquals(expectedCollection.head)
            }
            collection.run {
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        @Test
        fun `should remove an element with an index as a positive int in 1 until the collection's size`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<Int> = NotEmptyList(1, 3)
            val index = PositiveInt(1)
            val expectedElement = 2
            val collection: NotEmptyMutableCollection<Int> =
                expectedCollection.toNotEmptyMutableList()
                    .apply { add(index, expectedElement) }
            // WHEN
            val element: Int = assertDoesNotThrow { collection removeAt index }
            // THEN
            element assertEquals expectedElement
            collection.run {
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element2: Int ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        @Test
        fun `should throw an error with an index as a positive int that is out of bounds`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<Int> = NotEmptyList(1)
            val collection: NotEmptyMutableCollection<Int> =
                expectedCollection.toNotEmptyMutableList()
            val index = PositiveInt(collection.size)
            // WHEN
            assertFailsWith<IndexOutOfBoundsException> {
                collection removeAt index
            }
            // THEN
            collection.run {
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element: Int ->
                    element assertEquals expectedCollection[index2]
                }
            }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should remove an element with an index as a strictly positive int in 1 until the list's size`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<Int> = NotEmptyList(1, 3)
            val index = StrictlyPositiveInt(1)
            val expectedElement = 2
            val collection: NotEmptyMutableCollection<Int> =
                expectedCollection.toNotEmptyMutableList()
                    .apply { add(index, expectedElement) }
            // WHEN
            val element: Int = assertDoesNotThrow { collection removeAt index }
            // THEN
            element assertEquals expectedElement
            collection.run {
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element2: Int ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        @Test
        fun `should throw an error with an index as a strictly positive int that is out of bounds`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<Int> = NotEmptyList(1)
            val collection: NotEmptyMutableCollection<Int> =
                expectedCollection.toNotEmptyMutableList()
            val index = StrictlyPositiveInt(collection.size)
            // WHEN
            assertFailsWith<IndexOutOfBoundsException> {
                collection removeAt index
            }
            // THEN
            collection.run {
                size assertEquals expectedCollection.size
                forEachIndexed { index2: Int, element: Int ->
                    element assertEquals expectedCollection[index2]
                }
            }
        }
    }

    @Nested
    inner class RemoveAtOrNull {
        // ---------- Int ----------

        @Test
        fun `should remove the head from a collection containing several elements and with an index as an int that equals 0`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("two", "three")
            val index = 0
            val expectedElement = "one"
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toNotEmptyMutableList()
                    .apply { add(index, expectedElement) }
            // WHEN
            val element: String? = collection removeAtOrNull index
            // THEN
            element.assertNotNull().run {
                assertEquals(expectedElement)
                assertNotEquals(collection.head)
            }
            collection.run {
                size assertEquals expectedCollection.size
                head assertEquals expectedCollection.head
                forEachIndexed { index2: Int, element2: String ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        @Test
        fun `shouldn't remove the head from a singleton collection and with an index as an int that equals 0`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one")
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toNotEmptyMutableList()
            val index = 0
            // WHEN
            val element: String? = collection removeAtOrNull index
            // THEN
            element.assertNotNull().run {
                assertEquals(expectedCollection.head)
                assertEquals(collection.head)
            }
        }

        @Test
        fun `should remove an element with an index as an int in 1 until the collection's size`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one", "three")
            val index = 1
            val expectedElement = "two"
            val collection: NotEmptyMutableCollection<String> =
                expectedCollection.toNotEmptyMutableList()
                    .apply { add(index, expectedElement) }
            // WHEN
            val element: String? = collection removeAtOrNull index
            // THEN
            element.assertNotNull() assertEquals expectedElement
            collection.run {
                size assertEquals expectedCollection.size
                head assertEquals expectedCollection.head
                forEachIndexed { index2, element2 ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        @Test
        fun `should return null with an index as an int that is out of bounds`() {
            // GIVEN
            val expectedCollection: NotEmptyCollection<String> =
                NotEmptyList("one")
            val collection: NotEmptyMutableList<String> =
                expectedCollection.toNotEmptyMutableList()
            val index: Int = expectedCollection.typedSize + 1
            // WHEN
            val element: String? = collection removeAtOrNull index
            // THEN
            element.assertNull()
            collection.run {
                size assertEquals expectedCollection.size
                head assertEquals expectedCollection.head
                forEachIndexed { index2, element2 ->
                    element2 assertEquals expectedCollection[index2]
                }
            }
        }

        // ---------- PositiveInt ----------

        @Test
        fun `should remove the head from a collection containing several elements and with an index as a positive int that equals 0`() {
            // GIVEN
            val head = "one"
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList(head, "two")
            val index = PositiveInt(0)
            val size: Int = collection.size
            // WHEN
            val element: String? = collection removeAtOrNull index
            // THEN
            element.assertNotNull().run {
                assertEquals(head)
                assertNotEquals(collection.head)
            }
            collection.size assertEquals size - 1
        }

        @Test
        fun `shouldn't remove the head from a singleton collection and with an index as a positive int that equals 0`() {
            // GIVEN
            val head = "one"
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList(head)
            val index = PositiveInt(0)
            val size: Int = collection.size
            // WHEN
            val element: String? = collection removeAtOrNull index
            // THEN
            head.let {
                element.assertNotNull() assertEquals it
                collection.head assertEquals it
            }
            collection.size assertEquals size
        }

        @Test
        fun `should remove an element with an index as a positive int in 1 until the collection's size`() {
            // GIVEN
            val tail: NotEmptyList<String> = NotEmptyList("two", "three")
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList("one", *tail.toTypedArray())
            val index = PositiveInt(1)
            val size: Int = collection.size
            // WHEN
            val element: String? = collection removeAtOrNull index
            // THEN
            element.assertNotNull() assertEquals tail.head
            collection.size assertEquals size - 1
        }

        @Test
        fun `should return null with an index as a positive int that is out of bounds`() {
            // GIVEN
            val initialCollection: NotEmptyCollection<String> =
                NotEmptyList("one")
            val collection: NotEmptyMutableCollection<String> =
                initialCollection.toNotEmptyMutableList()
            val index = PositiveInt(collection.size)
            // WHEN
            val element: String? = collection removeAtOrNull index
            // THEN
            element.assertNull()
            collection.run {
                size assertEquals initialCollection.size
                head assertEquals initialCollection.head
                forEachIndexed { index2, element2 ->
                    element2 assertEquals initialCollection[index2]
                }
            }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should remove an element with an index as a strictly positive int in 1 until the collection's size`() {
            // GIVEN
            val tail = "two"
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList("one", tail)
            val index = StrictlyPositiveInt(1)
            val size: Int = collection.size
            // WHEN
            val element: String? = collection removeAtOrNull index
            // THEN
            element.assertNotNull() assertEquals tail
            collection.size assertEquals size - 1
        }

        @Test
        fun `should return null with an index as a strictly positive int that is out of bounds`() {
            // GIVEN
            val initialCollection: NotEmptyCollection<String> =
                NotEmptyList("one")
            val collection: NotEmptyMutableCollection<String> =
                initialCollection.toNotEmptyMutableList()
            val index = StrictlyPositiveInt(collection.size)
            // WHEN
            val element: String? = collection removeAtOrNull index
            // THEN
            element.assertNull()
            collection.run {
                size assertEquals initialCollection.size
                head assertEquals initialCollection.head
                forEachIndexed { index2, element2 ->
                    element2 assertEquals initialCollection[index2]
                }
            }
        }
    }
}
