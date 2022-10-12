package kotools.types.experimental

import kotools.assert.assertEquals
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotools.types.ExperimentalKotoolsTypesApi
import kotools.types.IntHolder
import kotools.types.randomNegativeInt
import kotools.types.randomNonZeroInt
import kotools.types.randomPositiveInt
import kotools.types.randomStrictlyNegativeInt
import kotools.types.randomStrictlyPositiveInt
import kotlin.test.Test

@ExperimentalKotoolsTypesApi
class IntHolderDslTest {
    private val validEntries: Map<IntHolderDsl<IntHolder>, Int> = mapOf(
        nonZero to randomNonZeroInt().value,
        positive to randomPositiveInt().value,
        strictlyPositive to randomPositiveInt().value,
        negative to randomNegativeInt().value,
        strictlyNegative to randomStrictlyNegativeInt().value
    )

    private val invalidEntries: Map<IntHolderDsl<IntHolder>, Int> = mapOf(
        nonZero to 0,
        positive to randomStrictlyNegativeInt().value,
        strictlyPositive to randomNegativeInt().value,
        negative to randomStrictlyPositiveInt().value,
        strictlyNegative to randomPositiveInt().value
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
