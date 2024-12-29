package kotools.types.collection

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class NotEmptyMapCompanionCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createWithMap() {
        val map: Map<Char, Int> = mapOf('a' to 1, 'b' to 2)
        val isSuccess: Boolean = try {
            NotEmptyMap.create(map)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createWithMutableMap() {
        val original: MutableMap<Char, Int> = mutableMapOf('a' to 1, 'b' to 2)
        val notEmptyMap: NotEmptyMap<Char, Int> = NotEmptyMap.create(original)
        assertEquals(expected = "$original", actual = "$notEmptyMap")
        original.clear()
        assertNotEquals(illegal = "$original", actual = "$notEmptyMap")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNullWithMap() {
        val map: Map<Char, Int> = mapOf('a' to 1, 'b' to 2)
        val actual: NotEmptyMap<Char, Int>? = NotEmptyMap.createOrNull(map)
        assertNotNull(actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNullWithMutableMap() {
        val original: MutableMap<Char, Int> = mutableMapOf('a' to 1, 'b' to 2)
        val notEmptyMap: NotEmptyMap<Char, Int>? =
            NotEmptyMap.createOrNull(original)
        assertEquals(expected = "$original", actual = "$notEmptyMap")
        original.clear()
        assertNotEquals(illegal = "$original", actual = "$notEmptyMap")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun of() {
        val map: NotEmptyMap<Char, Int> = NotEmptyMap.of('a' to 1, 'b' to 2)
        assertEquals(expected = "{a=1, b=2}", actual = "$map")
    }
}
