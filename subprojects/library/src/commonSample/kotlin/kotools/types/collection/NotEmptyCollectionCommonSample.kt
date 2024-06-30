package kotools.types.collection

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class NotEmptyCollectionCommonSample {
    @Test
    fun head() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
        val actual: Int = collection.head
        val expected = 1
        assertEquals(expected, actual)
    }

    @Test
    fun tail() {
        var collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
        var actual: NotEmptyCollection<Int>? = collection.tail
        val expected: NotEmptyCollection<Int> = notEmptyListOf(2, 3)
        assertEquals(expected, actual)

        collection = notEmptyListOf(-1)
        actual = collection.tail
        assertNull(actual)
    }

    @Test
    fun toStringOverride() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
        val actual = "$collection"
        val expected = "${listOf(1, 2, 3)}"
        assertEquals(expected, actual)
    }
}
