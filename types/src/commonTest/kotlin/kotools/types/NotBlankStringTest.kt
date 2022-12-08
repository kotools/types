package kotools.types

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*
import kotlin.test.Test

class NotBlankStringTest {
    @Test
    fun compareTo_should_pass() {
        val x: NotBlankString = "hello".toNotBlankStringOrThrow()
        val y: NotBlankString = "world".toNotBlankStringOrThrow()
        val result: Int = x.compareTo(y)
        val expectedResult: Int = "$x".compareTo("$y")
        result assertEquals expectedResult
    }

    @Test
    fun serialization_should_behave_like_a_String() {
        val value: NotBlankString = "hello world".toNotBlankStringOrThrow()
        val result: String = Json.encodeToString(value)
        val expectedResult: String = Json.encodeToString("$value")
        result assertEquals expectedResult
    }

    @Test
    fun deserialization_should_pass_with_a_not_blank_String() {
        val value = "hello world"
        val encoded: String = Json.encodeToString(value)
        val result: NotBlankString = Json.decodeFromString(encoded)
        "$result" assertEquals value
    }

    @Test
    fun deserialization_should_fail_with_a_blank_String() {
        val value = " "
        val encoded: String = Json.encodeToString(value)
        val error: IllegalArgumentException =
            assertFailsWith { Json.decodeFromString<NotBlankString>(encoded) }
        error.message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    @Test
    fun string_toNotBlankString_should_pass_with_a_not_blank_String() {
        val value = "hello world"
        value.toNotBlankString()
            .getOrThrow()
            .toString() assertEquals value
    }

    @Test
    fun string_toNotBlankString_should_fail_with_a_blank_String() {
        val result: Result<NotBlankString> = "   ".toNotBlankString()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}

@Throws(IllegalArgumentException::class)
private fun String.toNotBlankStringOrThrow(): NotBlankString =
    toNotBlankString()
        .getOrThrow()
