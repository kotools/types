package kotools.types.collection

internal class NotEmptyListKotlinSample {
    fun notEmptyListOf() {
        val integers: NotEmptyList<Int> = notEmptyListOf(1, 2, 3)
        println(integers) // [1, 2, 3]
    } // END
}
