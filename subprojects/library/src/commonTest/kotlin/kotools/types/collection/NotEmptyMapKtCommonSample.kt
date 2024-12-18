package kotools.types.collection

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class NotEmptyMapKtCommonSample {
    @Test
    fun notEmptyMapOf() {
        val map: NotEmptyMap<Char, Int> = notEmptyMapOf('a' to 1, 'b' to 2)
        assertEquals(expected = "{a=1, b=2}", actual = "$map")
    }

    @Test
    fun toNotEmptyMapOnMap() {
        var map: Map<Char, Int> = mapOf('a' to 1, 'b' to 2)
        var notEmptyMap: NotEmptyMap<Char, Int>? = map.toNotEmptyMap()
            .getOrNull()
        assertNotNull(notEmptyMap)
        map = emptyMap()
        notEmptyMap = map.toNotEmptyMap()
            .getOrNull()
        assertNull(notEmptyMap)
    }

    @Test
    fun toNotEmptyMapOnMutableMap() {
        val original: MutableMap<Char, Int> = mutableMapOf('a' to 1, 'b' to 2)
        val notEmptyMap: NotEmptyMap<Char, Int> = original.toNotEmptyMap()
            .getOrThrow()
        assertEquals(expected = "$original", actual = "$notEmptyMap")
        original.clear()
        assertNotEquals(illegal = "$original", actual = "$notEmptyMap")
    }
}
