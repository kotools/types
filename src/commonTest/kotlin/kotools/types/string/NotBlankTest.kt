package kotools.types.string

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotools.types.core.RandomValueHolder
import kotools.types.number.PositiveInt
import kotlin.random.Random
import kotlin.test.Test

class NotBlankStringTest : RandomValueHolder {
    private companion object {
        private const val BLANK_STRING: String = " "
    }

    // ---------- Builders ----------

    @Test
    fun constructor_should_pass_with_a_not_blank_String() {
        val value: String = randomString
        val result = NotBlankString(value)
        result.value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_a_blank_String() {
        assertFailsWith<IllegalArgumentException> {
            NotBlankString(BLANK_STRING)
        }
    }

    @Suppress("TestFunctionName")
    @Test
    fun NotBlankStringOrNull_should_pass_with_a_not_blank_String() {
        val value: String = randomString
        val result: NotBlankString? = NotBlankStringOrNull(value)
        result.assertNotNull().value assertEquals value
    }

    @Suppress("TestFunctionName")
    @Test
    fun NotBlankStringOrNull_should_return_null_with_a_blank_String() {
        val result: NotBlankString? = NotBlankStringOrNull(BLANK_STRING)
        result.assertNull()
    }

    @Test
    fun string_toNotBlankString_should_pass_with_a_not_blank_String() {
        val value: String = randomString
        val result: NotBlankString = value.toNotBlankString()
        result.value assertEquals value
    }

    @Test
    fun string_toNotBlankString_should_throw_an_error_with_a_blank_String() {
        assertFailsWith<IllegalArgumentException>(
            BLANK_STRING::toNotBlankString
        )
    }

    @Test
    fun string_toNotBlankStringOrNull_should_pass_with_a_not_blank_String() {
        val value: String = randomString
        val result: NotBlankString? = value.toNotBlankStringOrNull()
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun string_toNotBlankStringOrNull_should_return_null_with_a_blank_String() {
        val result: NotBlankString? = BLANK_STRING.toNotBlankStringOrNull()
        result.assertNull()
    }

    // ---------- Positional access operations ----------

    @Test
    fun get_should_pass_with_an_index_in_bounds() {
        val value: String = randomString
        val index: PositiveInt = Random.nextInt(from = 0, until = value.length)
            .let(::PositiveInt)
        val string = NotBlankString(value)
        val result: Char = string[index]
        result assertEquals value[index.value]
    }

    // ---------- Binary operations ----------

    @Test
    fun compareTo_should_pass_when_comparing_a_String_with_a_NotBlankString() {
        val x: String = randomString
        val y = NotBlankString(randomString)
        val result: Int = x.compareTo(y)
        result assertEquals x.compareTo(y.value)
    }

    @Test
    fun compareTo_should_pass_with_a_String() {
        val x = NotBlankString(randomString)
        val y: String = randomString
        val result: Int = x.compareTo(y)
        result assertEquals x.value.compareTo(y)
    }

    @Test
    fun compareTo_should_pass_with_another_NotBlankString() {
        val x = NotBlankString(randomString)
        val y = NotBlankString(randomString)
        val result: Int = x.compareTo(y)
        result assertEquals x.value.compareTo(y.value)
    }

    @Test
    fun plus_should_pass() {
        val x = NotBlankString(randomString)
        val y: String = randomString
        val result: NotBlankString = x + y
        result.value assertEquals x.value + y
    }

    // ---------- Conversions ----------

    @Test
    fun toString_should_behave_like_a_String() {
        val value: String = randomString
        val notBlankString = NotBlankString(value)
        notBlankString.toString() assertEquals value
    }
}

private typealias Serializer = NotBlankStringSerializer

class NotBlankStringSerializerTest : RandomValueHolder {
    @Test
    fun serialization_should_behave_like_a_String() {
        val string = NotBlankString(randomString)
        val result: String = Json.encodeToString(Serializer, string)
        result assertEquals Json.encodeToString(string.value)
    }

    @Test
    fun deserialization_should_pass() {
        val value: String = randomString
        val encoded: String = Json.encodeToString(value)
        val result: NotBlankString = Json.decodeFromString(Serializer, encoded)
        result.value assertEquals value
    }
}
