package kotools.types.collection

internal class NotEmptySetKtKotlinSample {
    fun notEmptySetOf() {
        val integers: NotEmptySet<Int> = notEmptySetOf(1, 2, 3, 1)
        println(integers) // [1, 2, 3]
    } // END
}
