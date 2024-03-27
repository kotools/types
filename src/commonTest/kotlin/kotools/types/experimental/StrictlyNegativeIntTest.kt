package kotools.types.experimental

import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyNegativeInt
import kotools.types.number.toStrictlyPositiveInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StrictlyNegativeIntTest {
    @ExperimentalKotoolsTypesApi
    @Test
    fun unaryMinus_should_pass() {
        val number: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val actual: StrictlyPositiveInt = -number
        val expected: StrictlyPositiveInt = number.toInt()
            .unaryMinus()
            .toStrictlyPositiveInt()
            .getOrThrow()
        assertEquals(expected, actual)
    }

}
