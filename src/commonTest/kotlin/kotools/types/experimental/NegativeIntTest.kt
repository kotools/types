package kotools.types.experimental

import kotools.types.number.NegativeInt
import kotools.types.number.PositiveInt
import kotools.types.number.toNegativeInt
import kotools.types.number.toPositiveIntOrFailure
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NegativeIntTest {
    @ExperimentalKotoolsTypesApi
    @Test
    fun unaryMinus_should_pass() {
        val number: NegativeInt = NegativeInt.random()
        val actual: PositiveInt = -number
        val expected: PositiveInt = number.toInt()
            .unaryMinus()
            .toPositiveIntOrFailure()
        assertEquals(expected, actual)
    }
}
