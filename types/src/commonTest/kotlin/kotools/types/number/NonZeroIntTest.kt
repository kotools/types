package kotools.types.number

import kotools.assert.*
import kotools.types.*
import kotools.types.core.RandomValueHolder
import kotools.types.string.toNotBlankStringOrNull
import kotlin.test.Test

class NonZeroIntTest : RandomValueHolder {
    // ---------- Companion ----------

    @Test
    fun ranges_should_not_contain_zero(): Unit = NonZeroInt.ranges
        .all { 0 !in it }
        .assertTrue()

    @Test
    fun min_should_be_the_minimum_of_Int(): Unit =
        NonZeroInt.min.value assertEquals Int.MIN_VALUE

    @Test
    fun max_should_be_the_maximum_of_Int(): Unit =
        NonZeroInt.max.value assertEquals Int.MAX_VALUE

    @Suppress("DEPRECATION")
    @Test
    fun random_should_return_different_values(): Unit =
        NonZeroInt.random().value assertNotEquals NonZeroInt.random().value

    // ---------- Builders ----------

    @Test
    fun int_toNonZeroIntOrNull_should_pass_with_a_non_zero_Int() {
        val value: Int = randomNonZeroInt().value
        val result: NonZeroInt? = value.toNonZeroIntOrNull()
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun int_toNonZeroIntOrNull_should_return_null_with_zero() {
        val result: NonZeroInt? = 0.toNonZeroIntOrNull()
        result.assertNull()
    }

    @Test
    fun int_toNonZeroIntOrThrow_should_pass_with_a_non_zero_Int() {
        val value: Int = randomNonZeroInt().value
        val result: NonZeroInt = value.toNonZeroIntOrThrow()
        result.value assertEquals value
    }

    @Test
    fun int_toNonZeroIntOrThrow_should_throw_an_error_with_zero() {
        assertFailsWith<IllegalArgumentException>(0::toNonZeroIntOrThrow)
            .message
            ?.toNotBlankStringOrNull()
            .assertNotNull()
    }

    @Test
    fun randomNonZeroInt_should_return_different_values() {
        val x: NonZeroInt = randomNonZeroInt()
        val y: NonZeroInt = randomNonZeroInt()
        x.value assertNotEquals y.value
    }

    // ---------- Unary operations ----------

    @Test
    fun inc_should_return_1_with_minus_1() {
        var x: NonZeroInt = (-1).toNonZeroIntOrThrow()
        x++
        x.value assertEquals 1
    }

    @Test
    fun inc_should_return_the_minimum_with_the_maximum() {
        var x = NonZeroInt.max
        x++
        x assertEquals NonZeroInt.min
    }

    @Test
    fun inc_should_increment_the_value_with_a_value_other_than_minus1_and_the_maximum() {
        var x: NonZeroInt = randomNonZeroInt()
        while (x.value == -1 || x.value == NonZeroInt.max.value)
            x = randomNonZeroInt()
        val initialValue: Int = x.value
        x++
        x.value assertEquals initialValue + 1
    }

    @Test
    fun dec_should_return_minus1_with_1() {
        var x: NonZeroInt = 1.toNonZeroIntOrThrow()
        x--
        x.value assertEquals -1
    }

    @Test
    fun dec_should_return_the_maximum_with_the_minimum() {
        var x: NonZeroInt = NonZeroInt.min
        x--
        x assertEquals NonZeroInt.max
    }

    @Test
    fun dec_should_decrement_the_value_with_a_value_other_than_1_and_the_minimum() {
        var x: NonZeroInt = randomNonZeroInt()
        while (x.value == 1 || x.value == NonZeroInt.min.value)
            x = randomNonZeroInt()
        val initialValue: Int = x.value
        x--
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass() {
        val x: NonZeroInt = randomNonZeroInt()
        val result: NonZeroInt = -x
        result.value assertEquals -x.value
    }

    // ---------- Binary operations ----------

    @Test
    fun times_should_pass_with_a_NonZeroInt() {
        val x: NonZeroInt = randomNonZeroInt()
        val y: NonZeroInt = randomNonZeroInt()
        val result: NonZeroInt = x * y
        result.value assertEquals x.value * y.value
    }

    @Test
    fun div_should_pass_when_dividing_an_Int_by_a_NonZeroInt() {
        val x: Int = randomInt
        val y: NonZeroInt = randomNonZeroInt()
        val result: Int = x / y
        result assertEquals x / y.value
    }

    // ---------- Conversions ----------

    @Test
    fun toString_should_pass() {
        val x: NonZeroInt = randomNonZeroInt()
        val result: String = x.toString()
        result assertEquals x.value.toString()
    }
}

@Suppress("unused")
class NonZeroIntSerializerTest : IntHolderSerializerTest<NonZeroInt>(
    NonZeroIntSerializer,
    ::randomNonZeroInt
)
