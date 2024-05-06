package kotools.types.collection

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class NotEmptyListKotlinSample {
    fun serialization() {
        val string: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        val encoded: String = Json.encodeToString(string)
        println(encoded) // [1,2,3]
        val decoded: NotEmptyList<Int> = Json.decodeFromString(encoded)
        println(decoded == string) // true
    } // END

    fun toList() {
        val notEmptyList: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        val list: List<Int> = notEmptyList.toList()
        println(list) // [1, 2, 3]
    } // END
}
