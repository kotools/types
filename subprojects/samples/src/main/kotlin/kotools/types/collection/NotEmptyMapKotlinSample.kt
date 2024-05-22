package kotools.types.collection

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class NotEmptyMapKotlinSample {
    fun serialization() {
        val map: NotEmptyMap<Char, Int> = notEmptyMapOf('a' to 1, 'b' to 2)
        val encoded: String = Json.encodeToString(map)
        println(encoded) // {"a":1,"b":2}
        val decoded: NotEmptyMap<Char, Int> = Json.decodeFromString(encoded)
        println(decoded == map) // true
    } // END

    fun head() {
        val map: NotEmptyMap<Char, Int> = notEmptyMapOf('a' to 1, 'b' to 2)
        val head: Pair<Char, Int> = map.head
        println(head) // (a, 1)
    } // END

    fun tail() {
        val map: NotEmptyMap<Char, Int> =
            notEmptyMapOf('a' to 1, 'b' to 2, 'c' to 3) // TABS: 1
        val tail: NotEmptyMap<Char, Int>? = map.tail
        println(tail) // {b=2, c=3}
    } // END

    fun entries() {
        val map: NotEmptyMap<Char, Int> = notEmptyMapOf('a' to 1, 'b' to 2)
        val entries: NotEmptySet<Map.Entry<Char, Int>> = map.entries
        println(entries) // [a=1, b=2]
    } // END

    fun keys() {
        val map: NotEmptyMap<Char, Int> = notEmptyMapOf('a' to 1, 'b' to 2)
        val keys: NotEmptySet<Char> = map.keys
        println(keys) // [a, b]
    } // END

    fun values() {
        val map: NotEmptyMap<Char, Int> = notEmptyMapOf('a' to 1, 'b' to 2)
        val values: NotEmptyList<Int> = map.values
        println(values) // [1, 2]
    } // END
}
