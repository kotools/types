package kotools.types.collections

import io.github.kotools.assert.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

class NotEmptySetTest {
    @Nested
    inner class Constructor {
        @Test
        fun `should return a singleton not empty set`() {
            // GIVEN
            val head = 1
            // WHEN
            val set: NotEmptySet<Int> = NotEmptySet(head)
            // THEN
            set.head assertEquals head
        }

        @Test
        fun `should return a not empty set with 3 elements`() {
            // GIVEN
            val expectedSet: Set<Int> = setOf(1, 2, 3)
            val head: Int = expectedSet.first()
            val tail: Array<Int> = expectedSet.toList()
                .subList(1, expectedSet.size)
                .toTypedArray()
            // WHEN
            val set: NotEmptySet<Int> = NotEmptySet(head, *tail)
            // THEN
            set.forEachIndexed { index: Int, element: Int ->
                element assertEquals expectedSet.elementAt(index)
            }
        }
    }

    // ---------- Positional Access Operations ----------

    @Nested
    inner class Get {
        @Test
        fun `should return the first element with an int that equals 0`() {
            // GIVEN
            val set: NotEmptySet<Int> = NotEmptySet(1)
            // WHEN
            val element: Int = set[0]
            // THEN
            element assertEquals set.head
        }

        @Test
        fun `should return the second element with an int that equals 1`() {
            // GIVEN
            val tail: Array<Int> = arrayOf(2, 3)
            val set: NotEmptySet<Int> = NotEmptySet(1, *tail)
            // WHEN
            val element: Int = set[1]
            // THEN
            element assertEquals tail[0]
        }

        @Test
        fun `should throw an error with an int that is out of bounds`() {
            // GIVEN
            val set: NotEmptySet<Int> = NotEmptySet(1)
            // WHEN & THEN
            assertFailsWith<IndexOutOfBoundsException> { set[10] }
        }
    }

    // ---------- Conversions ----------

    @Nested
    inner class ArrayToNotEmptySet {
        @Test
        fun `should pass with an array with at least 1 element`() {
            // GIVEN
            val expectedSet: Set<Int> = setOf(1, 2, 3)
            val array: Array<Int> = arrayOf(*expectedSet.toTypedArray(), 1, 2)
            // WHEN
            val set: NotEmptySet<Int> = assertDoesNotThrow(array::toNotEmptySet)
            // THEN
            set.forEachIndexed { index: Int, element: Int ->
                element assertEquals expectedSet.elementAt(index)
            }
        }

        @Test
        fun `should throw an error with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(array::toNotEmptySet)
        }
    }

    @Nested
    inner class ArrayToNotEmptySetOrNull {
        @Test
        fun `should pass with an array with at least 1 element`() {
            // GIVEN
            val expectedSet: NotEmptySet<Int> = NotEmptySet(1, 2, 3)
            val array: Array<Int> = arrayOf(*expectedSet.toTypedArray(), 1, 2)
            // WHEN
            val set: NotEmptySet<Int>? = array.toNotEmptySetOrNull()
            // THEN
            set.assertNotNull().forEachIndexed { index: Int, element: Int ->
                element assertEquals expectedSet.elementAt(index)
            }
        }

        @Test
        fun `should return null with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            // WHEN
            val set: NotEmptySet<Int>? = array.toNotEmptySetOrNull()
            // THEN
            set.assertNull()
        }
    }

    @Nested
    inner class ArrayToNotEmptySetOrElse {
        @Test
        fun `should pass with an array with at least 1 element`() {
            // GIVEN
            val expectedSet: NotEmptySet<Int> = NotEmptySet(1, 2, 3)
            val array: Array<Int> = arrayOf(*expectedSet.toTypedArray(), 1, 2)
            val defaultValue: NotEmptySet<Int> = NotEmptySet(-1, -2, -3)
            // WHEN
            val set: NotEmptySet<Int> =
                array toNotEmptySetOrElse { defaultValue }
            // THEN
            set.forEachIndexed { index: Int, element: Int ->
                element assertEquals expectedSet.elementAt(index)
                element assertNotEquals defaultValue.elementAt(index)
            }
        }

        @Test
        fun `should return the default value with an empty array`() {
            // GIVEN
            val array: Array<Int> = emptyArray()
            val defaultValue: NotEmptySet<Int> = NotEmptySet(-1, -2, -3)
            // WHEN
            val set: NotEmptySet<Int> =
                array toNotEmptySetOrElse { defaultValue }
            // THEN
            set.forEachIndexed { index: Int, element: Int ->
                element assertEquals defaultValue.elementAt(index)
            }
        }
    }

    @Nested
    inner class CollectionToNotEmptySet {
        @Test
        fun `should pass with a collection with at least 1 element`() {
            // GIVEN
            val collection: Collection<Int> = listOf(1, 2, 3)
            // WHEN
            val set: NotEmptySet<Int> =
                assertDoesNotThrow(collection::toNotEmptySet)
            // THEN
            set.forEachIndexed { index: Int, element: Int ->
                element assertEquals collection.elementAt(index)
            }
        }

        @Test
        fun `should throw an error with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptyList()
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(collection::toNotEmptySet)
        }
    }

    @Nested
    inner class CollectionToNotEmptySetOrNull {
        @Test
        fun `should pass with a collection with at least 1 element`() {
            // GIVEN
            val expectedSet: Set<Int> = setOf(1, 2, 3)
            val collection: Collection<Int> = expectedSet.toMutableList().run {
                this += 2
                toList()
            }
            // WHEN
            val set: NotEmptySet<Int>? = collection.toNotEmptySetOrNull()
            // THEN
            set.assertNotNull().forEachIndexed { index: Int, element: Int ->
                element assertEquals expectedSet.elementAt(index)
            }
        }

        @Test
        fun `should return null with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptySet()
            // WHEN
            val set: NotEmptySet<Int>? = collection.toNotEmptySetOrNull()
            // THEN
            set.assertNull()
        }
    }

    @Nested
    inner class CollectionToNotEmptySetOrElse {
        @Test
        fun `should pass with a collection with at least 1 element`() {
            // GIVEN
            val expectedSet: Set<Int> = setOf(1, 2, 3)
            val collection: Collection<Int> = expectedSet.toMutableList().run {
                this += 1
                toList()
            }
            val defaultValue: NotEmptySet<Int> = NotEmptySet(-1, -2, -3)
            // WHEN
            val set: NotEmptySet<Int> =
                collection toNotEmptySetOrElse { defaultValue }
            // THEN
            set.forEachIndexed { index: Int, element: Int ->
                element assertEquals expectedSet.elementAt(index)
                element assertNotEquals defaultValue.elementAt(index)
            }
        }

        @Test
        fun `should return the default value with an empty collection`() {
            // GIVEN
            val collection: Collection<Int> = emptyList()
            val defaultValue: NotEmptySet<Int> = NotEmptySet(-1, -2, -3)
            // WHEN
            val set: NotEmptySet<Int> =
                collection toNotEmptySetOrElse { defaultValue }
            // THEN
            set.forEachIndexed { index: Int, element: Int ->
                element assertEquals defaultValue.elementAt(index)
            }
        }
    }
}
