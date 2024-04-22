package kotools.types.experimental

import kotools.types.number.AnyInt

internal class AnyIntKotlinSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    fun unaryMinusOperator() {
        val number = AnyInt(1)
        val result: AnyInt = -number // or number.unaryMinus()
        println(result) // -1
    } // END
}
