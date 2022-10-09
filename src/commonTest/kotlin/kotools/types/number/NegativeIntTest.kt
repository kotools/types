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

class NegativeIntTest : RandomValueHolder {
    // ---------- Builders ----------

    @Test
    fun constructor_should_pass_with_a_negative_Int() {
        var value: Int = randomInt
        while (0 < value) value = randomInt
        val result = NegativeInt(value)
        result.value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.random.value
        assertFailsWith<IllegalArgumentException> { NegativeInt(value) }
    }

    @Test
    fun companion_orNull_should_pass_with_a_negative_Int() {
        var value: Int = randomInt
        while (0 < value) value = randomInt
        val result: NegativeInt? = NegativeInt orNull value
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companion_orNull_should_return_null_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.random.value
        val result: NegativeInt? = NegativeInt orNull value
        result.assertNull()
    }

    @Test
    fun int_toNegativeInt_should_pass_with_a_negative_Int() {
        val value: Int = NegativeInt.random.value
        val result: NegativeInt = value.toNegativeInt()
        result.value assertEquals value
    }

    @Test
    fun int_toNegativeInt_should_throw_an_error_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.random.value
        assertFailsWith<IllegalArgumentException>(value::toNegativeInt)
    }

    @Test
    fun int_toNegativeIntOrNull_should_pass_with_a_negative_Int() {
        val value: Int = NegativeInt.random.value
        val result: NegativeInt? = value.toNegativeIntOrNull()
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun int_toNegativeIntOrNull_should_return_null_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.random.value
        val result: NegativeInt? = value.toNegativeIntOrNull()
        result.assertNull()
    }

    // ---------- Serialization ----------

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: NegativeInt = NegativeInt.random
        val result: String = Json.encodeToString(x)
        result assertEquals Json.encodeToString(x.value)
    }

    @Test
    fun deserialization_should_pass() {
        val value: Int = NegativeInt.random.value
        val encoded: String = Json.encodeToString(value)
        val result: NegativeInt = Json.decodeFromString(encoded)
        result.value assertEquals value
    }

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

    // ---------- Binary operations ----------

    @Test
    fun compareTo_should_pass_with_another_NegativeInt() {
        val x: NegativeInt = NegativeInt.random
        val y: NegativeInt = NegativeInt.random
        val result: Int = x.compareTo(y)
        result assertEquals x.value.compareTo(y.value)
    }

    // ---------- Conversions ----------

    @Suppress("DEPRECATION")
    @Test
    fun toNonZeroInt_should_pass_with_a_strictly_negative_value() {
        val x = NegativeInt(StrictlyNegativeInt.random.value)
        val result: NonZeroInt = x.toNonZeroInt()
        result.value assertEquals x.value
    }

    @Suppress("DEPRECATION")
    @Test
    fun toNonZeroInt_should_throw_an_error_with_a_value_that_equals_zero() {
        val x = NegativeInt(0)
        assertFailsWith<IllegalArgumentException>(x::toNonZeroInt)
    }

    @Suppress("DEPRECATION")
    @Test
    fun toNonZeroIntOrNull_should_pass_with_a_strictly_negative_value() {
        val x = NegativeInt(StrictlyNegativeInt.random.value)
        val result: NonZeroInt? = x.toNonZeroIntOrNull()
        result.assertNotNull().value assertEquals x.value
    }

    @Suppress("DEPRECATION")
    @Test
    fun toNonZeroIntOrNull_should_return_null_with_a_value_that_equals_zero() {
        val x = NegativeInt(0)
        val result: NonZeroInt? = x.toNonZeroIntOrNull()
        result.assertNull()
    }

    @Suppress("DEPRECATION")
    @Test
    fun toPositiveInt_should_pass_with_a_value_that_equals_zero() {
        val x = NegativeInt(0)
        val result: PositiveInt = x.toPositiveInt()
        result.value assertEquals x.value
    }

    @Suppress("DEPRECATION")
    @Test
    fun toPositiveInt_should_throw_an_error_with_a_strictly_negative_value() {
        val x = NegativeInt(StrictlyNegativeInt.random.value)
        assertFailsWith<IllegalArgumentException>(x::toPositiveInt)
    }

    @Suppress("DEPRECATION")
    @Test
    fun toPositiveIntOrNull_should_pass_with_a_value_that_equals_zero() {
        val x = NegativeInt(0)
        val result: PositiveInt? = x.toPositiveIntOrNull()
        result?.value assertEquals x.value
    }

    @Suppress("DEPRECATION")
    @Test
    fun toPositiveIntOrNull_should_return_null_with_a_strictly_negative_value() {
        val x = NegativeInt(StrictlyNegativeInt.random.value)
        val result: PositiveInt? = x.toPositiveIntOrNull()
        result.assertNull()
    }

    @Suppress("DEPRECATION")
    @Test
    fun toStrictlyNegativeInt_should_pass_with_a_strictly_negative_value() {
        val x = NegativeInt(StrictlyNegativeInt.random.value)
        val result: StrictlyNegativeInt = x.toStrictlyNegativeInt()
        result.value assertEquals x.value
    }

    @Suppress("DEPRECATION")
    @Test
    fun toStrictlyNegativeInt_should_throw_an_error_with_a_value_that_equals_zero() {
        val x = NegativeInt(0)
        assertFailsWith<IllegalArgumentException>(x::toStrictlyNegativeInt)
    }

    @Suppress("DEPRECATION")
    @Test
    fun toStrictlyNegativeIntOrNull_should_pass_with_a_strictly_negative_value() {
        val x = NegativeInt(StrictlyNegativeInt.random.value)
        val result: StrictlyNegativeInt? = x.toStrictlyNegativeIntOrNull()
        result?.value assertEquals x.value
    }

    @Suppress("DEPRECATION")
    @Test
    fun toStrictlyNegativeIntOrNull_should_return_null_with_a_value_that_equals_zero() {
        val x = NegativeInt(0)
        val result: StrictlyNegativeInt? = x.toStrictlyNegativeIntOrNull()
        result.assertNull()
    }

    @Test
    fun toString_should_return_the_string_representation_of_the_value() {
        val x: NegativeInt = NegativeInt.random
        val result: String = x.toString()
        result assertEquals x.value.toString()
    }
}
