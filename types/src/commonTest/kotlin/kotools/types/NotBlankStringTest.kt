package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

class NotBlankStringTest {
    // ---------- NotBlankString.of(String) ----------

    @Test
    fun of_should_pass_with_a_not_blank_String() {
        val value = "hello world"
        NotBlankString.of(value)
            .getOrThrow()
            .toString() assertEquals value
    }

    @Test
    fun of_should_fail_with_a_blank_String() {
        val result: Result<NotBlankString> = NotBlankString of "  "
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    // ---------- NotBlankString.length ----------

    @Test
    fun length_should_pass() {
        val value = "hello world"
        value.toNotBlankString()
            .getOrThrow()
            .length
            .toInt() assertEquals value.length
    }

    // ---------- NotBlankString.plus(String) ----------

    @Test
    fun plus_should_pass_with_a_String() {
        val xValue = "hello "
        val x: NotBlankString = xValue.toNotBlankString().getOrThrow()
        val y = "world"
        val result: NotBlankString = x + y
        result.toString() assertEquals xValue + y
    }

    // ---------- Char.plus(NotBlankString) ----------

    @Test
    fun plus_on_a_Char_should_pass_with_a_NotBlankString() {
        val x = 'd'
        val y: NotBlankString = "o".toNotBlankString().getOrThrow()
        val result: NotBlankString = x + y
        result.toString() assertEquals x + y.toString()
    }
}
