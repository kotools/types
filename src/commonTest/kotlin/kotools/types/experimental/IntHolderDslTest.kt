package kotools.types.experimental

import kotools.assert.assertEquals
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotools.types.ExperimentalKotoolsTypesApi
import kotools.types.number.*
import kotlin.test.Test

@ExperimentalKotoolsTypesApi
class IntHolderDslTest {
    private val validEntries: Map<IntHolderDsl<IntHolder>, Int> = mapOf(
        nonZero to NonZeroInt.random().value,
        positive to PositiveInt.random().value,
        strictlyPositive to PositiveInt.random().value,
        negative to NegativeInt.random().value,
        strictlyNegative to StrictlyNegativeInt.random().value
    )

    private val invalidEntries: Map<IntHolderDsl<IntHolder>, Int> = mapOf(
        nonZero to 0,
        positive to StrictlyNegativeInt.random().value,
        strictlyPositive to NegativeInt.random().value,
        negative to StrictlyPositiveInt.random().value,
        strictlyNegative to PositiveInt.random().value
    )

    @Test
    fun int_should_pass(): Unit = validEntries.mapKeys { it.key int it.value }
        .forEach { it.key.value assertEquals it.value }

    @Test
    fun int_should_fail(): Unit = invalidEntries.forEach {
        runCatching { it.key int it.value }
            .exceptionOrNull()
            .assertNotNull()
            .message
            .assertNotNull()
    }

    @Test
    fun intOrNull_should_pass(): Unit =
        validEntries.mapKeys { it.key intOrNull it.value }
            .forEach { it.key.assertNotNull().value assertEquals it.value }

    @Test
    fun intOrNull_should_fail(): Unit =
        invalidEntries.mapKeys { it.key intOrNull it.value }
            .keys
            .forEach(IntHolder?::assertNull)
}
