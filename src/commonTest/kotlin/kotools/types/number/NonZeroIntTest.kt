package kotools.types.number

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotools.types.core.RandomValueHolder
import kotlin.test.Test

class NonZeroIntTest : RandomValueHolder {
    // ---------- Builders ----------

    @Test
    fun constructor_should_pass_with_an_Int_other_than_zero() {
        var value: Int = randomInt
        while (0 == value) value = randomInt
        val result = NonZeroInt(value)
        result.value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_an_Int_that_equals_zero() {
        assertFailsWith<IllegalArgumentException> { NonZeroInt(0) }
    }

    @Test
    fun companion_orNull_should_pass_with_an_Int_other_than_zero() {
        var value: Int = randomInt
        while (0 == value) value = randomInt
        val result: NonZeroInt? = NonZeroInt orNull value
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companion_orNull_should_return_null_with_an_Int_that_equals_zero() {
        val result: NonZeroInt? = NonZeroInt orNull 0
        result.assertNull()
    }

    @Test
    fun int_toNonZeroInt_should_pass_with_an_Int_other_than_zero() {
        val value: Int = NonZeroInt.random.value
        val result: NonZeroInt = value.toNonZeroInt()
        result.value assertEquals value
    }

    @Test
    fun int_toNonZeroInt_should_throw_an_error_with_an_Int_that_equals_zero() {
        assertFailsWith<IllegalArgumentException>(0::toNonZeroInt)
    }

    @Test
    fun int_toNonZeroIntOrNull_should_pass_with_an_Int_other_than_zero() {
        val value: Int = NonZeroInt.random.value
        val result: NonZeroInt? = value.toNonZeroIntOrNull()
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun int_toNonZeroIntOrNull_should_return_null_with_an_Int_that_equals_zero() {
        val result: NonZeroInt? = 0.toNonZeroIntOrNull()
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

    // ---------- Binary operations ----------

    @Test
    fun compareTo_should_pass_with_another_NonZeroInt() {
        val x: NonZeroInt = NonZeroInt.random
        val y: NonZeroInt = NonZeroInt.random
        val result: Int = x.compareTo(y)
        result assertEquals x.value.compareTo(y.value)
    }

    // ---------- Conversions ----------

    @Suppress("DEPRECATION")
    @Test
    fun toPositiveInt_should_pass_with_a_strictly_positive_value() {
        val x = NonZeroInt(StrictlyPositiveInt.random.value)
        val result: PositiveInt = x.toPositiveInt()
        result.value assertEquals x.value
    }

    @Suppress("DEPRECATION")
    @Test
    fun toPositiveInt_should_throw_an_error_with_a_strictly_negative_value() {
        val x = NonZeroInt(StrictlyNegativeInt.random.value)
        assertFailsWith<IllegalArgumentException>(x::toPositiveInt)
    }

    @Suppress("DEPRECATION")
    @Test
    fun toPositiveIntOrNull_should_pass_with_a_strictly_positive_value() {
        val x = NonZeroInt(StrictlyPositiveInt.random.value)
        val result: PositiveInt? = x.toPositiveIntOrNull()
        result.assertNotNull().value assertEquals x.value
    }

    @Suppress("DEPRECATION")
    @Test
    fun toPositiveIntOrNull_should_return_null_with_a_strictly_negative_value() {
        val x = NonZeroInt(StrictlyNegativeInt.random.value)
        val result: PositiveInt? = x.toPositiveIntOrNull()
        result.assertNull()
    }

    @Suppress("DEPRECATION")
    @Test
    fun toStrictlyPositiveInt_should_pass_with_a_strictly_positive_value() {
        val x = NonZeroInt(StrictlyPositiveInt.random.value)
        val result: StrictlyPositiveInt = x.toStrictlyPositiveInt()
        result.value assertEquals x.value
    }

    @Suppress("DEPRECATION")
    @Test
    fun toStrictlyPositiveInt_should_throw_an_error_with_a_strictly_negative_value() {
        val x = NonZeroInt(StrictlyNegativeInt.random.value)
        assertFailsWith<IllegalArgumentException>(x::toStrictlyPositiveInt)
    }

    @Suppress("DEPRECATION")
    @Test
    fun toStrictlyPositiveIntOrNull_should_pass_with_a_strictly_positive_value() {
        val x = NonZeroInt(StrictlyPositiveInt.random.value)
        val result: StrictlyPositiveInt? = x.toStrictlyPositiveIntOrNull()
        result.assertNotNull().value assertEquals x.value
    }

    @Suppress("DEPRECATION")
    @Test
    fun toStrictlyPositiveIntOrNull_should_return_null_with_a_strictly_negative_value() {
        val x = NonZeroInt(StrictlyNegativeInt.random.value)
        val result: StrictlyPositiveInt? = x.toStrictlyPositiveIntOrNull()
        result.assertNull()
    }

    @Suppress("DEPRECATION")
    @Test
    fun toNegativeInt_should_pass_with_a_strictly_negative_value() {
        val x = NonZeroInt(StrictlyNegativeInt.random.value)
        val result: NegativeInt = x.toNegativeInt()
        result.value assertEquals x.value
    }

    @Suppress("DEPRECATION")
    @Test
    fun toNegativeInt_should_throw_an_error_with_a_strictly_positive_value() {
        val x = NonZeroInt(StrictlyPositiveInt.random.value)
        assertFailsWith<IllegalArgumentException>(x::toNegativeInt)
    }

    @Suppress("DEPRECATION")
    @Test
    fun toNegativeIntOrNull_should_pass_with_a_strictly_negative_value() {
        val x = NonZeroInt(StrictlyNegativeInt.random.value)
        val result: NegativeInt? = x.toNegativeIntOrNull()
        result.assertNotNull().value assertEquals x.value
    }

    @Suppress("DEPRECATION")
    @Test
    fun toNegativeIntOrNull_should_return_null_with_a_strictly_positive_value() {
        val x = NonZeroInt(StrictlyPositiveInt.random.value)
        val result: NegativeInt? = x.toNegativeIntOrNull()
        result.assertNull()
    }

    @Suppress("DEPRECATION")
    @Test
    fun toStrictlyNegativeInt_should_pass_with_a_strictly_negative_value() {
        val x = NonZeroInt(StrictlyNegativeInt.random.value)
        val result: StrictlyNegativeInt = x.toStrictlyNegativeInt()
        result.value assertEquals x.value
    }

    @Suppress("DEPRECATION")
    @Test
    fun toStrictlyNegativeInt_should_throw_an_error_with_a_strictly_positive_value() {
        val x = NonZeroInt(StrictlyPositiveInt.random.value)
        assertFailsWith<IllegalArgumentException>(x::toStrictlyNegativeInt)
    }

    @Suppress("DEPRECATION")
    @Test
    fun toStrictlyNegativeIntOrNull_should_pass_with_a_strictly_negative_value() {
        val x = NonZeroInt(StrictlyNegativeInt.random.value)
        val result: StrictlyNegativeInt? = x.toStrictlyNegativeIntOrNull()
        result.assertNotNull().value assertEquals x.value
    }

    @Suppress("DEPRECATION")
    @Test
    fun toStrictlyNegativeIntOrNull_should_return_null_with_a_strictly_positive_value() {
        val x = NonZeroInt(StrictlyPositiveInt.random.value)
        val result: StrictlyNegativeInt? = x.toStrictlyNegativeIntOrNull()
        result.assertNull()
    }

    @Test
    fun toString_should_return_the_string_representation_of_the_value() {
        val x: NonZeroInt = NonZeroInt.random
        val result: String = x.toString()
        result assertEquals x.value.toString()
    }
}
