package kotools.types.experimental

import kotools.types.number.NegativeInt
import kotools.types.number.PositiveInt
import kotools.types.number.toPositiveInt

internal class PositiveIntKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun unaryMinusOperator() {
        val number: PositiveInt = 1.toPositiveInt()
            .getOrThrow()
        val result: NegativeInt = -number // or number.unaryMinus()
        println(result) // -1
    } // END
}
