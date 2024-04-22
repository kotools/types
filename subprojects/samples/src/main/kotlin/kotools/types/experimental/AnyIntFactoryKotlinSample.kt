package kotools.types.experimental

internal class AnyIntFactoryKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun constructorLikeInt() {
        val number = AnyInt(1)
        println(number) // 1
    } // END
}
