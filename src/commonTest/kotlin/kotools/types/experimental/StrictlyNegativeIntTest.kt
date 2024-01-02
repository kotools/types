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

    @ExperimentalKotoolsTypesApi
    @Test
    fun range_should_go_from_Int_MIN_VALUE_included_to_minus_1_included() {
        val actual: NotEmptyRange<StrictlyNegativeInt> =
            StrictlyNegativeInt.range
        assertTrue { actual.start is InclusiveBound }
        val expectedStartValue: StrictlyNegativeInt = Int.MIN_VALUE
            .toStrictlyNegativeInt()
            .getOrThrow()
        assertEquals(expectedStartValue, actual.start.value)
        assertTrue { actual.end is InclusiveBound }
        val expectedEndValue: StrictlyNegativeInt = (-1).toStrictlyNegativeInt()
            .getOrThrow()
        assertEquals(expectedEndValue, actual.end.value)
    }
}
