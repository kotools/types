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
}
