/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal

import kotlin.test.Test
import kotlin.test.assertEquals

class KotoolsTypesPackageTest {
    @Test
    fun toString_should_pass_on_Number() {
        val actual: String = KotoolsTypesPackage.Number.toString()
        val expected = "kotools.types.number"
        assertEquals(expected, actual)
    }

    @Test
    fun toString_should_pass_on_Text() {
        val actual: String = KotoolsTypesPackage.Text.toString()
        val expected = "kotools.types.text"
        assertEquals(expected, actual)
    }
}
