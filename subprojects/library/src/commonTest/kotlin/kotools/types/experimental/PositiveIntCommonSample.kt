package kotools.types.experimental

import kotools.types.number.NegativeInt
import kotools.types.number.PositiveInt
import kotools.types.number.toPositiveInt
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PositiveIntCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun unaryMinusOperator() {
        val number: PositiveInt = 1.toPositiveInt()
            .getOrThrow()
        val result: NegativeInt = -number // or number.unaryMinus()
        assertEquals(expected = "-1", actual = "$result")
    }
}
