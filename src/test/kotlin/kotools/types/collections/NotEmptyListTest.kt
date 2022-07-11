package kotools.types.collections

import io.github.kotools.assert.assertEquals
import io.github.kotools.assert.assertNotNull
import io.github.kotools.assert.assertNull
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

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

    // ---------- Query operations ----------

    @Nested
    inner class Size {
        @Test
        fun `should return 1 with a singleton list`() {
            // GIVEN
            val list: NotEmptyList<Int> = NotEmptyList(1)
            // WHEN
            val size: Int = list.size
            // THEN
            size assertEquals 1
        }

        @Test
        fun `should return 3 with a list of 3 elements`() {
            // GIVEN
            val list: NotEmptyList<Int> = NotEmptyList(1, 2, 3)
            // WHEN
            val size: Int = list.size
            // THEN
            size assertEquals 3
        }
    }

    @Nested
    inner class TypedSize {
        @Test
        fun `should return 1 as a strictly positive int with a singleton list`() {
            // GIVEN
            val list: NotEmptyList<Int> = NotEmptyList(1)
            // WHEN
            val size: StrictlyPositiveInt = list.typedSize
            // THEN
            size.value assertEquals 1
        }

        @Test
        fun `should return 3 as a strictly positive int with a list of 3 elements`() {
            // GIVEN
            val list: NotEmptyList<Int> = NotEmptyList(1, 2, 3)
            // WHEN
            val size: StrictlyPositiveInt = list.typedSize
            // THEN
            size.value assertEquals 3
        }
    }

    @Nested
    inner class IsEmpty {
        @Test
        fun `should always return false`() {
            // GIVEN
            val list: NotEmptyList<Int> = NotEmptyList(1)
            // WHEN & THEN
            assertFalse(block = list::isEmpty)
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

        // ---------- PositiveInt ----------

        @Test
        fun `should return the first element with a positive int that equals 0`() {
            // GIVEN
            val head = 1
            val list: NotEmptyList<Int> = NotEmptyList(head, 2)
            val index = PositiveInt(0)
            // WHEN
            val element: Int = assertDoesNotThrow { list[index] }
            // THEN
            element assertEquals head
        }

        @Test
        fun `should return the second element with a positive int that equals 1`() {
            // GIVEN
            val expectedElement = 2
            val list: NotEmptyList<Int> = NotEmptyList(1, expectedElement)
            val index = PositiveInt(1)
            // WHEN
            val element: Int = assertDoesNotThrow { list[index] }
            // THEN
            element assertEquals expectedElement
        }

        @Test
        fun `should throw an error with a positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyList<Int> = NotEmptyList(1)
            val index = PositiveInt(10)
            // WHEN & THEN
            assertFailsWith<IndexOutOfBoundsException> { list[index] }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return the second element with a strictly positive int that equals 1`() {
            // GIVEN
            val expectedElement = 2
            val list: NotEmptyList<Int> = NotEmptyList(1, expectedElement)
            val index = StrictlyPositiveInt(1)
            // WHEN
            val element: Int = assertDoesNotThrow { list[index] }
            // THEN
            element assertEquals expectedElement
        }

        @Test
        fun `should throw an error with a strictly positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyList<Int> = NotEmptyList(1)
            val index = StrictlyPositiveInt(10)
            // WHEN & THEN
            assertFailsWith<IndexOutOfBoundsException> { list[index] }
        }
    }

    @Nested
    inner class GetOrNull {
        // ---------- PositiveInt ----------

        @Test
        fun `should return the first element with a positive int that equals 0`() {
            // GIVEN
            val head = 1
            val list: NotEmptyList<Int> = NotEmptyList(head, 2)
            val index = PositiveInt(0)
            // WHEN
            val element: Int? = list getOrNull index
            // THEN
            element.assertNotNull() assertEquals head
        }

        @Test
        fun `should return the second element with a positive int that equals 1`() {
            // GIVEN
            val expectedElement = 2
            val list: NotEmptyList<Int> = NotEmptyList(1, expectedElement)
            val index = PositiveInt(1)
            // WHEN
            val element: Int? = list getOrNull index
            // THEN
            element.assertNotNull() assertEquals expectedElement
        }

        @Test
        fun `should return null with a positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyList<Int> = NotEmptyList(1)
            val index = PositiveInt(10)
            // WHEN
            val element: Int? = list getOrNull index
            // THEN
            element.assertNull()
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return the second element with a strictly positive int that equals 1`() {
            // GIVEN
            val expectedElement = 2
            val list: NotEmptyList<Int> = NotEmptyList(1, expectedElement)
            val index = StrictlyPositiveInt(1)
            // WHEN
            val element: Int? = list getOrNull index
            // THEN
            element.assertNotNull() assertEquals expectedElement
        }

        @Test
        fun `should return null with a strictly positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyList<Int> = NotEmptyList(1)
            val index = StrictlyPositiveInt(10)
            // WHEN
            val element: Int? = list getOrNull index
            // THEN
            element.assertNull()
        }
    }

    @Nested
    inner class GetOrElse {
        // ---------- PositiveInt ----------

        @Test
        fun `should return the first element with a positive int that equals 0`() {
            // GIVEN
            val head = 1
            val list: NotEmptyList<Int> = NotEmptyList(head, 2)
            val index = PositiveInt(0)
            val defaultValue = -1
            // WHEN
            val element: Int = list.getOrElse(index) { defaultValue }
            // THEN
            element assertEquals head
        }

        @Test
        fun `should return the second element with a positive int that equals 1`() {
            // GIVEN
            val expectedElement = 2
            val list: NotEmptyList<Int> = NotEmptyList(1, expectedElement)
            val index = PositiveInt(1)
            val defaultValue = -1
            // WHEN
            val element: Int = list.getOrElse(index) { defaultValue }
            // THEN
            element assertEquals expectedElement
        }

        @Test
        fun `should return the default value with a positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyList<Int> = NotEmptyList(1)
            val index = PositiveInt(10)
            val defaultValue = -1
            // WHEN
            val element: Int = list.getOrElse(index) { defaultValue }
            // THEN
            element assertEquals defaultValue
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return the second element with a strictly positive int that equals 1`() {
            // GIVEN
            val expectedElement = 2
            val list: NotEmptyList<Int> = NotEmptyList(1, expectedElement)
            val index = StrictlyPositiveInt(1)
            val defaultValue = -1
            // WHEN
            val element: Int = list.getOrElse(index) { defaultValue }
            // THEN
            element assertEquals expectedElement
        }

        @Test
        fun `should return the default value with a strictly positive int that is out of bounds`() {
            // GIVEN
            val list: NotEmptyList<Int> = NotEmptyList(1)
            val index = StrictlyPositiveInt(10)
            val defaultValue = -1
            // WHEN
            val element: Int = list.getOrElse(index) { defaultValue }
            // THEN
            element assertEquals defaultValue
        }
    }
}
