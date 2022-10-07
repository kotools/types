package kotools.types.string

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotools.types.core.RandomValueHolder
import kotools.types.number.PositiveInt
import kotools.types.number.toPositiveInt
import kotlin.random.Random
import kotlin.test.Test

class NotBlankStringTest : RandomValueHolder {
    private companion object {
        private const val BLANK_STRING: String = " "
    }

    // ---------- Builders ----------

    @Test
    fun constructor_should_pass_with_a_not_blank_String() {
        val value = randomString
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
        val value = randomString
        val result: NotBlankString? = NotBlankString orNull value
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companion_orNull_should_return_null_with_a_blank_String() {
        val result: NotBlankString? = NotBlankString orNull BLANK_STRING
        result.assertNull()
    }

    // TODO: String.toNotBlankString should pass with a not blank String
    // TODO: String.toNotBlankString should throw an error with a blank String

    // TODO: String.toNotBlankStringOrNull should pass with a not blank String
    // TODO: String.toNotBlankStringOrNull should return null with a blank String

    // ---------- Positional access operations ----------

    @Test
    fun get_should_pass_with_an_index_in_bounds() {
        val value = randomString
        val index: PositiveInt = Random.nextInt(from = 0, until = value.length)
            .toPositiveInt()
        val string = NotBlankString(value)
        val result: Char = string[index]
        result assertEquals value[index.value]
    }

    // ---------- Binary operations ----------

    // TODO: compareTo should pass when comparing a String with a NotBlankString
    // TODO: compareTo should pass with a String
    // TODO: compareTo should pass with another NotBlankString

    // TODO: plus should pass

    // ---------- Conversions ----------

    // TODO: toNonZeroInt should pass with a String representing a non-zero number
    // TODO: toNonZeroInt should throw an error with an invalid String
    // TODO: toNonZeroInt should throw an error with a String representing zero

    // TODO: toNonZeroIntOrNull should pass with a String representing a non-zero number
    // TODO: toNonZeroIntOrNull should return null with an invalid String
    // TODO: toNonZeroIntOrNull should return null with a String representing zero

    // TODO: toPositiveInt should pass with a String representing a positive number
    // TODO: toPositiveInt should throw an error with an invalid String
    // TODO: toPositiveInt should throw an error with a String representing a strictly negative number

    // TODO: toPositiveIntOrNull should pass with a String representing a positive number
    // TODO: toPositiveIntOrNull should return null with an invalid String
    // TODO: toPositiveIntOrNull should return null with a String representing a strictly negative number

    // TODO: toStrictlyPositiveInt should pass with a String representing a strictly positive number
    // TODO: toStrictlyPositiveInt should throw an error with an invalid String
    // TODO: toStrictlyPositiveInt should throw an error with a String representing a negative number

    // TODO: toStrictlyPositiveIntOrNull should pass with a String representing a strictly positive number
    // TODO: toStrictlyPositiveIntOrNull should return null with an invalid String
    // TODO: toStrictlyPositiveIntOrNull should return null with a String representing a negative number

    // TODO: toNegativeInt should pass with a String representing a negative number
    // TODO: toNegativeInt should throw an error with an invalid String
    // TODO: toNegativeInt should throw an error with a String representing a strictly positive number

    // TODO: toNegativeIntOrNull should pass with a String representing a negative number
    // TODO: toNegativeIntOrNull should return null with an invalid String
    // TODO: toNegativeIntOrNull should return null with a String representing a strictly positive number

    // TODO: toStrictlyNegativeInt should pass with a String representing a strictly negative number
    // TODO: toStrictlyNegativeInt should throw an error with an invalid String
    // TODO: toStrictlyNegativeInt should throw an error with a String representing a positive number

    // TODO: toStrictlyNegativeIntOrNull should pass with a String representing a strictly negative number
    // TODO: toStrictlyNegativeIntOrNull should return null with an invalid String
    // TODO: toStrictlyNegativeIntOrNull should return null with a String representing a positive number

    // ---------- Serialization ----------

    @Test
    fun serialization_should_behave_like_a_String() {
        val string = NotBlankString(randomString)
        val result: String = Json.encodeToString(string)
        result assertEquals Json.encodeToString(string.value)
    }

    @Test
    fun deserialization_should_pass() {
        val value = randomString
        val encoded: String = Json.encodeToString(value)
        val result: NotBlankString = Json.decodeFromString(encoded)
        result.value assertEquals value
    }
}
