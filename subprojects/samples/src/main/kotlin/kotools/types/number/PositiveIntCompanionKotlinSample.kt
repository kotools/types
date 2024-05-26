package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal class PositiveIntCompanionKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun create() {
        val number: PositiveInt = PositiveInt.create(23)
        println(number) // 23
    } // END

    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun createOrNull() {
        val number: PositiveInt? = PositiveInt.createOrNull(23)
        println(number) // 23
    } // END
}
