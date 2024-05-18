package kotools.types.collection

internal class NotEmptyCollectionKotlinSample {
    fun head() {
        val collection: NotEmptyCollection<Int> = notEmptyListOf(1, 2, 3)
        val result: Int = collection.head
        println(result) // 1
    } // END
}
