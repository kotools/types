package kotools.types.experimental

import kotools.types.number.NonZeroInt
import kotools.types.number.toNonZeroInt

internal class NonZeroIntKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun unaryMinusOperator() {
        val number: NonZeroInt = 1.toNonZeroInt()
            .getOrThrow() // TABS: 1
        val result: NonZeroInt = -number // or number.unaryMinus()
        println(result) // -1
    } // END
}
