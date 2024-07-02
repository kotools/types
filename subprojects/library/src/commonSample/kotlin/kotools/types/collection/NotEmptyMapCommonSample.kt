package kotools.types.collection

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyPositiveInt
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NotEmptyMapCommonSample {
    @Test
    fun serialization() {
        val map: NotEmptyMap<Char, Int> = notEmptyMapOf('a' to 1, 'b' to 2)
        val encoded: String = Json.encodeToString(map)
        assertEquals(expected = "{\"a\":1,\"b\":2}", actual = encoded)
        val decoded: NotEmptyMap<Char, Int> = Json.decodeFromString(encoded)
        assertEquals(expected = map, actual = decoded)
    }

    @Test
    fun head() {
        val expected: Pair<Char, Int> = 'a' to 1
        val actual: Pair<Char, Int> = notEmptyMapOf(expected, 'b' to 2).head
        assertEquals(expected, actual)
    }

    @Test
    fun tail() {
        val expected: NotEmptyMap<Char, Int> = notEmptyMapOf('b' to 2, 'c' to 3)
        val actual: NotEmptyMap<Char, Int>? = expected.toMap()
            .toList()
            .toTypedArray()
            .let { notEmptyMapOf('a' to 1, *it) }
            .tail
        assertEquals(expected, actual)
    }

    @Test
    fun entries() {
        val map: NotEmptyMap<Char, Int> = notEmptyMapOf('a' to 1, 'b' to 2)
        val entries: NotEmptySet<Map.Entry<Char, Int>> = map.entries
        assertEquals(expected = "[a=1, b=2]", actual = "$entries")
    }

    @Test
    fun keys() {
        val map: NotEmptyMap<Char, Int> = notEmptyMapOf('a' to 1, 'b' to 2)
        val keys: NotEmptySet<Char> = map.keys
        assertEquals(expected = "[a, b]", actual = "$keys")
    }

    @Test
    fun values() {
        val map: NotEmptyMap<Char, Int> = notEmptyMapOf('a' to 1, 'b' to 2)
        val values: NotEmptyList<Int> = map.values
        assertEquals(expected = "[1, 2]", actual = "$values")
    }

    @Test
    fun size() {
        val map: NotEmptyMap<Char, Int> = notEmptyMapOf('a' to 1, 'b' to 2)
        val actual: StrictlyPositiveInt = map.size
        val expected: StrictlyPositiveInt = 2.toStrictlyPositiveInt()
            .getOrThrow()
        assertEquals(expected, actual)
    }

    @Test
    fun toMap() {
        val notEmptyMap: NotEmptyMap<Char, Int> =
            notEmptyMapOf('a' to 1, 'b' to 2, 'c' to 3)
        val map: Map<Char, Int> = notEmptyMap.toMap()
        assertEquals(expected = "{a=1, b=2, c=3}", actual = "$map")
    }

    @Test
    fun toStringOverride() {
        val notEmptyMap: NotEmptyMap<Char, Int> =
            notEmptyMapOf('a' to 1, 'b' to 2, 'c' to 3)
        assertEquals(expected = "{a=1, b=2, c=3}", actual = "$notEmptyMap")
        val map: Map<Char, Int> = notEmptyMap.toMap()
        assertEquals(expected = "$map", actual = "$notEmptyMap")
    }
}
