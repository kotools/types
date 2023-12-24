/*
 * Copyright 2023 Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotools.types.number.AnyInt
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class AnyIntTest {
    @ExperimentalKotoolsTypesApi
    @Test
    fun unaryMinus_should_pass() {
        val value: Int = Random.nextInt()
        val number = AnyInt(value)
        val actual: AnyInt = -number
        val expected = AnyInt(-value)
        assertEquals(expected, actual)
    }
}
