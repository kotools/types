/*
 * Copyright 2022-2023 Loïc Lamarque and Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.text

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotools.types.experimental.ExperimentalTextApi
import kotools.types.internal.ErrorMessage
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.simpleNameOf
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.ZeroInt
import kotools.types.shouldEqual
import kotlin.test.Test
import kotlin.test.assertEquals
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
        val exception: IllegalArgumentException =
            assertFailsWith { result.getOrThrow() }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage = ErrorMessage.blankString
        assertEquals(expectedMessage, actualMessage)
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
    fun descriptor_serial_name_should_be_the_qualified_name_of_NotBlankString() {
        val actual: String = serializer<NotBlankString>().descriptor.serialName
        val simpleName: String = simpleNameOf<NotBlankString>()
        val expected = "${KotoolsTypesPackage.Text}.$simpleName"
        assertEquals(expected, actual)
    }

    @ExperimentalSerializationApi
    @Test
    fun descriptor_kind_should_be_PrimitiveKind_STRING() {
        val actual: SerialKind = serializer<NotBlankString>().descriptor.kind
        val expected: SerialKind = PrimitiveKind.STRING
        assertEquals(expected, actual)
    }

    @Test
    fun serialization_should_behave_like_a_String() {
        val value: NotBlankString = StringExample.NOT_BLANK.toNotBlankString()
            .getOrThrow()
        val actual: String = Json.encodeToString(value)
        val expected: String = Json.encodeToString("$value")
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_pass_with_a_not_blank_String() {
        val value: String = StringExample.NOT_BLANK
        val encoded: String = Json.encodeToString(value)
        val actual: NotBlankString = Json.decodeFromString(encoded)
        val expected: NotBlankString = value.toNotBlankString()
            .getOrThrow()
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_fail_with_a_blank_String() {
        val encoded: String = Json.encodeToString(StringExample.BLANK)
        val exception: SerializationException =
            assertFailsWith { Json.decodeFromString<NotBlankString>(encoded) }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage = ErrorMessage.blankString
        assertEquals(expectedMessage, actualMessage)
    }
}
