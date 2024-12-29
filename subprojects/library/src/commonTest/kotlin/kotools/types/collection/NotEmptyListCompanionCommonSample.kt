package kotools.types.collection

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class NotEmptyListCompanionCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createWithCollection() {
        val collection: Collection<Int> = listOf(1, 2, 3)
        val isSuccess: Boolean = try {
            NotEmptyList.create(collection)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createWithMutableCollection() {
        val original: MutableCollection<Int> = mutableListOf(1, 2, 3)
        val notEmptyList: NotEmptyList<Int> = NotEmptyList.create(original)
        assertEquals(expected = "$original", actual = "$notEmptyList")
        original.clear()
        assertNotEquals(illegal = "$original", actual = "$notEmptyList")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNullWithCollection() {
        val collection: Collection<Int> = listOf(1, 2, 3)
        val actual: NotEmptyList<Int>? = NotEmptyList.createOrNull(collection)
        assertNotNull(actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNullWithMutableCollection() {
        val original: MutableCollection<Int> = mutableListOf(1, 2, 3)
        val notEmptyList: NotEmptyList<Int>? =
            NotEmptyList.createOrNull(original)
        assertEquals(expected = "$original", actual = "$notEmptyList")
        original.clear()
        assertNotEquals(illegal = "$original", actual = "$notEmptyList")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun of() {
        val integers: NotEmptyList<Int> = NotEmptyList.of(1, 2, 3)
        assertEquals(expected = "[1, 2, 3]", actual = "$integers")
    }
}
