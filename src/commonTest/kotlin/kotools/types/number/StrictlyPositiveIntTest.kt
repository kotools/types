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

class StrictlyPositiveIntTest : RandomValueHolder {
    // ---------- Builders ----------

    @Test
    fun constructor_should_pass_with_a_strictly_positive_Int() {
        var value: Int = randomInt
        while (0 >= value) value = randomInt
        val result = StrictlyPositiveInt(value)
        result.value assertEquals value
    }

    @Test
    fun constructor_should_throw_an_error_with_a_negative_Int() {
        val value: Int = NegativeInt.random.value
        assertFailsWith<IllegalArgumentException> { StrictlyPositiveInt(value) }
    }

    @Test
    fun companion_orNull_should_pass_with_a_strictly_positive_Int() {
        var value: Int = randomInt
        while (0 >= value) value = randomInt
        val result: StrictlyPositiveInt? = StrictlyPositiveInt orNull value
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companion_orNull_should_return_null_with_a_negative_Int() {
        val value: Int = NegativeInt.random.value
        val result: StrictlyPositiveInt? = StrictlyPositiveInt orNull value
        result.assertNull()
    }

    @Test
    fun int_toStrictlyPositiveInt_should_pass_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.random.value
        val result: StrictlyPositiveInt = value.toStrictlyPositiveInt()
        result.value assertEquals value
    }

    @Test
    fun int_toStrictlyPositiveInt_should_throw_an_error_with_a_negative_Int() {
        val value: Int = NegativeInt.random.value
        assertFailsWith<IllegalArgumentException>(value::toStrictlyPositiveInt)
    }

    @Test
    fun int_toStrictlyPositiveIntOrNull_should_pass_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.random.value
        val result: StrictlyPositiveInt? = value.toStrictlyPositiveIntOrNull()
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun int_toStrictlyPositiveIntOrNull_should_return_null_with_a_negative_Int() {
        val value: Int = NegativeInt.random.value
        val result: StrictlyPositiveInt? = value.toStrictlyPositiveIntOrNull()
        result.assertNull()
    }

    // ---------- Serialization ----------

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        val result: String = Json.encodeToString(x)
        result assertEquals Json.encodeToString(x.value)
    }

    @Test
    fun deserialization_should_pass() {
        val value: Int = StrictlyPositiveInt.random.value
        val encoded: String = Json.encodeToString(value)
        val result: StrictlyPositiveInt = Json.decodeFromString(encoded)
        result.value assertEquals value
    }

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

    // ---------- Binary operations ----------

    @Test
    fun compareTo_should_pass_with_another_StrictlyPositiveInt() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        val y: StrictlyPositiveInt = StrictlyPositiveInt.random
        val result: Int = x.compareTo(y)
        result assertEquals x.value.compareTo(y.value)
    }

    // ---------- Conversions ----------

    @Test
    fun toNonZeroInt_should_pass() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        val result: NonZeroInt = x.toNonZeroInt()
        result.value assertEquals x.value
    }

    @Test
    fun toPositiveInt_should_pass() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        val result: PositiveInt = x.toPositiveInt()
        result.value assertEquals x.value
    }

    @Test
    fun toString_should_return_the_string_representation_of_the_value() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        val result: String = x.toString()
        result assertEquals x.value.toString()
    }
}