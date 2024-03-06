package kotools.types.number

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.ErrorMessage
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.simpleNameOf
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class StrictlyNegativeDoubleCompanionTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun create_should_pass_with_a_Number_that_is_less_than_zero() {
        val number: Number = Random.nextInt(Int.MIN_VALUE until 0)
        StrictlyNegativeDouble.create(number)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
    @Test
    fun create_should_fail_with_a_Number_that_equals_zero() {
        val number: Number = 0
        val exception: IllegalArgumentException = assertFailsWith {
            StrictlyNegativeDouble.create(number)
        }
        val actual = ErrorMessage(exception)
        val expected: ErrorMessage = StrictlyNegativeDouble.invalid(number)
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
    @Test
    fun create_should_fail_with_a_Number_that_is_greater_than_zero() {
        val number: Number = Random.nextInt(1..Int.MAX_VALUE)
        val exception: IllegalArgumentException = assertFailsWith {
            StrictlyNegativeDouble.create(number)
        }
        val actual = ErrorMessage(exception)
        val expected: ErrorMessage = StrictlyNegativeDouble.invalid(number)
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
    @Test
    fun createOrNull_should_pass_with_a_Number_that_is_less_than_zero() {
        val number: Number = Random.nextInt(Int.MIN_VALUE until 0)
        val actual: StrictlyNegativeDouble? =
            StrictlyNegativeDouble.createOrNull(number)
        val typeName: String = simpleNameOf<StrictlyNegativeDouble>()
        assertNotNull(actual, "Converting $number to $typeName should pass")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
    @Test
    fun createOrNull_should_fail_with_a_Number_that_equals_zero() {
        val number: Number = 0
        val actual: StrictlyNegativeDouble? =
            StrictlyNegativeDouble.createOrNull(number)
        val typeName: String = simpleNameOf<StrictlyNegativeDouble>()
        assertNull(actual, "Converting $number to $typeName should fail")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
    @Test
    fun createOrNull_should_fail_with_a_Number_that_is_greater_than_zero() {
        val number: Number = Random.nextInt(1..Int.MAX_VALUE)
        val actual: StrictlyNegativeDouble? =
            StrictlyNegativeDouble.createOrNull(number)
        val typeName: String = simpleNameOf<StrictlyNegativeDouble>()
        assertNull(actual, "Converting $number to $typeName should fail")
    }
}
