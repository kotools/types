package kotools.types.collection

internal class NotEmptyMapKtKotlinSample {
    fun notEmptyMapOf() {
        val map: NotEmptyMap<Char, Int> = notEmptyMapOf('a' to 1, 'b' to 2)
        println(map) // {a=1, b=2}
    } // END

    fun toNotEmptyMapOnMap() {
        var map: Map<Char, Int> = mapOf('a' to 1, 'b' to 2)
        var result: Result<NotEmptyMap<Char, Int>> = map.toNotEmptyMap()
        println(result) // Success({a=1, b=2})

        map = emptyMap()
        result = map.toNotEmptyMap()
        println(result) // Failure(IllegalArgumentException)
    } // END

    fun toNotEmptyMapOnMutableMap() {
        val original: MutableMap<Char, Int> = mutableMapOf('a' to 1, 'b' to 2)
        val notEmptyMap: NotEmptyMap<Char, Int> = original.toNotEmptyMap()
            .getOrThrow()
        println(original) // {a=1, b=2}
        println(notEmptyMap) // {a=1, b=2}

        original.clear()
        println(original) // {}
        println(notEmptyMap) // {a=1, b=2}
    } // END
}
