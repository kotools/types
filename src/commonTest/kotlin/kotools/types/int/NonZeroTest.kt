package kotools.types.int

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.test.Test

@Suppress("TestFunctionName")
class NonZeroIntTest {
    // ---------- Builders ----------

    @Test
    fun NonZeroInt_should_pass_with_an_Int_other_than_0() {
        val value: Int = NonZeroInt.random.value
        val result = NonZeroInt(value)
        result.value assertEquals value
    }

    @Test
    fun NonZeroInt_should_throw_an_error_with_an_Int_that_equals_0() {
        assertFailsWith<IllegalArgumentException> { NonZeroInt(0) }
    }

    @Test
    fun NonZeroIntOrNull_should_pass_with_an_Int_other_than_0() {
        val value: Int = NonZeroInt.random.value
        val result: NonZeroInt? = NonZeroIntOrNull(value)
        result.assertNotNull().value assertEquals value
    }


    @Test
    fun NonZeroIntOrNull_should_return_null_with_an_Int_that_equals_0(): Unit =
        NonZeroIntOrNull(0).assertNull()

    @Test
    fun String_toNonZeroInt_should_pass_with_a_String_representing_a_number_other_than_0() {
        val value: Int = NonZeroInt.random.value
        val result: NonZeroInt = value.toString().toNonZeroInt()
        result.value assertEquals value
    }

    @Test
    fun String_toNonZeroInt_should_throw_an_error_with_an_invalid_String() {
        assertFailsWith<NumberFormatException>("a"::toNonZeroInt)
    }

    @Test
    fun String_toNonZeroInt_should_throw_an_error_with_a_String_representing_0() {
        assertFailsWith<IllegalArgumentException>("0"::toNonZeroInt)
    }

    @Test
    fun String_toNonZeroIntOrNull_should_pass_with_a_String_representing_a_number_other_than_0() {
        val value: Int = NonZeroInt.random.value
        val result: NonZeroInt? = value.toString().toNonZeroIntOrNull()
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun String_toNonZeroIntOrNull_should_return_null_with_an_invalid_String(): Unit =
        "a".toNonZeroIntOrNull().assertNull()

    @Test
    fun String_toNonZeroIntOrNull_should_return_null_with_a_String_representing_0() =
        "0".toNonZeroIntOrNull().assertNull()

    // ---------- Unary operations ----------

    @Test
    fun inc_should_return_1_with_a_value_that_equals_minus1() {
        var x = NonZeroInt(-1)
        x++
        x.value assertEquals 1
    }

    @Test
    fun inc_should_return_the_minimum_value_with_the_maximum_value() {
        var x: NonZeroInt = NonZeroInt.max
        x++
        x assertEquals NonZeroInt.min
    }

    @Test
    fun inc_should_increment_the_value_by_1_with_an_initial_value_other_than_minus1_and_the_maximum_value() {
        var x: NonZeroInt = NonZeroInt.random
        while (x.value == -1 || x == NonZeroInt.max) x = NonZeroInt.random
        val initialValue: Int = x.value
        x++
        x.value assertEquals initialValue + 1
    }

    @Test
    fun dec_should_return_minus1_with_a_value_that_equals_1() {
        var x = NonZeroInt(1)
        x--
        x.value assertEquals -1
    }

    @Test
    fun dec_should_return_the_maximum_value_with_the_minimum_value() {
        var x: NonZeroInt = NonZeroInt.min
        x--
        x assertEquals NonZeroInt.max
    }

    @Test
    fun dec_should_decrement_the_value_by_1_with_an_initial_value_other_than_1_and_the_minimum_value() {
        var x: NonZeroInt = NonZeroInt.random
        while (x.value == 1 || x == NonZeroInt.min) x = NonZeroInt.random
        val initialValue: Int = x.value
        x--
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass() {
        val x: NonZeroInt = NonZeroInt.random
        val result: NonZeroInt = -x
        result.value assertEquals -x.value
    }
}
