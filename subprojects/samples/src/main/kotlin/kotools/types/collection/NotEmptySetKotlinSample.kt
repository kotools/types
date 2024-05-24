package kotools.types.collection

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class NotEmptySetKotlinSample {
    fun serialization() {
        val string: NotEmptySet<Int> = notEmptySetOf(1, 2, 3)
        val encoded: String = Json.encodeToString(string)
        println(encoded) // [1,2,3]
        val decoded: NotEmptySet<Int> = Json.decodeFromString(encoded)
        println(decoded == string) // true
    } // END

    fun toSet() {
        val notEmptySet: NotEmptySet<Int> = notEmptySetOf(1, 2, 3, 1)
        val set: Set<Int> = notEmptySet.toSet()
        println(set) // [1, 2, 3]
    } // END
}
