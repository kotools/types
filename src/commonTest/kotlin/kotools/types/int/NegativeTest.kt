package kotools.types.int

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.test.Test

@Suppress("TestFunctionName")
class NegativeIntTest {
    @Test
    fun NegativeInt_should_pass_with_a_negative_Int() {
        val value: Int = NegativeInt.random.value
        NegativeInt(value).value assertEquals value
    }

    @Test
    fun NegativeInt_should_throw_an_error_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.random.value
        assertFailsWith<IllegalArgumentException> { NegativeInt(value) }
    }

    @Test
    fun NegativeIntOrNull_should_pass_with_a_negative_Int() {
        val value: Int = NegativeInt.random.value
        NegativeIntOrNull(value)
            .assertNotNull()
            .value assertEquals value
    }

    @Test
    fun NegativeIntOrNull_should_return_null_with_a_strictly_positive_Int(): Unit =
        NegativeIntOrNull(StrictlyPositiveInt.random.value).assertNull()

    @Test
    fun String_toNegativeInt_should_pass_with_a_String_representing_a_negative_number() {
        val value: Int = NegativeInt.random.value
        value.toString()
            .toNegativeInt()
            .value assertEquals value
    }

    @Test
    fun String_toNegativeInt_should_throw_an_error_with_an_invalid_String() {
        assertFailsWith<NumberFormatException>("a"::toNegativeInt)
    }

    @Test
    fun String_toNegativeInt_should_throw_an_error_with_a_String_representing_a_strictly_positive_number() {
        val value: String = StrictlyPositiveInt.random.value.toString()
        assertFailsWith<IllegalArgumentException>(value::toNegativeInt)
    }

    @Test
    fun String_toNegativeOrNull_should_pass_with_a_String_representing_a_negative_number() {
        val value: Int = NegativeInt.random.value
        value.toString()
            .toNegativeIntOrNull()
            .assertNotNull()
            .value assertEquals value
    }

    @Test
    fun String_toNegativeIntOrNull_should_return_null_with_an_invalid_String(): Unit =
        "a".toNegativeIntOrNull().assertNull()

    @Test
    fun String_toNegativeIntOrNull_should_return_null_with_a_String_representing_a_strictly_positive_number(): Unit =
        StrictlyPositiveInt.random.value.toString()
            .toNegativeIntOrNull()
            .assertNull()
}
