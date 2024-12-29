package kotools.types.experimental

import kotools.types.number.NegativeInt
import kotools.types.number.PositiveInt
import kotools.types.number.toNegativeInt
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NegativeIntCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun unaryMinusOperator() {
        val number: NegativeInt = (-1).toNegativeInt()
            .getOrThrow()
        val result: PositiveInt = -number // or number.unaryMinus()
        assertEquals(expected = "1", actual = "$result")
    }
}
