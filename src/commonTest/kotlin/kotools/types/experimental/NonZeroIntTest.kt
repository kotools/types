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
private fun Number.toNonZeroIntOrFailure(): NonZeroInt = toNonZeroInt()
    .getOrNull()
    ?: unexpectedCreationFailure<NonZeroInt>(value = this)
