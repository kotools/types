/*
 * Copyright 2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.internal.hashCodeOf
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertSame

private const val RANGE_FROM: Double = 1.0
private const val RANGE_UNTIL: Double = Double.MAX_VALUE

@ExperimentalNumberApi
class StrictlyPositiveDoubleTest {
    @Test
    fun constructor_should_pass_with_a_Double_that_is_greater_than_zero() {
        val value: Double = Random.nextDouble(RANGE_FROM, RANGE_UNTIL)
        StrictlyPositiveDouble(value)
    }

    @Test
    fun constructor_should_fail_with_a_Double_that_equals_zero() {
        val value = 0.0
        val exception: IllegalArgumentException =
            assertFailsWith { StrictlyPositiveDouble(value) }
        assertEquals(
            expected = "Number should be greater than zero (tried with $value)",
            actual = exception.message
        )
    }

    @Test
    fun constructor_should_fail_with_a_Double_that_is_less_than_zero() {
        val value: Double = Random.nextDouble(RANGE_FROM, RANGE_UNTIL)
            .unaryMinus()
        val exception: IllegalArgumentException =
            assertFailsWith { StrictlyPositiveDouble(value) }
        assertEquals(
            expected = "Number should be greater than zero (tried with $value)",
            actual = exception.message
        )
    }

    @Test
    fun equals_should_pass_with_the_same_instance() {
        val value: Double = Random.nextDouble(RANGE_FROM, RANGE_UNTIL)
        val x = StrictlyPositiveDouble(value)
        val y: StrictlyPositiveDouble = x
        assertSame(x, y)
    }

    @Test
    fun equals_should_pass_with_another_StrictlyPositiveDouble_having_the_same_value() {
        val xValue: Double = Random.nextDouble(RANGE_FROM, RANGE_UNTIL)
        val x = StrictlyPositiveDouble(xValue)
        val yValue: Double = x.toDouble()
        val y = StrictlyPositiveDouble(yValue)
        assertEquals(x, y)
    }

    @Test
    fun equals_should_fail_with_null() {
        val value: Double = Random.nextDouble(RANGE_FROM, RANGE_UNTIL)
        val x = StrictlyPositiveDouble(value)
        val y: Any? = null
        assertNotEquals(x, y)
    }

    @Test
    fun equals_should_fail_with_another_object_of_type_other_than_StrictlyPositiveDouble() {
        val value: Double = Random.nextDouble(RANGE_FROM, RANGE_UNTIL)
        val x = StrictlyPositiveDouble(value)
        val y = "null"
        val actual: Boolean = x.equals(y)
        assertFalse(actual)
    }

    @Test
    fun equals_should_fail_with_another_StrictlyPositiveDouble_having_a_different_value() {
        val xValue: Double = Random.nextDouble(RANGE_FROM, RANGE_UNTIL)
        val x = StrictlyPositiveDouble(xValue)
        val yValue: Double = Random.nextDouble(RANGE_FROM, RANGE_UNTIL)
        val y = StrictlyPositiveDouble(yValue)
        assertNotEquals(x, y)
    }

    @Test
    fun hashCode_should_return_a_unique_hash_code_based_on_its_value() {
        val value: Double = Random.nextDouble(RANGE_FROM, RANGE_UNTIL)
        val number = StrictlyPositiveDouble(value)
        val actual: Int = number.hashCode()
        val expected: Int = hashCodeOf(value)
        assertEquals(expected, actual)
    }

    @Test
    fun toDouble_should_return_its_value() {
        val value: Double = Random.nextDouble(RANGE_FROM, RANGE_UNTIL)
        val number = StrictlyPositiveDouble(value)
        val actual: Double = number.toDouble()
        assertEquals(expected = value, actual)
    }

    @Test
    fun toString_should_return_its_value_as_String() {
        val value: Double = Random.nextDouble(RANGE_FROM, RANGE_UNTIL)
        val number = StrictlyPositiveDouble(value)
        assertEquals(expected = "$value", actual = "$number")
    }
}

@ExperimentalNumberApi
class StrictlyPositiveDoubleCompanionTest {
    @Test
    fun orNull_should_pass_with_a_Double_that_is_greater_than_zero() {
        val value: Double = Random.nextDouble(RANGE_FROM, RANGE_UNTIL)
        val actual: StrictlyPositiveDouble? =
            StrictlyPositiveDouble.orNull(value)
        assertNotNull(actual)
    }

    @Test
    fun orNull_should_fail_with_a_Double_that_equals_zero() {
        val actual: StrictlyPositiveDouble? = StrictlyPositiveDouble.orNull(0.0)
        assertNull(actual)
    }

    @Test
    fun orNull_should_fail_with_a_Double_that_is_less_than_zero() {
        val value: Double = Random.nextDouble(RANGE_FROM, RANGE_UNTIL)
            .unaryMinus()
        val actual: StrictlyPositiveDouble? =
            StrictlyPositiveDouble.orNull(value)
        assertNull(actual)
    }
}
