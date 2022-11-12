package kotools.types.number

import kotools.assert.*
import kotools.types.*
import kotlin.test.Test

class StrictlyNegativeIntTest {
    // ---------- Companion ----------

    @Test
    fun min_should_be_the_minimum_of_Int(): Unit =
        StrictlyNegativeInt.min.value assertEquals Int.MIN_VALUE

    @Test
    fun max_should_be_minus1(): Unit =
        StrictlyNegativeInt.max.value assertEquals -1

    @Suppress("DEPRECATION")
    @Test
    fun random_should_return_different_values() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val y: StrictlyNegativeInt = StrictlyNegativeInt.random()
        x.value assertNotEquals y.value
    }

    // ---------- Builders ----------

    @Test
    fun strictlyNegativeIntOrNull_should_pass_with_a_strictly_negative_Int() {
        val value: Int = randomStrictlyNegativeInt().value
        val result: StrictlyNegativeInt? = strictlyNegativeIntOrNull(value)
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun strictlyNegativeIntOrNull_should_return_null_with_a_positive_Int() {
        val value: Int = randomPositiveInt().value
        val result: StrictlyNegativeInt? = strictlyNegativeIntOrNull(value)
        result.assertNull()
    }

    @Test
    fun strictlyNegativeIntOrThrow_should_pass_with_a_strictly_negative_Int() {
        val value: Int = randomStrictlyNegativeInt().value
        val result: StrictlyNegativeInt = strictlyNegativeIntOrThrow(value)
        result.value assertEquals value
    }

    @Test
    fun strictlyNegativeIntOrThrow_should_throw_an_error_with_a_positive_Int() {
        val value: Int = randomPositiveInt().value
        val result: IllegalArgumentException =
            assertFailsWith { strictlyNegativeIntOrThrow(value) }
        result.message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    @Test
    fun int_toStrictlyNegativeIntOrThrow_should_pass_with_a_strictly_negative_Int() {
        val value: Int = randomStrictlyNegativeInt().value
        val result: StrictlyNegativeInt = value.toStrictlyNegativeIntOrThrow()
        result.value assertEquals value
    }

    @Test
    fun int_toStrictlyNegativeIntOrThrow_should_throw_an_error_with_a_positive_Int() {
        val value: Int = randomPositiveInt().value
        val result: IllegalArgumentException =
            assertFailsWith(value::toStrictlyNegativeIntOrThrow)
        result.message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    @Test
    fun randomStrictlyNegativeInt_should_return_different_values() {
        val x: StrictlyNegativeInt = randomStrictlyNegativeInt()
        val y: StrictlyNegativeInt = randomStrictlyNegativeInt()
        x.value assertNotEquals y.value
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
        var x: StrictlyNegativeInt = randomStrictlyNegativeInt()
        while (x.value == StrictlyNegativeInt.max.value)
            x = randomStrictlyNegativeInt()
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
        var x: StrictlyNegativeInt = randomStrictlyNegativeInt()
        while (x.value == StrictlyNegativeInt.min.value)
            x = randomStrictlyNegativeInt()
        val initialValue: Int = x.value
        x--
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass() {
        val x: StrictlyNegativeInt = randomStrictlyNegativeInt()
        val result: StrictlyPositiveInt = -x
        result.value assertEquals -x.value
    }

    // ---------- Conversions ----------

    @Test
    fun toString_should_pass() {
        val x: StrictlyNegativeInt = randomStrictlyNegativeInt()
        val result: String = x.toString()
        result assertEquals x.value.toString()
    }
}

@Suppress("unused")
class StrictlyNegativeIntSerializerTest :
    IntHolderSerializerTest<StrictlyNegativeInt>(
        StrictlyNegativeIntSerializer,
        ::randomStrictlyNegativeInt
    )
