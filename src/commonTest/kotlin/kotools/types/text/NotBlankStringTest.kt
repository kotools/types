package kotools.types.text

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.ZeroInt
import kotools.types.shouldEqual
import kotools.types.shouldHaveAMessage
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

private object StringExample {
    const val BLANK: String = "  "
    const val NOT_BLANK: String = "hello world"
}

class NotBlankStringTest {
    @Test
    fun length_should_return_a_StrictlyPositiveInt() {
        val result: StrictlyPositiveInt = StringExample.NOT_BLANK
            .toNotBlankString()
            .map(NotBlankString::length)
            .getOrThrow()
        result.asInt shouldEqual StringExample.NOT_BLANK.length
    }

    @Test
    fun compareTo_should_return_zero_with_the_same_NotBlankString() {
        val x: NotBlankString = StringExample.NOT_BLANK.toNotBlankString()
            .getOrThrow()
        val y: NotBlankString = StringExample.NOT_BLANK.toNotBlankString()
            .getOrThrow()
        val result: Int = x.compareTo(y)
        result shouldEqual ZeroInt.asInt
    }

    @Test
    fun compareTo_should_return_a_positive_Int_with_a_lower_NotBlankString() {
        val x: NotBlankString = "b".toNotBlankString()
            .getOrThrow()
        val y: NotBlankString = "a".toNotBlankString()
            .getOrThrow()
        val result: Int = x.compareTo(y)
        assertTrue { result > ZeroInt.asInt }
    }

    @Test
    fun compareTo_should_return_a_negative_Int_with_a_greater_NotBlankString() {
        val x: NotBlankString = "a".toNotBlankString()
            .getOrThrow()
        val y: NotBlankString = "b".toNotBlankString()
            .getOrThrow()
        val result: Int = x.compareTo(y)
        assertTrue { result < ZeroInt.asInt }
    }

    @Test
    fun string_toNotBlankString_should_pass_with_a_not_blank_String() {
        val result: Result<NotBlankString> =
            StringExample.NOT_BLANK.toNotBlankString()
        "${result.getOrThrow()}" shouldEqual StringExample.NOT_BLANK
    }

    @Test
    fun string_toNotBlankString_should_fail_with_a_blank_String() {
        val result: Result<NotBlankString> =
            StringExample.BLANK.toNotBlankString()
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .shouldHaveAMessage()
    }
}

class NotBlankStringSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_be_named_with_the_qualified_name_of_NotBlankString() {
        val result: String = NotBlankStringSerializer.descriptor.serialName
        result shouldEqual "${Package.text}.NotBlankString"
    }

    @Test
    fun serialization_should_behave_like_a_String() {
        val value: NotBlankString = StringExample.NOT_BLANK.toNotBlankString()
            .getOrThrow()
        val result: String = Json.encodeToString(value)
        result shouldEqual Json.encodeToString("$value")
    }

    @Test
    fun deserialization_should_pass_with_a_not_blank_String() {
        val encoded: String = Json.encodeToString(StringExample.NOT_BLANK)
        val result: NotBlankString = Json.decodeFromString(encoded)
        "$result" shouldEqual StringExample.NOT_BLANK
    }

    @Test
    fun deserialization_should_fail_with_a_blank_String() {
        val encoded: String = Json.encodeToString(StringExample.BLANK)
        val exception: SerializationException =
            assertFailsWith { Json.decodeFromString<NotBlankString>(encoded) }
        exception.shouldHaveAMessage()
    }
}
