package kotools.types.collection

internal class NotEmptyMapKtKotlinSample {
    fun notEmptyMapOf() {
        val map: NotEmptyMap<Char, Int> = notEmptyMapOf('a' to 1, 'b' to 2)
        println(map) // {a=1, b=2}
    } // END
}
