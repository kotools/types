package kotools.types.experimental

import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyNegativeInt

internal class StrictlyNegativeIntKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun unaryMinusOperator() {
        val number: StrictlyNegativeInt = (-1).toStrictlyNegativeInt()
            .getOrThrow() // TABS: 1
        val result: StrictlyPositiveInt = -number // or number.unaryMinus()
        println(result) // 1
    } // END
}
