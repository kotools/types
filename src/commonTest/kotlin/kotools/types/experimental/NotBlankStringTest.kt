package kotools.types.experimental

import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalKotoolsTypesApi
class NotBlankStringTest {
    @Test
    fun plus_should_pass_with_a_String() {
        val first: NotBlankString = "hello".toNotBlankString()
            .getOrThrow()
        val second = " world"
        val actual: NotBlankString = first + second
        val expected: NotBlankString = "$first$second".toNotBlankString()
            .getOrThrow()
        assertEquals(expected, actual)
    }

    @Test
    fun char_plus_should_pass_with_a_NotBlankString() {
        val first = 'a'
        val second: NotBlankString = " book".toNotBlankString()
            .getOrThrow()
        val actual: NotBlankString = first + second
        val expected: NotBlankString = "$first$second".toNotBlankString()
            .getOrThrow()
        assertEquals(expected, actual)
    }
}
