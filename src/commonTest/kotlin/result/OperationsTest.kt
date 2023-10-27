/*
 * Copyright 2023 Loïc Lamarque.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.result

import kotools.types.experimental.ExperimentalResultApi
import kotools.types.number.*
import kotools.types.shouldEqual
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@ExperimentalResultApi
class FlatMapTest {
    @Test
    fun should_pass_with_a_successful_receiver_and_result_of_transformation() {
        // GIVEN
        val receiver: Result<NonZeroInt> = 1.toNonZeroInt()
        // WHEN
        val result: Result<StrictlyPositiveInt> =
            receiver.flatMap { it.plus(2).toStrictlyPositiveInt() }
        // THEN
        result.getOrThrow().toInt() shouldEqual 3
    }

    @Test
    fun should_fail_with_a_failure_as_receiver() {
        // GIVEN
        val receiver: Result<NonZeroInt> = 0.toNonZeroInt()
        // WHEN
        val result: Result<StrictlyPositiveInt> =
            receiver.flatMap { it.minus(2).toStrictlyPositiveInt() }
        // THEN
        val originalException: Throwable =
            assertNotNull(receiver.exceptionOrNull())
        val resultException: Throwable = assertNotNull(result.exceptionOrNull())
        originalException shouldEqual resultException
    }

    @Test
    fun should_fail_with_a_failure_as_result_of_transformation() {
        // GIVEN
        val receiver: Result<NonZeroInt> = 1.toNonZeroInt()
        // WHEN
        val result: Result<StrictlyPositiveInt> =
            receiver.flatMap { it.minus(2).toStrictlyPositiveInt() }
        // THEN
        assertNull(receiver.exceptionOrNull())
        assertFails(result::getOrThrow)
    }
}
