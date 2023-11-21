/*
 * Copyright 2022-2023 Loïc Lamarque.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.text

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.experimental.ExperimentalTextApi
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.ZeroInt
import kotools.types.shouldBeNotNull
import kotools.types.shouldEqual
import kotools.types.shouldFailWithIllegalArgumentException
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
    fun toNotBlankString_should_pass_with_a_not_blank_String() {
        val result: Result<NotBlankString> =
            StringExample.NOT_BLANK.toNotBlankString()
        "${result.getOrThrow()}" shouldEqual StringExample.NOT_BLANK
    }

    @Test
    fun toNotBlankString_should_fail_with_a_blank_String() {
        val result: Result<NotBlankString> =
            StringExample.BLANK.toNotBlankString()
        result.shouldFailWithIllegalArgumentException { getOrThrow() }
            .message
            .shouldBeNotNull()
            .shouldEqual(NotBlankStringException.message)
    }

    @ExperimentalTextApi
    @Test
    fun toNotBlankStringOrThrow_should_pass_with_a_not_blank_String() {
        val string: String = StringExample.NOT_BLANK
        val result: NotBlankString = string.toNotBlankStringOrThrow()
        "$result" shouldEqual string
    }

    @ExperimentalTextApi
    @Test
    fun toNotBlankStringOrThrow_should_fail_with_a_blank_String() {
        val string: String = StringExample.BLANK
        val error: IllegalArgumentException =
            string.shouldFailWithIllegalArgumentException {
                toNotBlankStringOrThrow()
            }
        error.message.shouldBeNotNull()
            .shouldEqual(NotBlankStringException.message)
    }

    @Test
    fun length_should_return_a_StrictlyPositiveInt() {
        val result: StrictlyPositiveInt = StringExample.NOT_BLANK
            .toNotBlankString()
            .map(NotBlankString::length)
            .getOrThrow()
        result.toInt() shouldEqual StringExample.NOT_BLANK.length
    }

    @Test
    fun compareTo_should_return_zero_with_the_same_NotBlankString() {
        val x: NotBlankString = StringExample.NOT_BLANK.toNotBlankString()
            .getOrThrow()
        val y: NotBlankString = "$x".toNotBlankString()
            .getOrThrow()
        val result: Int = x compareTo y
        result shouldEqual ZeroInt.toInt()
    }

    @Test
    fun compareTo_should_return_a_negative_number_with_a_greater_NotBlankString() {
        val x: NotBlankString = "a".toNotBlankString()
            .getOrThrow()
        val y: NotBlankString = "b".toNotBlankString()
            .getOrThrow()
        val result: Int = x compareTo y
        assertTrue { result < ZeroInt.toInt() }
    }

    @Test
    fun compareTo_should_return_a_positive_number_with_a_less_NotBlankString() {
        val x: NotBlankString = "c".toNotBlankString()
            .getOrThrow()
        val y: NotBlankString = "b".toNotBlankString()
            .getOrThrow()
        val result: Int = x compareTo y
        assertTrue { result > ZeroInt.toInt() }
    }

    @ExperimentalTextApi
    @Test
    fun plus_should_pass_with_a_String() {
        val first: NotBlankString = "hell".toNotBlankString()
            .getOrThrow()
        val second = "o"
        val result: NotBlankString = first + second
        "$result" shouldEqual "$first$second"
    }

    @ExperimentalTextApi
    @Test
    fun plus_should_pass_with_a_NotBlankString() {
        val first: NotBlankString = "hell".toNotBlankString()
            .getOrThrow()
        val second: NotBlankString = "o".toNotBlankString()
            .getOrThrow()
        val result: NotBlankString = first + second
        "$result" shouldEqual "$first$second"
    }

    @ExperimentalTextApi
    @Test
    fun plus_should_pass_with_a_Char() {
        val first: NotBlankString = "hell".toNotBlankString()
            .getOrThrow()
        val second = 'o'
        val result: NotBlankString = first + second
        "$result" shouldEqual "$first$second"
    }

    @ExperimentalTextApi
    @Test
    fun char_plus_should_pass_with_a_NotBlankString() {
        val first = 'a'
        val second: NotBlankString = "b".toNotBlankString()
            .getOrThrow()
        val result: NotBlankString = first + second
        "$result" shouldEqual "$first$second"
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
