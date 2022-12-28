package kotools.types.text

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.assertHasAMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NotBlankStringTest {
    @Test
    fun length_should_pass() {
        val string = "hello world"
        assertEquals(
            actual = string.asNotBlankString.getOrThrow().length.value,
            expected = string.length
        )
    }

    @Test
    fun compareTo_should_pass() {
        val x: NotBlankString = "hello".asNotBlankString.getOrThrow()
        val y: NotBlankString = "world".asNotBlankString.getOrThrow()
        assertEquals(
            actual = x.compareTo(y),
            expected = "$x".compareTo("$y")
        )
    }

    @Test
    fun string_toNotBlankString_should_pass_with_a_not_blank_String() {
        val value = "hello world"
        assertEquals(
            actual = value.asNotBlankString.getOrThrow()
                .toString(),
            expected = value
        )
    }

    @Test
    fun string_toNotBlankString_should_fail_with_a_blank_String() {
        val result: Result<NotBlankString> = "   ".asNotBlankString
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .assertHasAMessage()
    }
}

class NotBlankStringSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_be_named_with_the_qualified_name_of_NotBlankString(): Unit =
        assertEquals(
            actual = NotBlankStringSerializer.descriptor.serialName,
            expected = "${Package.text}.NotBlankString"
        )

    @Test
    fun serialization_should_behave_like_a_String() {
        val value: NotBlankString = "hello world".asNotBlankString.getOrThrow()
        assertEquals(
            actual = Json.encodeToString(value),
            expected = Json.encodeToString("$value")
        )
    }

    @Test
    fun deserialization_should_pass_with_a_not_blank_String() {
        val value = "hello world"
        val encoded: String = Json.encodeToString(value)
        val result: NotBlankString = Json.decodeFromString(encoded)
        assertEquals(actual = "$result", expected = value)
    }

    @Test
    fun deserialization_should_fail_with_a_blank_String() {
        val encoded: String = Json.encodeToString(" ")
        val exception: SerializationException =
            assertFailsWith { Json.decodeFromString<NotBlankString>(encoded) }
        exception.assertHasAMessage()
    }
}
