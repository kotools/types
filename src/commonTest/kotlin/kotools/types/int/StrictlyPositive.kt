package kotools.types.int

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.test.Test

@Suppress("TestFunctionName")
class StrictlyPositiveIntTest {
    @Test
    fun StrictlyPositiveInt_should_pass_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.random.value
        StrictlyPositiveInt(value).value assertEquals value
    }

    @Test
    fun StrictlyPositiveInt_should_throw_an_error_with_a_negative_Int() {
        assertFailsWith<IllegalArgumentException> {
            StrictlyPositiveInt(NegativeInt.random.value)
        }
    }

    @Test
    fun StrictlyPositiveIntOrNull_should_pass_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.random.value
        StrictlyPositiveIntOrNull(value)
            .assertNotNull()
            .value assertEquals value
    }

    @Test
    fun StrictlyPositiveIntOrNull_should_return_null_with_a_negative_Int(): Unit =
        StrictlyPositiveIntOrNull(NegativeInt.random.value).assertNull()

    @Test
    fun String_toStrictlyPositiveInt_should_pass_with_a_String_representing_a_strictly_positive_number() {
        val value: Int = StrictlyPositiveInt.random.value
        value.toString()
            .toStrictlyPositiveInt()
            .value assertEquals value
    }

    @Test
    fun String_toStrictlyPositiveInt_should_throw_an_error_with_an_invalid_String() {
        assertFailsWith<NumberFormatException>("a"::toStrictlyPositiveInt)
    }

    @Test
    fun String_toStrictlyPositiveInt_should_throw_an_error_with_a_String_representing_a_negative_number() {
        val value: String = NegativeInt.random.value.toString()
        assertFailsWith<IllegalArgumentException>(value::toStrictlyPositiveInt)
    }

    @Test
    fun String_toStrictlyPositiveIntOrNull_should_pass_with_a_String_representing_a_strictly_positive_number() {
        val value: Int = StrictlyPositiveInt.random.value
        value.toString()
            .toStrictlyPositiveIntOrNull()
            .assertNotNull()
            .value assertEquals value
    }

    @Test
    fun String_toStrictlyPositiveIntOrNull_should_return_null_with_an_invalid_String(): Unit =
        "a".toStrictlyPositiveIntOrNull().assertNull()

    @Test
    fun String_toStrictlyPositiveIntOrNull_should_return_null_with_a_String_representing_a_negative_number(): Unit =
        NegativeInt.random.value.toString()
            .toStrictlyPositiveIntOrNull()
            .assertNull()
}
