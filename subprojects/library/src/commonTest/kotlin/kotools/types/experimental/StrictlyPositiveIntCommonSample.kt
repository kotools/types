package kotools.types.experimental

import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyPositiveInt
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

internal class StrictlyPositiveIntCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun unaryMinusOperator() {
        val number: StrictlyPositiveInt = 1.toStrictlyPositiveInt()
            .getOrThrow()
        val result: StrictlyNegativeInt = -number // or number.unaryMinus()
        assertEquals(expected = "-1", actual = "$result")
    }
}
