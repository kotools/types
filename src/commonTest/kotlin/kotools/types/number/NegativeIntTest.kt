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

class NegativeIntTest {
    // ---------- Builders ----------

    @Test
    fun constructor_should_pass_with_a_negative_Int() {
        var value: Int = Random.nextInt()
        while (0 < value) value = Random.nextInt()
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
        var value: Int = Random.nextInt()
        while (0 < value) value = Random.nextInt()
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
    fun string_toNegativeInt_should_pass_with_a_String_representing_a_negative_number() {
        val value: Int = NegativeInt.random.value
        val valueAsString: String = value.toString()
        val result: NegativeInt = valueAsString.toNegativeInt()
        result.value assertEquals value
    }

    @Test
    fun string_toNegativeInt_should_throw_an_error_with_an_invalid_String() {
        assertFailsWith<NumberFormatException>("a"::toNegativeInt)
    }

    @Test
    fun string_toNegativeInt_should_throw_an_error_with_a_String_representing_a_strictly_positive_number() {
        val value: String = StrictlyPositiveInt.random.value.toString()
        assertFailsWith<IllegalArgumentException>(value::toNegativeInt)
    }

    @Test
    fun string_toNegativeOrNull_should_pass_with_a_String_representing_a_negative_number() {
        val value: Int = NegativeInt.random.value
        val valueAsString: String = value.toString()
        val result: NegativeInt? = valueAsString.toNegativeIntOrNull()
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun string_toNegativeIntOrNull_should_return_null_with_an_invalid_String() {
        val result: NegativeInt? = "a".toNegativeIntOrNull()
        result.assertNull()
    }

    @Test
    fun string_toNegativeIntOrNull_should_return_null_with_a_String_representing_a_strictly_positive_number() {
        val value: String = StrictlyPositiveInt.random.value.toString()
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
}
