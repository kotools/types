package kotools.types.experimental

import kotools.types.internal.unexpectedCreationFailure
import kotools.types.number.AnyInt
import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.ZeroInt
import kotools.types.number.toStrictlyNegativeInt
import kotools.types.number.toStrictlyPositiveIntOrFailure
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class AnyIntTest {
    @ExperimentalKotoolsTypesApi
    @Test
    fun unaryMinus_should_pass() {
        val number: AnyInt = AnyInt.random()
        val actual: AnyInt = -number
        val expected: AnyInt = number.toInt()
            .unaryMinus()
            .toAnyIntOrFailure()
        assertEquals(expected, actual)
    }
}

private fun AnyInt.Companion.random(): AnyInt = Random.nextInt()
    .toAnyIntOrFailure()

private fun Int.toAnyIntOrFailure(): AnyInt = when {
    this == 0 -> ZeroInt
    this > 0 -> toStrictlyPositiveIntOrFailure()
    else -> toStrictlyNegativeIntOrFailure()
}

private fun Int.toStrictlyNegativeIntOrFailure(): StrictlyNegativeInt =
    toStrictlyNegativeInt()
        .getOrNull()
        ?: unexpectedCreationFailure<StrictlyNegativeInt>(value = this)
