/*
 * Copyright 2023 Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotools.types.number.NegativeInt
import kotools.types.number.PositiveInt
import kotools.types.number.toNegativeIntOrFailure
import kotools.types.number.toPositiveInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PositiveIntTest {
    @ExperimentalKotoolsTypesApi
    @Test
    fun unaryMinus_should_pass() {
        val number: PositiveInt = PositiveInt.random()
        val actual: NegativeInt = -number
        val expected: NegativeInt = number.toInt()
            .unaryMinus()
            .toNegativeIntOrFailure()
        assertEquals(expected, actual)
    }

    @ExperimentalKotoolsTypesApi
    @Test
    fun range_should_go_from_0_included_to_Int_MAX_VALUE_included() {
        val actual: NotEmptyRange<PositiveInt> = PositiveInt.range
        assertTrue { actual.start is InclusiveBound }
        val expectedStartValue: PositiveInt = 0.toPositiveInt()
            .getOrThrow()
        assertEquals(expectedStartValue, actual.start.value)
        assertTrue { actual.end is InclusiveBound }
        val expectedEndValue: PositiveInt = Int.MAX_VALUE.toPositiveInt()
            .getOrThrow()
        assertEquals(expectedEndValue, actual.end.value)
    }
}
