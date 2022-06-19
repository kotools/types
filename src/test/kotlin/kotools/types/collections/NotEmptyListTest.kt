package kotools.types.collections

import io.github.kotools.assert.assertEquals
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertFalse

class NotEmptyListTest {
    @Nested
    inner class Create {
        @Test
        fun `should return a singleton list`() {
            // GIVEN
            val head = 1
            // WHEN
            val list: NotEmptyList<Int> = notEmptyList(head)
            // THEN
            assertFalse(list.isEmpty())
            list.size assertEquals 1
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
            val list: NotEmptyList<Int> = notEmptyList(head, *tail)
            // THEN
            assertFalse(list.isEmpty())
            list.size assertEquals 3
            list.forEachIndexed { index: Int, element: Int ->
                element assertEquals expectedList[index]
            }
        }
    }
}
