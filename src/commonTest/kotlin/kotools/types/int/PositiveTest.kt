package kotools.types.int

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.test.Test

@Suppress("TestFunctionName")
class PositiveIntTest {
    // ---------- Builders ----------

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

    // ---------- Unary operations ----------

    @Test
    fun inc_should_return_the_minimum_value_with_the_maximum_value() {
        var x: PositiveInt = PositiveInt.max
        x++
        x assertEquals PositiveInt.min
    }

    @Test
    fun inc_should_increment_the_value_by_1_with_an_initial_value_other_than_the_maximum_value() {
        var x: PositiveInt = PositiveInt.random
        while (x.value == PositiveInt.max.value) x = PositiveInt.random
        val initialValue: Int = x.value
        x++
        x.value assertEquals initialValue + 1
    }

    @Test
    fun dec_should_return_the_maximum_value_with_the_minimum_value() {
        var x: PositiveInt = PositiveInt.min
        x--
        x assertEquals PositiveInt.max
    }

    @Test
    fun dec_should_decrement_the_value_by_1_with_an_initial_value_other_than_the_minimum_value() {
        var x: PositiveInt = PositiveInt.random
        while (x.value == PositiveInt.min.value) x = PositiveInt.random
        val initialValue: Int = x.value
        x--
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass() {
        val x: PositiveInt = PositiveInt.random
        val result: NegativeInt = -x
        result.value assertEquals -x.value
    }
}
