package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.test.Test

class IntHolderDslTest {
    @Test
    fun intOrNull_should_pass_with_a_valid_value(): Unit = mapOf(
        nonZero to NonZeroInt.random().value,
        positive to PositiveInt.random().value,
        strictlyPositive to StrictlyPositiveInt.random().value,
        negative to NegativeInt.random().value,
        strictlyNegative to StrictlyNegativeInt.random().value
    ).forEach {
        it.key.intOrNull(it.value)
            .assertNotNull()
            .value assertEquals it.value
    }

    @Test
    fun intOrNull_should_return_null_with_an_invalid_value(): Unit = mapOf(
        nonZero to 0,
        positive to StrictlyNegativeInt.random().value,
        strictlyPositive to NegativeInt.random().value,
        negative to StrictlyPositiveInt.random().value,
        strictlyNegative to PositiveInt.random().value
    ).forEach {
        it.key.intOrNull(it.value)
            .assertNull()
    }
}
