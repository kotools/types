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

class StrictlyNegativeIntTest : RandomValueHolder {
    // ---------- Builders ----------

    @Test
    fun constructor_should_pass_with_a_strictly_negative_Int() {
        var value: Int = randomInt
        while (0 <= value) value = randomInt
        val result = StrictlyNegativeInt(value)
        result.value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_a_positive_Int() {
        val value: Int = PositiveInt.random.value
        assertFailsWith<IllegalArgumentException> { StrictlyNegativeInt(value) }
    }

    @Test
    fun companion_orNull_should_pass_with_a_strictly_negative_Int() {
        var value: Int = randomInt
        while (0 <= value) value = randomInt
        val result: StrictlyNegativeInt? = StrictlyNegativeInt orNull value
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companion_orNull_should_return_null_with_a_positive_Int() {
        val value: Int = PositiveInt.random.value
        val result: StrictlyNegativeInt? = StrictlyNegativeInt orNull value
        result.assertNull()
    }

    @Test
    fun int_toStrictlyNegativeInt_should_pass_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.random.value
        val result: StrictlyNegativeInt = value.toStrictlyNegativeInt()
        result.value assertEquals value
    }

    @Test
    fun int_toStrictlyNegativeInt_should_throw_an_error_with_a_positive_Int() {
        val value: Int = PositiveInt.random.value
        assertFailsWith<IllegalArgumentException>(value::toStrictlyNegativeInt)
    }

    @Test
    fun int_toStrictlyNegativeIntOrNull_should_pass_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.random.value
        val result: StrictlyNegativeInt? = value.toStrictlyNegativeIntOrNull()
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun int_toStrictlyNegativeIntOrNull_should_return_null_with_a_positive_Int() {
        val value: Int = PositiveInt.random.value
        val result: StrictlyNegativeInt? = value.toStrictlyNegativeIntOrNull()
        result.assertNull()
    }

    // ---------- Serialization ----------

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        val result: String = Json.encodeToString(x)
        result assertEquals Json.encodeToString(x.value)
    }

    @Test
    fun deserialization_should_pass() {
        val value: Int = StrictlyNegativeInt.random.value
        val encoded: String = Json.encodeToString(value)
        val result: StrictlyNegativeInt = Json.decodeFromString(encoded)
        result.value assertEquals value
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

    // ---------- Binary operations ----------

    @Test
    fun compareTo_should_pass_with_another_StrictlyNegativeInt() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        val y: StrictlyNegativeInt = StrictlyNegativeInt.random
        val result: Int = x.compareTo(y)
        result assertEquals x.value.compareTo(y.value)
    }

    // ---------- Conversions ----------

    @Test
    fun toNonZeroInt_should_pass() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        val result: NonZeroInt = x.toNonZeroInt()
        result.value assertEquals x.value
    }

    @Test
    fun toNegativeInt_should_pass() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        val result: NegativeInt = x.toNegativeInt()
        result.value assertEquals x.value
    }

    @Test
    fun toString_should_return_the_string_representation_of_the_value() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random
        val result: String = x.toString()
        result assertEquals x.value.toString()
    }
}
