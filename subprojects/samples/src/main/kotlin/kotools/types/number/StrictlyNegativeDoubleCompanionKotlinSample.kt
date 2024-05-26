package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal class StrictlyNegativeDoubleCompanionKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun create() {
        val number: Number = -23
        val isSuccess: Boolean = try {
            StrictlyNegativeDouble.create(number) // TABS: 1
            true // TABS: 1
        } catch (exception: IllegalArgumentException) {
            false // TABS: 1
        }
        println(isSuccess) // true
    } // END
}
