package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal class NonZeroIntCompanionKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun create() {
        val number: NonZeroInt = NonZeroInt.create(23)
        println(number) // 23
    } // END
}
