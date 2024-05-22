package kotools.types.collection

internal class NotEmptySetKtKotlinSample {
    fun notEmptySetOf() {
        val integers: NotEmptySet<Int> = notEmptySetOf(1, 2, 3, 1)
        println(integers) // [1, 2, 3]
    } // END

    fun toNotEmptySetOnCollection() {
        var collection: Collection<Int> = setOf(1, 2, 3, 1)
        var result: Result<NotEmptySet<Int>> = collection.toNotEmptySet()
        println(result) // Success([1, 2, 3])

        collection = emptySet()
        result = collection.toNotEmptySet()
        println(result) // Failure(IllegalArgumentException)
    } // END

    fun toNotEmptySetOnMutableCollection() {
        val original: MutableCollection<Int> = mutableSetOf(1, 2, 3)
        val notEmptySet: NotEmptySet<Int> = original.toNotEmptySet()
            .getOrThrow() // TABS: 1
        println(original) // [1, 2, 3]
        println(notEmptySet) // [1, 2, 3]

        original.clear()
        println(original) // []
        println(notEmptySet) // [1, 2, 3]
    } // END
}
