package kotools.types.collections

import io.github.kotools.assert.assertEquals
import io.github.kotools.assert.assertFailsWith
import io.github.kotools.assert.assertNotNull
import io.github.kotools.assert.assertNull
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
}
