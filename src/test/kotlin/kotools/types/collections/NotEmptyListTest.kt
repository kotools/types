package kotools.types.collections

import io.github.kotools.assert.assertEquals
import kotools.types.number.StrictlyPositiveInt
import org.junit.jupiter.api.Nested
import kotlin.test.Test
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
        @Test
        fun `should return the first element with an int that equals 0`() {
            // GIVEN
            val head = 1
            val list: NotEmptyList<Int> = NotEmptyList(head, 2)
            val index = 0
            // WHEN
            val element: Int = list[index]
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
            val element: Int = list[index]
            // THEN
            element assertEquals expectedElement
        }
    }
}
