package kotools.types.string

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotools.types.number.PositiveInt
import kotools.types.number.toPositiveInt
import kotlin.random.Random
import kotlin.test.Test

class NotBlankStringTest {
    // ---------- Builders ----------

    @Test
    fun constructor_should_pass_with_a_not_blank_String() {
        val value = "hello"
        val result = NotBlankString(value)
        result.value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_a_blank_String() {
        assertFailsWith<IllegalArgumentException> { NotBlankString(" ") }
    }

    @Test
    fun companion_orNull_should_pass_with_a_not_blank_String() {
        val value = "world"
        val result: NotBlankString? = NotBlankString orNull value
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companion_orNull_should_return_null_with_a_blank_String() {
        val result: NotBlankString? = NotBlankString orNull ""
        result.assertNull()
    }

    // ---------- Serialization ----------

    @Test
    fun serialization_should_behave_like_a_String() {
        val string = NotBlankString("hello world")
        val result: String = Json.encodeToString(string)
        result assertEquals Json.encodeToString(string.value)
    }

    @Test
    fun deserialization_should_pass() {
        val value = "hello world"
        val encoded: String = Json.encodeToString(value)
        val result: NotBlankString = Json.decodeFromString(encoded)
        result.value assertEquals value
    }

    // ---------- Positional access operations ----------

    @Test
    fun get_should_pass_with_an_index_in_bounds() {
        val value = "hello world"
        val index: PositiveInt = Random.nextInt(from = 0, until = value.length)
            .toPositiveInt()
        val string = NotBlankString(value)
        val result: Char = string[index]
        result assertEquals value[index.value]
    }
}
