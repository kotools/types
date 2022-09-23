package kotools.types.int

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.test.Test

@Suppress("TestFunctionName")
class StrictlyNegativeIntTest {
    // ---------- Builders ----------

    @Test
    fun StrictlyNegativeInt_should_pass_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.random.value
        StrictlyNegativeInt(value).value assertEquals value
    }

    @Test
    fun StrictlyNegativeInt_should_throw_an_error_with_a_positive_Int() {
        val value: Int = PositiveInt.random.value
        assertFailsWith<IllegalArgumentException> { StrictlyNegativeInt(value) }
    }

    @Test
    fun StrictlyNegativeIntOrNull_should_pass_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.random.value
        StrictlyNegativeIntOrNull(value)
            .assertNotNull()
            .value assertEquals value
    }

    @Test
    fun StrictlyNegativeIntOrNull_should_return_null_with_a_positive_Int() {
        StrictlyNegativeIntOrNull(PositiveInt.random.value).assertNull()
    }

    @Test
    fun String_toStrictlyNegativeInt_should_pass_with_a_String_representing_a_strictly_negative_number() {
        val value: Int = StrictlyNegativeInt.random.value
        value.toString()
            .toStrictlyNegativeInt()
            .value assertEquals value
    }

    @Test
    fun String_toStrictlyNegativeInt_should_throw_an_error_with_an_invalid_String() {
        assertFailsWith<NumberFormatException>("a"::toStrictlyNegativeInt)
    }

    @Test
    fun String_toStrictlyNegativeInt_should_throw_an_error_with_a_String_representing_a_positive_number() {
        val value: String = PositiveInt.random.value.toString()
        assertFailsWith<IllegalArgumentException>(value::toStrictlyNegativeInt)
    }

    @Test
    fun String_toStrictlyNegativeIntOrNull_should_pass_with_a_String_representing_a_strictly_negative_number() {
        val value: Int = StrictlyNegativeInt.random.value
        value.toString()
            .toStrictlyNegativeIntOrNull()
            .assertNotNull()
            .value assertEquals value
    }

    @Test
    fun String_toStrictlyNegativeIntOrNull_should_return_null_with_an_invalid_String() {
        "a".toStrictlyNegativeIntOrNull().assertNull()
    }

    @Test
    fun String_toStrictlyNegativeIntOrNull_should_return_null_with_a_String_representing_a_positive_number() {
        PositiveInt.random.value.toString()
            .toStrictlyNegativeIntOrNull()
            .assertNull()
    }

    // ---------- Unary operations ----------

    @Test
    fun inc_should_return_the_minimum_value_with_maximum_value() {
        var x: StrictlyNegativeInt = StrictlyNegativeInt.max
        x++
        x assertEquals StrictlyNegativeInt.min
    }

    @Test
    fun inc_should_increment_the_value_by_one_with_an_initial_value_other_than_the_maximum() {
        var x: StrictlyNegativeInt = StrictlyNegativeInt.random
        while (x.value == StrictlyNegativeInt.max.value)
            x = StrictlyNegativeInt.random
        val initialValue: Int = x.value
        x++
        x.value assertEquals initialValue + 1
    }

    @Test
    fun dec_should_return_the_maximum_value_with_the_minimum_value() {
        var x: StrictlyNegativeInt = StrictlyNegativeInt.min
        x--
        x assertEquals StrictlyNegativeInt.max
    }

    @Test
    fun dec_should_decrement_the_value_by_one_with_an_initial_value_other_than_the_minimum() {
        var x: StrictlyNegativeInt = StrictlyNegativeInt.random
        while (x.value == StrictlyNegativeInt.min.value)
            x = StrictlyNegativeInt.random
        val initialValue: Int = x.value
        x--
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        val result: StrictlyPositiveInt = -x
        result.value assertEquals -x.value
    }
}
