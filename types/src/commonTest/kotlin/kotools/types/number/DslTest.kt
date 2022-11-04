package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.test.Test

class IntHolderDslTest {
    @Test
    fun intOrNull_should_pass_with_a_valid_value(): Unit = mapOf(
        nonZero to randomNonZeroInt().value,
        positive to randomPositiveInt().value,
        strictlyPositive to randomStrictlyPositiveInt().value,
        negative to NegativeInt.random().value,
        strictlyNegative to StrictlyNegativeInt.random().value
    ).forEach {
        val result: IntHolder? = it.key intOrNull it.value
        result.assertNotNull().value assertEquals it.value
    }

    @Test
    fun intOrNull_should_return_null_with_an_invalid_value(): Unit = mapOf(
        nonZero to 0,
        positive to StrictlyNegativeInt.random().value,
        strictlyPositive to NegativeInt.random().value,
        negative to randomStrictlyPositiveInt().value,
        strictlyNegative to randomPositiveInt().value
    ).forEach {
        val result: IntHolder? = it.key intOrNull it.value
        result.assertNull()
    }
}
