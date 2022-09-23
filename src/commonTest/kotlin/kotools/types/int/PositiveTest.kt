package kotools.types.int

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.test.Test

@Suppress("TestFunctionName")
class PositiveIntTest {
    @Test
    fun PositiveInt_should_pass_with_a_positive_Int() {
        val value: Int = PositiveInt.random.value
        val result = PositiveInt(value)
        result.value assertEquals value
    }

    @Test
    fun PositiveInt_should_throw_an_error_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.random.value
        assertFailsWith<IllegalArgumentException> { PositiveInt(value) }
    }

    @Test
    fun PositiveIntOrNull_should_pass_with_a_positive_Int() {
        val value: Int = PositiveInt.random.value
        val result: PositiveInt? = PositiveIntOrNull(value)
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun PositiveIntOrNull_should_return_null_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.random.value
        val result: PositiveInt? = PositiveIntOrNull(value)
        result.assertNull()
    }

    @Test
    fun String_toPositiveInt_should_pass_with_a_String_representing_a_positive_number() {
        val value: Int = PositiveInt.random.value
        val result: PositiveInt = value.toString().toPositiveInt()
        result.value assertEquals value
    }

    @Test
    fun String_toPositiveInt_should_throw_an_error_with_an_invalid_String() {
        assertFailsWith<NumberFormatException>("a"::toPositiveInt)
    }

    @Test
    fun String_toPositiveInt_should_throw_an_error_with_a_String_representing_a_strictly_negative_number() {
        val value: String = StrictlyNegativeInt.random.value.toString()
        assertFailsWith<IllegalArgumentException>(value::toPositiveInt)
    }

    @Test
    fun String_toPositiveIntOrNull_should_pass_with_a_String_representing_a_positive_number() {
        val value: Int = PositiveInt.random.value
        val result: PositiveInt? = value.toString().toPositiveIntOrNull()
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun String_toPositiveIntOrNull_should_return_null_with_an_invalid_String(): Unit =
        "a".toPositiveIntOrNull().assertNull()

    @Test
    fun String_toPositiveIntOrNull_should_return_null_with_a_String_representing_a_strictly_negative_number(): Unit =
        StrictlyNegativeInt.random.value.toString()
            .toPositiveIntOrNull()
            .assertNull()
}
