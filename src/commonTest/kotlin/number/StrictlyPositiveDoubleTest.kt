/*
 * Copyright 2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotools.types.experimental.ExperimentalNumberApi
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@ExperimentalNumberApi
class StrictlyPositiveDoubleTest {
    @Test
    fun constructor_should_pass_with_a_Double_that_is_greater_than_zero() {
        val value: Double =
            Random.nextDouble(from = 1.0, until = Double.MAX_VALUE)
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
        val value: Double = Random
            .nextDouble(from = 1.0, until = Double.MAX_VALUE)
            .unaryMinus()
        val exception: IllegalArgumentException =
            assertFailsWith { StrictlyPositiveDouble(value) }
        assertEquals(
            expected = "Number should be greater than zero (tried with $value)",
            actual = exception.message
        )
    }
}
