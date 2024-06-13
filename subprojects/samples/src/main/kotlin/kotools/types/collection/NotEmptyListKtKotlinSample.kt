package kotools.types.collection

internal class NotEmptyListKtKotlinSample {
    fun notEmptyListOf() {
        val integers: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        println(integers) // [1, 2, 3]
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
            .getOrThrow()
        println(original) // [1, 2, 3]
        println(notEmptyList) // [1, 2, 3]

        original.clear()
        println(original) // []
        println(notEmptyList) // [1, 2, 3]
    } // END
}
