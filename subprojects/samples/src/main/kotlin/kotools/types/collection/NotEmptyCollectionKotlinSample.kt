package kotools.types.collection

internal class NotEmptyCollectionKotlinSample {
    fun head() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
        val result: Int = collection.head
        println(result) // 1
    } // END

    fun tail() {
        var collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
        var result: NotEmptyCollection<Int>? = collection.tail
        println(result) // [2, 3]

        collection = notEmptyListOf(-1)
        result = collection.tail
        println(result) // null
    } // END

    fun toStringOverride() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
        println(collection) // [1, 2, 3]

        val list: Iterable<Int> = listOf(1, 2, 3)
        println("$collection" == "$list") // true
    } // END
}
