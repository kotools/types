package kotools.types.experimental

import kotools.types.number.NonZeroInt
import kotools.types.number.toNonZeroInt
import kotlin.test.Test
import kotlin.test.assertEquals

internal class NonZeroIntCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun unaryMinusOperator() {
        val number: NonZeroInt = 1.toNonZeroInt()
            .getOrThrow()
        val result: NonZeroInt = -number // or number.unaryMinus()
        assertEquals(expected = "-1", actual = "$result")
    }
}
