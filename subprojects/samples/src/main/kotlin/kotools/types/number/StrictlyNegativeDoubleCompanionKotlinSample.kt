package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi

internal class StrictlyNegativeDoubleCompanionKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun create() {
        val result: Result<StrictlyNegativeDouble> = runCatching {
            StrictlyNegativeDouble.create(-23) // TABS: 1
        }
        println(result.isSuccess) // true
    } // END
}
