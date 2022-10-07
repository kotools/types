package kotools.types.string

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotools.types.core.RandomValueHolder
import kotools.types.number.*
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

    @Test
    fun companion_orNull_should_pass_with_a_not_blank_String() {
        val value: String = randomString
        val result: NotBlankString? = NotBlankString orNull value
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companion_orNull_should_return_null_with_a_blank_String() {
        val result: NotBlankString? = NotBlankString orNull BLANK_STRING
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
            .toPositiveInt()
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
    fun toNonZeroInt_should_pass_with_a_String_representing_a_non_zero_number() {
        val x: NonZeroInt = NonZeroInt.random
        val string: NotBlankString = x.toNotBlankString()
        val result: NonZeroInt = string.toNonZeroInt()
        result.value assertEquals x.value
    }

    @Test
    fun toNonZeroInt_should_throw_an_error_with_an_invalid_String() {
        val value = NotBlankString(randomString)
        assertFailsWith<NumberFormatException>(value::toNonZeroInt)
    }

    @Test
    fun toNonZeroInt_should_throw_an_error_with_a_String_representing_zero() {
        val value = NotBlankString("0")
        assertFailsWith<IllegalArgumentException>(value::toNonZeroInt)
    }

    @Test
    fun toNonZeroIntOrNull_should_pass_with_a_String_representing_a_non_zero_number() {
        val x: NonZeroInt = NonZeroInt.random
        val string: NotBlankString = x.toNotBlankString()
        val result: NonZeroInt? = string.toNonZeroIntOrNull()
        result.assertNotNull().value assertEquals x.value
    }

    @Test
    fun toNonZeroIntOrNull_should_return_null_with_an_invalid_String() {
        val value = NotBlankString(randomString)
        val result: NonZeroInt? = value.toNonZeroIntOrNull()
        result.assertNull()
    }

    @Test
    fun toNonZeroIntOrNull_should_return_null_with_a_String_representing_zero() {
        val value = NotBlankString("0")
        val result: NonZeroInt? = value.toNonZeroIntOrNull()
        result.assertNull()
    }

    @Test
    fun toPositiveInt_should_pass_with_a_String_representing_a_positive_number() {
        val x: PositiveInt = PositiveInt.random
        val string: NotBlankString = x.toNotBlankString()
        val result: PositiveInt = string.toPositiveInt()
        result.value assertEquals x.value
    }

    @Test
    fun toPositiveInt_should_throw_an_error_with_an_invalid_String() {
        val value = NotBlankString(randomString)
        assertFailsWith<NumberFormatException>(value::toPositiveInt)
    }

    @Test
    fun toPositiveInt_should_throw_an_error_with_a_String_representing_a_strictly_negative_number() {
        val x: NotBlankString = StrictlyNegativeInt.random.toNotBlankString()
        assertFailsWith<IllegalArgumentException>(x::toPositiveInt)
    }

    @Test
    fun toPositiveIntOrNull_should_pass_with_a_String_representing_a_positive_number() {
        val x: PositiveInt = PositiveInt.random
        val value: NotBlankString = x.toNotBlankString()
        val result: PositiveInt? = value.toPositiveIntOrNull()
        result.assertNotNull().value assertEquals x.value
    }

    @Test
    fun toPositiveIntOrNull_should_return_null_with_an_invalid_String() {
        val value = NotBlankString(randomString)
        val result: PositiveInt? = value.toPositiveIntOrNull()
        result.assertNull()
    }

    @Test
    fun toPositiveIntOrNull_should_return_null_with_a_String_representing_a_strictly_negative_number() {
        val value: NotBlankString =
            StrictlyNegativeInt.random.toNotBlankString()
        val result: PositiveInt? = value.toPositiveIntOrNull()
        result.assertNull()
    }

    @Test
    fun toStrictlyPositiveInt_should_pass_with_a_String_representing_a_strictly_positive_number() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        val value: NotBlankString = x.toNotBlankString()
        val result: StrictlyPositiveInt = value.toStrictlyPositiveInt()
        result.value assertEquals x.value
    }

    @Test
    fun toStrictlyPositiveInt_should_throw_an_error_with_an_invalid_String() {
        val value = NotBlankString(randomString)
        assertFailsWith<NumberFormatException>(value::toStrictlyPositiveInt)
    }

    @Test
    fun toStrictlyPositiveInt_should_throw_an_error_with_a_String_representing_a_negative_number() {
        val value: NotBlankString = NegativeInt.random.toNotBlankString()
        assertFailsWith<IllegalArgumentException>(value::toStrictlyPositiveInt)
    }

    @Test
    fun toStrictlyPositiveIntOrNull_should_pass_with_a_String_representing_a_strictly_positive_number() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        val value: NotBlankString = x.toNotBlankString()
        val result: StrictlyPositiveInt? = value.toStrictlyPositiveIntOrNull()
        result.assertNotNull().value assertEquals x.value
    }

    @Test
    fun toStrictlyPositiveIntOrNull_should_return_null_with_an_invalid_String() {
        val value = NotBlankString(randomString)
        val result: StrictlyPositiveInt? = value.toStrictlyPositiveIntOrNull()
        result.assertNull()
    }

    @Test
    fun toStrictlyPositiveIntOrNull_should_return_null_with_a_String_representing_a_negative_number() {
        val value: NotBlankString = NegativeInt.random.toNotBlankString()
        val result: StrictlyPositiveInt? = value.toStrictlyPositiveIntOrNull()
        result.assertNull()
    }

    @Test
    fun toNegativeInt_should_pass_with_a_String_representing_a_negative_number() {
        val x: NegativeInt = NegativeInt.random
        val value: NotBlankString = x.toNotBlankString()
        val result: NegativeInt = value.toNegativeInt()
        result.value assertEquals x.value
    }

    @Test
    fun toNegativeInt_should_throw_an_error_with_an_invalid_String() {
        val value = NotBlankString(randomString)
        assertFailsWith<NumberFormatException>(value::toNegativeInt)
    }

    @Test
    fun toNegativeInt_should_throw_an_error_with_a_String_representing_a_strictly_positive_number() {
        val value: NotBlankString =
            StrictlyPositiveInt.random.toNotBlankString()
        assertFailsWith<IllegalArgumentException>(value::toNegativeInt)
    }

    @Test
    fun toNegativeIntOrNull_should_pass_with_a_String_representing_a_negative_number() {
        val x: NegativeInt = NegativeInt.random
        val value: NotBlankString = x.toNotBlankString()
        val result: NegativeInt? = value.toNegativeIntOrNull()
        result.assertNotNull().value assertEquals x.value
    }

    @Test
    fun toNegativeIntOrNull_should_return_null_with_an_invalid_String() {
        val value = NotBlankString(randomString)
        val result: NegativeInt? = value.toNegativeIntOrNull()
        result.assertNull()
    }

    @Test
    fun toNegativeIntOrNull_should_return_null_with_a_String_representing_a_strictly_positive_number() {
        val value: NotBlankString =
            StrictlyPositiveInt.random.toNotBlankString()
        val result: NegativeInt? = value.toNegativeIntOrNull()
        result.assertNull()
    }

    @Test
    fun toStrictlyNegativeInt_should_pass_with_a_String_representing_a_strictly_negative_number() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        val value: NotBlankString = x.toNotBlankString()
        val result: StrictlyNegativeInt = value.toStrictlyNegativeInt()
        result.value assertEquals x.value
    }

    @Test
    fun toStrictlyNegativeInt_should_throw_an_error_with_an_invalid_String() {
        val value = NotBlankString(randomString)
        assertFailsWith<NumberFormatException>(value::toStrictlyNegativeInt)
    }

    @Test
    fun toStrictlyNegativeInt_should_throw_an_error_with_a_String_representing_a_positive_number() {
        val value: NotBlankString = PositiveInt.random.toNotBlankString()
        assertFailsWith<IllegalArgumentException>(value::toStrictlyNegativeInt)
    }

    @Test
    fun toStrictlyNegativeIntOrNull_should_pass_with_a_String_representing_a_strictly_negative_number() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        val value: NotBlankString = x.toNotBlankString()
        val result: StrictlyNegativeInt? = value.toStrictlyNegativeIntOrNull()
        result.assertNotNull().value assertEquals x.value
    }

    @Test
    fun toStrictlyNegativeIntOrNull_should_return_null_with_an_invalid_String() {
        val value = NotBlankString(randomString)
        val result: StrictlyNegativeInt? = value.toStrictlyNegativeIntOrNull()
        result.assertNull()
    }

    @Test
    fun toStrictlyNegativeIntOrNull_should_return_null_with_a_String_representing_a_positive_number() {
        val value: NotBlankString = PositiveInt.random.toNotBlankString()
        val result: StrictlyNegativeInt? = value.toStrictlyNegativeIntOrNull()
        result.assertNull()
    }

    // ---------- Serialization ----------

    @Test
    fun serialization_should_behave_like_a_String() {
        val string = NotBlankString(randomString)
        val result: String = Json.encodeToString(string)
        result assertEquals Json.encodeToString(string.value)
    }

    @Test
    fun deserialization_should_pass() {
        val value: String = randomString
        val encoded: String = Json.encodeToString(value)
        val result: NotBlankString = Json.decodeFromString(encoded)
        result.value assertEquals value
    }
}
