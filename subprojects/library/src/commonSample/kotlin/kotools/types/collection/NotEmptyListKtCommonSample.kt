package kotools.types.collection

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class NotEmptyListKtCommonSample {
    @Test
    fun notEmptyListOf() {
        val integers: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        val actual: List<Int> = integers.toList()
        val expected: List<Int> = listOf(1, 2, 3)
        assertContentEquals(expected, actual)
    }

    @Test
    fun toNotEmptyListOnCollection() {
        val collection: Collection<Int> = listOf(1, 2, 3)
        val result: Result<NotEmptyList<Int>> = collection.toNotEmptyList()
        val actual: List<Int> = result.getOrThrow()
            .toList()
        assertContentEquals(expected = collection, actual)
    }

    @Test
    fun toNotEmptyListOnMutableCollection() {
        val original: MutableCollection<Int> = mutableListOf(1, 2, 3)
        val notEmptyList: NotEmptyList<Int> = original.toNotEmptyList()
            .getOrThrow()
        assertEquals(expected = "$original", actual = "$notEmptyList")
        original.clear()
        assertNotEquals(illegal = "$original", actual = "$notEmptyList")
    }
}
