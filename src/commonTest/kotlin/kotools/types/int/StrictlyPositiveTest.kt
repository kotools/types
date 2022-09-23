package kotools.types.int

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.test.Test

@Suppress("TestFunctionName")
class StrictlyPositiveIntTest {
    // ---------- Builders ----------

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

    // ---------- Unary operations ----------

    @Test
    fun inc_should_return_the_minimum_value_with_the_maximum_value() {
        var x: StrictlyPositiveInt = StrictlyPositiveInt.max
        x++
        x assertEquals StrictlyPositiveInt.min
    }

    @Test
    fun inc_should_increment_the_value_by_one_with_an_initial_value_other_than_the_maximum() {
        var x: StrictlyPositiveInt = StrictlyPositiveInt.random
        while (x.value == StrictlyPositiveInt.max.value)
            x = StrictlyPositiveInt.random
        val initialValue: Int = x.value
        x++
        x.value assertEquals initialValue + 1
    }

    @Test
    fun dec_should_return_the_maximum_value_with_the_minimum_value() {
        var x: StrictlyPositiveInt = StrictlyPositiveInt.min
        x--
        x assertEquals StrictlyPositiveInt.max
    }

    @Test
    fun dec_should_decrement_the_value_by_one_with_an_initial_value_other_than_the_minimum() {
        var x: StrictlyPositiveInt = StrictlyPositiveInt.random
        while (x.value == StrictlyPositiveInt.min.value)
            x = StrictlyPositiveInt.random
        val initialValue: Int = x.value
        x--
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        val result: StrictlyNegativeInt = -x
        result.value assertEquals -x.value
    }
}
