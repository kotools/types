package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal class StrictlyPositiveIntCompanionKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun create() {
        val number: StrictlyPositiveInt = StrictlyPositiveInt.create(42)
        println(number) // 42
    } // END
}
