package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal class NegativeIntCompanionKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun create() {
        val number: NegativeInt = NegativeInt.create(-7)
        println(number) // -7
    } // END

    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun createOrNull() {
        val number: NegativeInt? = NegativeInt.createOrNull(-7)
        println(number) // -7
    } // END
}
