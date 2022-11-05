package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertNotEquals
import kotools.types.*
import kotlin.test.Test

class NegativeIntTest {
    // ---------- Companion ----------

    @Test
    fun min_should_be_the_minimum_of_Int(): Unit =
        NegativeInt.min.value assertEquals Int.MIN_VALUE

    @Test
    fun max_should_be_zero(): Unit = NegativeInt.max.value assertEquals 0

    @Suppress("DEPRECATION")
    @Test
    fun random_should_return_different_values() {
        val x: NegativeInt = NegativeInt.random()
        val y: NegativeInt = NegativeInt.random()
        x.value assertNotEquals y.value
    }

    // ---------- Builders ----------

    @Test
    fun randomNegativeInt_should_return_different_values() {
        val x: NegativeInt = randomNegativeInt()
        val y: NegativeInt = randomNegativeInt()
        x.value assertNotEquals y.value
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
        var x: NegativeInt = randomNegativeInt()
        while (x.value == NegativeInt.max.value) x = randomNegativeInt()
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
        var x: NegativeInt = randomNegativeInt()
        while (x.value == NegativeInt.min.value) x = randomNegativeInt()
        val initialValue: Int = x.value
        x--
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass() {
        val x: NegativeInt = randomNegativeInt()
        val result: PositiveInt = -x
        result.value assertEquals -x.value
    }

    // ---------- Binary operations ----------

    @Test
    fun div_should_pass_with_a_StrictlyPositiveInt() {
        val x: NegativeInt = randomNegativeInt()
        val y: StrictlyPositiveInt = randomStrictlyPositiveInt()
        val result: NegativeInt = x / y
        result.value assertEquals x.value / y.value
    }

    @Test
    fun div_should_pass_with_a_StrictlyNegativeInt() {
        val x: NegativeInt = randomNegativeInt()
        val y: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val result: PositiveInt = x / y
        result.value assertEquals x.value / y.value
    }

    // ---------- Conversions ----------

    @Test
    fun toString_should_pass() {
        val x: NegativeInt = randomNegativeInt()
        val result: String = x.toString()
        result assertEquals x.value.toString()
    }
}

@Suppress("unused")
class NegativeIntSerializerTest : IntHolderSerializerTest<NegativeInt>(
    NegativeIntSerializer,
    ::randomNegativeInt
)
