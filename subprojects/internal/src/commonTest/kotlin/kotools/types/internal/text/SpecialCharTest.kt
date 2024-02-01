package kotools.types.internal.text

import kotools.types.internal.InternalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@InternalKotoolsTypesApi
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
