package kotools.types.int

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.test.Test

@Suppress("TestFunctionName")
class NegativeIntTest {
    // ---------- Builders ----------

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

    // ---------- Unary operations ----------

    @Test
    fun inc_should_return_the_minimum_value_with_the_maximum_value() {
        var x: NegativeInt = NegativeInt.max
        x++
        x assertEquals NegativeInt.min
    }

    @Test
    fun inc_should_increment_the_value_by_one_with_an_initial_value_other_than_the_maximum() {
        var x: NegativeInt = NegativeInt.random
        while (x.value == NegativeInt.max.value) x = NegativeInt.random
        val initialValue: Int = x.value
        x++
        x.value assertEquals initialValue + 1
    }

    @Test
    fun dec_should_return_the_maximum_value_with_the_minimum_value() {
        var x: NegativeInt = NegativeInt.min
        x--
        x assertEquals NegativeInt.max
    }

    @Test
    fun dec_should_decrement_the_value_by_one_with_an_initial_value_other_than_the_minimum() {
        var x: NegativeInt = NegativeInt.random
        while (x.value == NegativeInt.min.value) x = NegativeInt.random
        val initialValue: Int = x.value
        x--
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass() {
        val x: NegativeInt = NegativeInt.random
        val result: PositiveInt = -x
        result.value assertEquals -x.value
    }
}
