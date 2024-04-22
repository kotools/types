package kotools.types.experimental

import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyPositiveInt

internal class StrictlyPositiveIntKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun unaryMinusOperator() {
        val number: StrictlyPositiveInt = 1.toStrictlyPositiveInt()
            .getOrThrow() // TABS: 1
        val result: StrictlyNegativeInt = -number // or number.unaryMinus()
        println(result) // -1
    } // END
}
