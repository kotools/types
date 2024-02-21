package kotools.types.experimental

import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyNegativeInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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

class StrictlyPositiveIntCompanionTest {
    @ExperimentalKotoolsTypesApi
    @Test
    fun range_should_go_from_1_included_to_Int_MAX_VALUE_included() {
        val actual: NotEmptyRange<StrictlyPositiveInt> =
            StrictlyPositiveInt.range
        assertTrue(actual.start is InclusiveBound)
        val expectedStartValue: StrictlyPositiveInt =
            StrictlyPositiveInt.create(1)
        assertEquals(expectedStartValue, actual.start.value)
        assertTrue(actual.end is InclusiveBound)
        val expectedEndValue: StrictlyPositiveInt =
            StrictlyPositiveInt.create(Int.MAX_VALUE)
        assertEquals(expectedEndValue, actual.end.value)
    }
}
