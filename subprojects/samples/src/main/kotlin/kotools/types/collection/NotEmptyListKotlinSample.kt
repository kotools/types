package kotools.types.collection

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class NotEmptyListKotlinSample {
    fun notEmptyListOf() {
        val integers: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        println(integers) // [1, 2, 3]
    } // END

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

    fun toNotEmptyListOnCollection() {
        var collection: Collection<Int> = listOf(1, 2, 3)
        var result: Result<NotEmptyList<Int>> = collection.toNotEmptyList()
        println(result) // Success([1, 2, 3])

        collection = emptyList()
        result = collection.toNotEmptyList()
        println(result) // Failure(IllegalArgumentException)
    } // END

    fun toNotEmptyListOnMutableCollection() {
        val original: MutableCollection<Int> = mutableListOf(1, 2, 3)
        val notEmptyList: NotEmptyList<Int> = original.toNotEmptyList()
            .getOrThrow() // TABS: 1
        println(original) // [1, 2, 3]
        println(notEmptyList) // [1, 2, 3]

        original.clear()
        println(original) // []
        println(notEmptyList) // [1, 2, 3]
    } // END
}
