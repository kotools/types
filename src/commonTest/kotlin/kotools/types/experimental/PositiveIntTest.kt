package kotools.types.experimental

import kotools.types.number.NegativeInt
import kotools.types.number.PositiveInt
import kotools.types.number.toNegativeIntOrFailure
import kotools.types.number.toPositiveInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PositiveIntTest {
    @ExperimentalKotoolsTypesApi
    @Test
    fun unaryMinus_should_pass() {
        val number: PositiveInt = PositiveInt.random()
        val actual: NegativeInt = -number
        val expected: NegativeInt = number.toInt()
            .unaryMinus()
            .toNegativeIntOrFailure()
        assertEquals(expected, actual)
    }
}
