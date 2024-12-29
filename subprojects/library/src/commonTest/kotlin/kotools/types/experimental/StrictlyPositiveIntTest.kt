package kotools.types.experimental

import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyNegativeInt
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

class StrictlyPositiveIntTest {
    @ExperimentalKotoolsTypesApi
    @Test
    fun unaryMinus_should_pass() {
        val number: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val actual: StrictlyNegativeInt = -number
        val expected: StrictlyNegativeInt = number.toInt()
            .unaryMinus()
            .toStrictlyNegativeInt()
            .getOrThrow()
        assertEquals(expected, actual)
    }
}
