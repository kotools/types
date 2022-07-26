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
