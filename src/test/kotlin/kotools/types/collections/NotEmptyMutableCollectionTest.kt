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

    @Nested
    inner class Set {
        // ---------- PositiveInt ----------

        @Test
        fun `should replace the head with an index as a positive int that equals 0`() {
            // GIVEN
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList("one")
            val index = PositiveInt(0)
            val element = "update"
            // WHEN
            assertDoesNotThrow { collection[index] = element }
            // THEN
            element.run {
                assertEquals(collection[index])
                assertEquals(collection.head)
            }
        }

        @Test
        fun `should replace a tail's element with an index as a positive int in 1 until the collection's size`() {
            // GIVEN
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList("one", "two")
            val index = PositiveInt(1)
            val element = "update"
            // WHEN
            assertDoesNotThrow { collection[index] = element }
            // THEN
            collection[index] assertEquals element
        }

        @Test
        fun `should throw an error with an index as a positive int that is out of bounds`() {
            // GIVEN
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList("one")
            val index = PositiveInt(collection.size)
            val element = "update"
            // WHEN & THEN
            assertFailsWith<IndexOutOfBoundsException> {
                collection[index] = element
            }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should replace a tail's element with an index as a strictly positive int in 1 until the collection's size`() {
            // GIVEN
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList("one", "two")
            val index = StrictlyPositiveInt(1)
            val element = "update"
            // WHEN
            assertDoesNotThrow { collection[index] = element }
            // THEN
            collection[index] assertEquals element
        }

        @Test
        fun `should throw an error with an index as a strictly positive int that is out of bounds`() {
            // GIVEN
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList("one")
            val index = StrictlyPositiveInt(collection.size)
            val element = "update"
            // WHEN & THEN
            assertFailsWith<IndexOutOfBoundsException> {
                collection[index] = element
            }
        }
    }

    @Nested
    inner class SetOrNull {
        // ---------- Int ----------

        @Test
        fun `should replace the head with an index as an int that equals 0`() {
            // GIVEN
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList("one")
            val index = 0
            val element = "update"
            // WHEN
            val result: String? = collection.setOrNull(index, element)
            // THEN
            element.run {
                assertEquals(result.assertNotNull())
                assertEquals(collection[index])
                assertEquals(collection.head)
            }
        }

        @Test
        fun `should replace a tail's element with an index as an int in 1 until the collection's size`() {
            // GIVEN
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList("one", "two")
            val index = 1
            val element = "update"
            // WHEN
            val result: String? = collection.setOrNull(index, element)
            // THEN
            element.run {
                assertEquals(result.assertNotNull())
                assertEquals(collection[index])
            }
        }

        @Test
        fun `should return null with an index as an int that is out of bounds`() {
            // GIVEN
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList("one", "two")
            val index: Int = collection.size
            val element = "update"
            // WHEN
            val result: String? = collection.setOrNull(index, element)
            // THEN
            result.assertNull()
        }

        // ---------- PositiveInt ----------

        @Test
        fun `should replace the head with an index as a positive int that equals 0`() {
            // GIVEN
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList("one")
            val index = PositiveInt(0)
            val element = "update"
            // WHEN
            val result: String? = collection.setOrNull(index, element)
            // THEN
            element.run {
                assertEquals(result.assertNotNull())
                assertEquals(collection[index])
                assertEquals(collection.head)
            }
        }

        @Test
        fun `should replace a tail's element with an index as a positive int in 1 until the collection's size`() {
            // GIVEN
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList("one", "two")
            val index = PositiveInt(1)
            val element = "update"
            // WHEN
            val result: String? = collection.setOrNull(index, element)
            // THEN
            element.run {
                assertEquals(result.assertNotNull())
                assertEquals(collection[index])
            }
        }

        @Test
        fun `should return null with an index as a positive int that is out of bounds`() {
            // GIVEN
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList("one", "two")
            val index = PositiveInt(collection.size)
            val element = "update"
            // WHEN
            val result: String? = collection.setOrNull(index, element)
            // THEN
            result.assertNull()
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should replace a tail's element with an index as a strictly positive int in 1 until the collection's size`() {
            // GIVEN
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList("one", "two")
            val index = StrictlyPositiveInt(1)
            val element = "update"
            // WHEN
            val result: String? = collection.setOrNull(index, element)
            // THEN
            element.run {
                assertEquals(result.assertNotNull())
                assertEquals(collection[index])
            }
        }

        @Test
        fun `should return null with an index as a strictly positive int that is out of bounds`() {
            // GIVEN
            val collection: NotEmptyMutableCollection<String> =
                NotEmptyMutableList("one", "two")
            val index = StrictlyPositiveInt(collection.size)
            val element = "update"
            // WHEN
            val result: String? = collection.setOrNull(index, element)
            // THEN
            result.assertNull()
        }
    }
}
