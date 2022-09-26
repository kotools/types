package kotools.types.string

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.test.Test

@Suppress("TestFunctionName")
class NotBlankStringTest {
    @Test
    fun NotBlankString_should_pass_with_a_not_blank_String() {
        val value = "hello"
        val result = NotBlankString(value)
        result.value assertEquals value
    }

    @Test
    fun NotBlankString_should_throw_an_error_with_a_blank_String() {
        assertFailsWith<IllegalArgumentException> { NotBlankString(" ") }
    }

    @Test
    fun NotBlankStringOrNull_should_pass_with_a_not_blank_String() {
        val value = "world"
        val result: NotBlankString? = NotBlankStringOrNull(value)
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun NotBlankStringOrNull_should_return_null_with_a_blank_String(): Unit =
        NotBlankStringOrNull("").assertNull()
}
