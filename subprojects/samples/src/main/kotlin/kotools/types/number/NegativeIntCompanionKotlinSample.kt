package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal class NegativeIntCompanionKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun create() {
        val number: NegativeInt = NegativeInt.create(-7)
        println(number) // -7
    } // END
}
