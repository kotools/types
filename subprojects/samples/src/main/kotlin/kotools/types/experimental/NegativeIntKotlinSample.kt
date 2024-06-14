package kotools.types.experimental

import kotools.types.number.NegativeInt
import kotools.types.number.PositiveInt
import kotools.types.number.toNegativeInt

internal class NegativeIntKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun unaryMinusOperator() {
        val number: NegativeInt = (-1).toNegativeInt()
            .getOrThrow()
        val result: PositiveInt = -number // or number.unaryMinus()
        println(result) // 1
    } // END
}
