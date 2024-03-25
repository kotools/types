package kotools.types.experimental

import kotools.types.internal.unexpectedCreationFailure
import kotools.types.number.NonZeroInt
import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toNonZeroInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class NonZeroIntTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun unaryMinus_should_pass() {
        val number: NonZeroInt = NonZeroInt.random()
        val actual: NonZeroInt = -number
        val expected: NonZeroInt = number.toInt()
            .unaryMinus()
            .toNonZeroIntOrFailure()
        assertEquals(expected, actual)
    }
}

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonZeroIntCompanionTest {
    @Test
    fun negativeRange_should_be_StrictlyNegativeInt_range() {
        val actual: NotEmptyRange<NonZeroInt> = NonZeroInt.negativeRange
        val expected: NotEmptyRange<NonZeroInt> = StrictlyNegativeInt.range
        assertSame(expected, actual)
    }

    @Test
    fun positiveRange_should_be_StrictlyPositiveInt_range() {
        val actual: NotEmptyRange<NonZeroInt> = NonZeroInt.positiveRange
        val expected: NotEmptyRange<NonZeroInt> = StrictlyPositiveInt.range
        assertSame(expected, actual)
    }
}

private fun Number.toNonZeroIntOrFailure(): NonZeroInt = toNonZeroInt()
    .getOrNull()
    ?: unexpectedCreationFailure<NonZeroInt>(value = this)
