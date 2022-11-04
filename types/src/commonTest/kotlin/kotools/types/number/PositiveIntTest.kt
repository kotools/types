package kotools.types.number

import kotools.assert.*
import kotlin.test.Test

class PositiveIntTest {
    // ---------- Companion ----------

    @Test
    fun min_should_be_zero(): Unit = PositiveInt.min.value assertEquals 0

    @Test
    fun max_should_be_the_maximum_of_Int(): Unit =
        PositiveInt.max.value assertEquals Int.MAX_VALUE

    @Suppress("DEPRECATION")
    @Test
    fun random_should_return_different_values(): Unit =
        PositiveInt.random().value assertNotEquals PositiveInt.random().value

    // ---------- Builders ----------

    @Test
    fun randomPositiveInt_should_return_different_values() {
        val x: PositiveInt = randomPositiveInt()
        val y: PositiveInt = randomPositiveInt()
        x.value assertNotEquals y.value
    }

    // ---------- Unary operations ----------

    @Test
    fun inc_should_return_the_minimum_value_with_the_maximum_value() {
        var x: PositiveInt = PositiveInt.max
        x++
        x assertEquals PositiveInt.min
    }

    @Test
    fun inc_should_increment_the_value_by_1_with_an_initial_value_other_than_the_maximum_value() {
        var x: PositiveInt = randomPositiveInt()
        while (x.value == PositiveInt.max.value) x = randomPositiveInt()
        val initialValue: Int = x.value
        x++
        x.value assertEquals initialValue + 1
    }

    @Test
    fun dec_should_return_the_maximum_value_with_the_minimum_value() {
        var x: PositiveInt = PositiveInt.min
        x--
        x assertEquals PositiveInt.max
    }

    @Test
    fun dec_should_decrement_the_value_by_1_with_an_initial_value_other_than_the_minimum_value() {
        var x: PositiveInt = randomPositiveInt()
        while (x.value == PositiveInt.min.value) x = randomPositiveInt()
        val initialValue: Int = x.value
        x--
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass() {
        val x: PositiveInt = randomPositiveInt()
        val result: NegativeInt = -x
        result.value assertEquals -x.value
    }

    // ---------- Binary operations ----------

    @Test
    fun div_should_pass_with_a_StrictlyPositiveInt() {
        val x: PositiveInt = randomPositiveInt()
        val y: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val result: PositiveInt = x / y
        result.value assertEquals x.value / y.value
    }

    @Test
    fun div_should_pass_with_a_StrictlyNegativeInt() {
        val x: PositiveInt = randomPositiveInt()
        val y: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val result: NegativeInt = x / y
        result.value assertEquals x.value / y.value
    }

    // ---------- Conversions ----------

    @Test
    fun toString_should_pass() {
        val x: PositiveInt = randomPositiveInt()
        val result: String = x.toString()
        result assertEquals x.value.toString()
    }
}

@Suppress("unused")
class PositiveIntSerializerTest : IntHolderSerializerTest<PositiveInt>(
    PositiveIntSerializer,
    ::randomPositiveInt
)
