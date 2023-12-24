/*
 * Copyright 2023 Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal.text

import kotlin.test.Test
import kotlin.test.assertEquals

class SpecialCharTest {
    @Test
    fun toChar_should_pass_on_AtSign() {
        val character: SpecialChar = SpecialChar.AtSign
        val actual: Char = character.toChar()
        assertEquals(expected = '@', actual)
    }

    @Test
    fun toString_should_pass_on_AtSign() {
        val character: SpecialChar = SpecialChar.AtSign
        assertEquals(expected = "@", actual = "$character")
    }
}
