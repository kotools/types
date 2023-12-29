/*
 * Copyright 2023 Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotools.types.internal.unexpectedCreationFailure
import kotools.types.number.NonZeroInt
import kotools.types.number.toNonZeroInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalKotoolsTypesApi
class NonZeroIntTest {
    @Test
    fun unaryMinus_should_pass() {
        val number: NonZeroInt = NonZeroInt.random()
        val actual: NonZeroInt = -number
        val expected: NonZeroInt = number.toInt()
            .unaryMinus()
            .toNonZeroIntOrFailure()
        assertEquals(expected, actual)
    }

    @Test
    fun positiveRange_should_go_from_1_included_to_Int_MAX_VALUE_included() {
        val actual: NotEmptyRange<NonZeroInt> = NonZeroInt.positiveRange
        assertTrue { actual.start is InclusiveBound }
        val expectedStartValue: NonZeroInt = 1.toNonZeroIntOrFailure()
        assertEquals(expectedStartValue, actual.start.value)
        assertTrue { actual.end is InclusiveBound }
        val expectedEndValue: NonZeroInt = Int.MAX_VALUE.toNonZeroIntOrFailure()
        assertEquals(expectedEndValue, actual.end.value)
    }

    @Test
    fun positiveRange_documentation_should_be_valid() {
        val actual: String = NonZeroInt.positiveRange.toString()
        val expected = "[1;2147483647]"
        assertEquals(expected, actual)
    }
}

private fun Number.toNonZeroIntOrFailure(): NonZeroInt = toNonZeroInt()
    .getOrNull()
    ?: unexpectedCreationFailure<NonZeroInt>(value = this)
