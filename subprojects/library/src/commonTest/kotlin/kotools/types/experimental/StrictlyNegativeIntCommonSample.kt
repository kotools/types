package kotools.types.experimental

import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyNegativeInt
import kotlin.test.Test
import kotlin.test.assertEquals

internal class StrictlyNegativeIntCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun unaryMinusOperator() {
        val number: StrictlyNegativeInt = (-1).toStrictlyNegativeInt()
            .getOrThrow()
        val result: StrictlyPositiveInt = -number // or number.unaryMinus()
        assertEquals(expected = "1", actual = "$result")
    }
}
