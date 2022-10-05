package kotools.types.number

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.random.Random
import kotlin.test.Test

class NonZeroIntTest {
    // ---------- Builders ----------

    @Test
    fun constructor_should_pass_with_an_Int_other_than_zero() {
        var value: Int = Random.nextInt()
        while (0 == value) value = Random.nextInt()
        val result = NonZeroInt(value)
        result.value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_an_Int_that_equals_zero() {
        assertFailsWith<IllegalArgumentException> { NonZeroInt(0) }
    }

    @Test
    fun companion_orNull_should_pass_with_an_Int_other_than_zero() {
        var value: Int = Random.nextInt()
        while (0 == value) value = Random.nextInt()
        val result: NonZeroInt? = NonZeroInt orNull value
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companion_orNull_should_return_null_with_an_Int_that_equals_zero() {
        val result: NonZeroInt? = NonZeroInt orNull 0
        result.assertNull()
    }

    @Test
    fun string_toNonZeroInt_should_pass_with_a_String_representing_a_number_other_than_0() {
        val value: Int = NonZeroInt.random.value
        val result: NonZeroInt = value.toString().toNonZeroInt()
        result.value assertEquals value
    }

    @Test
    fun string_toNonZeroInt_should_throw_an_error_with_an_invalid_String() {
        assertFailsWith<NumberFormatException>("a"::toNonZeroInt)
    }

    @Test
    fun string_toNonZeroInt_should_throw_an_error_with_a_String_representing_0() {
        assertFailsWith<IllegalArgumentException>("0"::toNonZeroInt)
    }

    @Test
    fun string_toNonZeroIntOrNull_should_pass_with_a_String_representing_a_number_other_than_0() {
        val value: Int = NonZeroInt.random.value
        val result: NonZeroInt? = value.toString().toNonZeroIntOrNull()
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun string_toNonZeroIntOrNull_should_return_null_with_an_invalid_String() {
        val result: NonZeroInt? = "a".toNonZeroIntOrNull()
        result.assertNull()
    }

    @Test
    fun string_toNonZeroIntOrNull_should_return_null_with_a_String_representing_0() {
        val result: NonZeroInt? = "0".toNonZeroIntOrNull()
        result.assertNull()
    }

    // ---------- Serialization ----------

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: NonZeroInt = NonZeroInt.random
        val result: String = Json.encodeToString(x)
        result assertEquals Json.encodeToString(x.value)
    }

    @Test
    fun deserialization_should_pass() {
        val value: Int = NonZeroInt.random.value
        val encoded: String = Json.encodeToString(value)
        val result: NonZeroInt = Json.decodeFromString(encoded)
        result.value assertEquals value
    }

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
