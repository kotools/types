/*
 * Copyright 2023 Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotools.types.number.NegativeInt
import kotools.types.number.PositiveInt
import kotools.types.number.toNegativeInt
import kotools.types.number.toPositiveIntOrFailure
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NegativeIntTest {
    @ExperimentalKotoolsTypesApi
    @Test
    fun unaryMinus_should_pass() {
        val number: NegativeInt = NegativeInt.random()
        val actual: PositiveInt = -number
        val expected: PositiveInt = number.toInt()
            .unaryMinus()
            .toPositiveIntOrFailure()
        assertEquals(expected, actual)
    }

    @ExperimentalKotoolsTypesApi
    @Test
    fun range_should_go_from_Int_MIN_VALUE_included_to_zero_included() {
        val actual: NotEmptyRange<NegativeInt> = NegativeInt.range
        assertTrue { actual.start is InclusiveBound }
        val expectedStartValue: NegativeInt = Int.MIN_VALUE.toNegativeInt()
            .getOrThrow()
        assertEquals(expectedStartValue, actual.start.value)
        val expectedEndValue: NegativeInt = 0.toNegativeInt()
            .getOrThrow()
        assertEquals(expectedEndValue, actual.end.value)
    }
}
