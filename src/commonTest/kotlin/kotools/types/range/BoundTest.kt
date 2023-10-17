/*
 * Copyright 2023 Loïc Lamarque.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.range

import kotools.types.experimental.ExperimentalRangeApi
import kotlin.random.Random
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@ExperimentalRangeApi
class InclusiveBoundTest {
    // Implementing the equals function introduces a KotlinFrontEndException.
    @Ignore
    @Test
    fun equals_should_pass_with_an_inclusive_bound_having_the_same_value() {
        // GIVEN
        val x: InclusiveBound<Int> = InclusiveBound(Random.nextInt())
        val y: InclusiveBound<Int> = InclusiveBound(x.value)
        // WHEN & THEN
        "The equals operation should be reflexive.".let {
            assertEquals(expected = x, actual = x, message = it)
            assertEquals(expected = y, actual = y, message = it)
        }
        "The equals operation should be symmetric.".let {
            assertEquals(expected = x, actual = y, message = it)
            assertEquals(expected = y, actual = x, message = it)
        }
        "The equals operation should be transitive.".let {
            val z: InclusiveBound<Int> = InclusiveBound(y.value)
            assertEquals(expected = x, actual = y, message = it)
            assertEquals(expected = y, actual = z, message = it)
            assertEquals(expected = x, actual = z, message = it)
        }
        "The equals operation should never equal to null.".let {
            val z: InclusiveBound<Int>? = null
            assertNotEquals(illegal = z, actual = x, message = it)
        }
    }

    // Implementing the equals function introduces a KotlinFrontEndException.
    @Ignore
    @Test
    fun equals_should_fail_with_an_inclusive_bound_having_another_value() {
        // GIVEN
        val x: InclusiveBound<Int> = InclusiveBound(Random.nextInt())
        val y: InclusiveBound<Int> = InclusiveBound(Random.nextInt())
        // WHEN & THEN
        assertNotEquals(illegal = x, actual = y)
    }

    @Test
    fun toString_should_return_the_string_representation_of_its_value() {
        // GIVEN
        val value: Int = Random.nextInt()
        val bound: InclusiveBound<Int> = InclusiveBound(value)
        // WHEN
        val result = "$bound"
        // THEN
        assertEquals(expected = "$value", actual = result)
    }
}

@ExperimentalRangeApi
class ExclusiveBoundTest {
    // Implementing the equals function introduces a KotlinFrontEndException.
    @Ignore
    @Test
    fun equals_should_pass_with_an_exclusive_bound_having_the_same_value() {
        // GIVEN
        val x: ExclusiveBound<Int> = ExclusiveBound(Random.nextInt())
        val y: ExclusiveBound<Int> = ExclusiveBound(x.value)
        // WHEN & THEN
        "The equals operation should be reflexive.".let {
            assertEquals(expected = x, actual = x, message = it)
            assertEquals(expected = y, actual = y, message = it)
        }
        "The equals operation should be symmetric.".let {
            assertEquals(expected = x, actual = y, message = it)
            assertEquals(expected = y, actual = x, message = it)
        }
        "The equals operation should be transitive.".let {
            val z: ExclusiveBound<Int> = ExclusiveBound(y.value)
            assertEquals(expected = x, actual = y, message = it)
            assertEquals(expected = y, actual = z, message = it)
            assertEquals(expected = x, actual = z, message = it)
        }
        "The equals operation should never equal to null.".let {
            val z: ExclusiveBound<Int>? = null
            assertNotEquals(illegal = z, actual = x, message = it)
        }
    }

    // Implementing the equals function introduces a KotlinFrontEndException.
    @Ignore
    @Test
    fun equals_should_fail_with_an_exclusive_bound_having_another_value() {
        // GIVEN
        val x: ExclusiveBound<Int> = ExclusiveBound(Random.nextInt())
        val y: ExclusiveBound<Int> = ExclusiveBound(Random.nextInt())
        // WHEN & THEN
        assertNotEquals(illegal = x, actual = y)
    }

    @Test
    fun toString_should_return_the_string_representation_of_its_value() {
        // GIVEN
        val value: Int = Random.nextInt()
        val bound: ExclusiveBound<Int> = ExclusiveBound(value)
        // WHEN
        val result = "$bound"
        // THEN
        assertEquals(expected = "$value", actual = result)
    }
}
