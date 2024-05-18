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
}
