package kotools.types.string

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.test.Test

class NotBlankStringTest {
    // ---------- Builders ----------

    @Test
    fun constructor_should_pass_with_a_not_blank_String(): Unit =
        NotBlankString(VALUE).value assertEquals VALUE

    @Test
    fun constructor_should_throw_an_error_with_a_blank_String() {
        assertFailsWith<IllegalArgumentException> { NotBlankString("") }
    }

    @Test
    fun companionOrNull_should_pass_with_a_not_blank_String() {
        val result: NotBlankString? = NotBlankString orNull VALUE
        result.assertNotNull().value assertEquals VALUE
    }

    @Test
    fun companionOrNull_should_return_null_with_a_blank_String() {
        val result: NotBlankString? = NotBlankString orNull ""
        result.assertNull()
    }

    private companion object {
        const val VALUE = "hello world"
    }
}
