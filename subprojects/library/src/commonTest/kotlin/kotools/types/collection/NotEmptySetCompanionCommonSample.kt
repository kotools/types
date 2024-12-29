package kotools.types.collection

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class NotEmptySetCompanionCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createWithCollection() {
        val collection: Collection<Int> = setOf(1, 2, 3)
        val elements: NotEmptySet<Int> = NotEmptySet.create(collection)
        assertEquals(expected = "[1, 2, 3]", actual = "$elements")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createWithMutableCollection() {
        val original: MutableCollection<Int> = mutableSetOf(1, 2, 3)
        val integers: NotEmptySet<Int> = NotEmptySet.create(original)
        assertEquals(expected = "$original", actual = "$integers")
        original.clear()
        assertNotEquals(illegal = "$original", actual = "$integers")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNullWithCollection() {
        val collection: Collection<Int> = setOf(1, 2, 3)
        val elements: NotEmptySet<Int>? = NotEmptySet.createOrNull(collection)
        assertEquals(expected = "[1, 2, 3]", actual = "$elements")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNullWithMutableCollection() {
        val original: MutableCollection<Int> = mutableSetOf(1, 2, 3)
        val integers: NotEmptySet<Int>? = NotEmptySet.createOrNull(original)
        assertEquals(expected = "$original", actual = "$integers")
        original.clear()
        assertNotEquals(illegal = "$original", actual = "$integers")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun of() {
        val integers: NotEmptySet<Int> = NotEmptySet.of(1, 2, 3, 1)
        assertEquals(expected = "[1, 2, 3]", actual = "$integers")
    }
}
