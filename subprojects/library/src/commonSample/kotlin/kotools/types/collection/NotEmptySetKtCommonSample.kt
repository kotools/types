package kotools.types.collection

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class NotEmptySetKtCommonSample {
    @Test
    fun notEmptySetOf() {
        val integers: NotEmptySet<Int> = notEmptySetOf(1, 2, 3, 1)
        assertEquals(expected = "[1, 2, 3]", actual = "$integers")
    }

    @Test
    fun toNotEmptySetOnCollection() {
        var collection: Collection<Int> = setOf(1, 2, 3, 1)
        var notEmptySet: NotEmptySet<Int>? = collection.toNotEmptySet()
            .getOrNull()
        assertNotNull(notEmptySet)
        collection = emptySet()
        notEmptySet = collection.toNotEmptySet()
            .getOrNull()
        assertNull(notEmptySet)
    }

    @Test
    fun toNotEmptySetOnMutableCollection() {
        val original: MutableCollection<Int> = mutableSetOf(1, 2, 3)
        val notEmptySet: NotEmptySet<Int> = original.toNotEmptySet()
            .getOrThrow()
        assertEquals(expected = "$original", actual = "$notEmptySet")
        original.clear()
        assertNotEquals(illegal = "$original", actual = "$notEmptySet")
    }
}
