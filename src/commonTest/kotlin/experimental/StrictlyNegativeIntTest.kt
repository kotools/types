/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyPositiveInt
import kotlin.test.Test
import kotlin.test.assertEquals

class StrictlyNegativeIntTest {
    @ExperimentalKotoolsTypesApi
    @Test
    fun unaryMinus_should_pass() {
        val number: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val actual: StrictlyPositiveInt = -number
        val expected: StrictlyPositiveInt = number.toInt()
            .unaryMinus()
            .toStrictlyPositiveInt()
            .getOrThrow()
        assertEquals(expected, actual)
    }
}
