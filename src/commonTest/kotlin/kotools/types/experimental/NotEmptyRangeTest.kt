/*
 * Copyright 2023 Loïc Lamarque and Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalKotoolsTypesApi
class NotEmptyRangeTest {
    @Test
    fun notEmptyRangeOf_should_return_a_range_starting_with_the_first_bound() {
        // GIVEN
        val first: Int = (Int.MIN_VALUE..0).random()
        val second: Int = (1..Int.MAX_VALUE).random()
        // WHEN
        val range: NotEmptyRange<Int> = notEmptyRangeOf {
            first.inclusive to second.exclusive
        }
        // THEN
        range.start.run {
            assertTrue("The range's start should be inclusive.") {
                this is InclusiveBound
            }
            assertEquals(expected = first, actual = value)
        }
        range.end.run {
            assertTrue("The range's end should be exclusive.") {
                this is ExclusiveBound
            }
            assertEquals(expected = second, actual = value)
        }
    }

    @Test
    fun notEmptyRangeOf_should_return_a_range_starting_with_the_second_bound() {
        // GIVEN
        val first: Int = (1..Int.MAX_VALUE).random()
        val second: Int = (Int.MIN_VALUE..0).random()
        // WHEN
        val range: NotEmptyRange<Int> = notEmptyRangeOf {
            first.exclusive to second.inclusive
        }
        // THEN
        range.start.run {
            assertTrue("The range's start should be inclusive.") {
                this is InclusiveBound
            }
            assertEquals(expected = second, actual = value)
        }
        range.end.run {
            assertTrue("The range's end should be exclusive.") {
                this is ExclusiveBound
            }
            assertEquals(expected = first, actual = value)
        }
    }

    @Test
    fun toString_should_pass_with_2_inclusive_bounds() {
        // GIVEN
        val first: Int = (Int.MIN_VALUE..0).random()
        val second: Int = (1..Int.MAX_VALUE).random()
        val range: NotEmptyRange<Int> = notEmptyRangeOf {
            first.inclusive to second.inclusive
        }
        // WHEN
        val result = "$range"
        // THEN
        assertEquals(expected = "[$first;$second]", actual = result)
    }

    @Test
    fun toString_should_pass_with_2_exclusive_bounds() {
        // GIVEN
        val first: Int = (Int.MIN_VALUE..0).random()
        val second: Int = (1..Int.MAX_VALUE).random()
        val range: NotEmptyRange<Int> = notEmptyRangeOf {
            first.exclusive to second.exclusive
        }
        // WHEN
        val result = "$range"
        // THEN
        assertEquals(expected = "]$first;$second[", actual = result)
    }

    @Test
    fun toString_should_pass_with_an_inclusive_and_an_exclusive_bounds() {
        // GIVEN
        val first: Int = (Int.MIN_VALUE..0).random()
        val second: Int = (1..Int.MAX_VALUE).random()
        val range: NotEmptyRange<Int> = notEmptyRangeOf {
            first.inclusive to second.exclusive
        }
        // WHEN
        val result = "$range"
        // THEN
        assertEquals(expected = "[$first;$second[", actual = result)
    }

    @Test
    fun contains_should_pass_with_a_value_in_inclusive_bounds() {
        // GIVEN
        val first: Int = Random.nextInt()
        val second: Int = Random.nextInt()
        val range: NotEmptyRange<Int> = notEmptyRangeOf {
            first.inclusive to second.inclusive
        }
        val value: Int = (range.start.value..range.end.value).random()
        // WHEN & THEN
        assertTrue("The value should be included in the range.") {
            value in range
        }
    }

    @Test
    fun contains_should_pass_with_a_value_in_exclusive_bounds() {
        // GIVEN
        val first: Int = Random.nextInt()
        val second: Int = Random.nextInt()
        val range: NotEmptyRange<Int> = notEmptyRangeOf {
            first.exclusive to second.exclusive
        }
        val value: Int = ((range.start.value + 1) until range.end.value)
            .random()
        // WHEN & THEN
        assertTrue("The value should be included in the range.") {
            value in range
        }
    }

    @Test
    fun contains_should_pass_with_a_value_in_inclusive_and_exclusive_bounds() {
        // GIVEN
        val first: Int = Random.nextInt()
        val second: Int = Random.nextInt()
        val range: NotEmptyRange<Int> = notEmptyRangeOf {
            first.inclusive to second.exclusive
        }
        val value: Int = (range.start.value until range.end.value).random()
        // WHEN & THEN
        assertTrue("The value should be included in the range.") {
            value in range
        }
    }

    @Test
    fun contains_should_fail_with_a_value_that_is_not_in_inclusive_bounds() {
        // GIVEN
        val first: Int = Random.nextInt()
        val second: Int = Random.nextInt()
        val range: NotEmptyRange<Int> = notEmptyRangeOf {
            first.inclusive to second.inclusive
        }
        val value: Int = range.end.value + 1
        // WHEN & THEN
        assertFalse("The value shouldn't be included in the range.") {
            value in range
        }
    }

    @Test
    fun contains_should_fail_with_a_value_that_is_not_in_exclusive_bounds() {
        // GIVEN
        val first: Int = Random.nextInt()
        val second: Int = Random.nextInt()
        val range: NotEmptyRange<Int> = notEmptyRangeOf {
            first.exclusive to second.exclusive
        }
        val value: Int = range.end.value
        // WHEN & THEN
        assertFalse("The value shouldn't be included in the range.") {
            value in range
        }
    }

    @Test
    fun contains_should_fail_with_a_value_that_is_not_in_inclusive_and_exclusive_bounds() {
        // GIVEN
        val first: Int = Random.nextInt()
        val second: Int = Random.nextInt()
        val range: NotEmptyRange<Int> = notEmptyRangeOf {
            first.inclusive to second.exclusive
        }
        val value: Int = range.end.let {
            if (it is InclusiveBound) it.value + 1 else it.value
        }
        // WHEN & THEN
        assertFalse("The value shouldn't be included in the range.") {
            value in range
        }
    }
}
