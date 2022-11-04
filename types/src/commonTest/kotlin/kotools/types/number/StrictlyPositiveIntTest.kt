package kotools.types.number

import kotools.assert.*
import kotlin.test.Test

class StrictlyPositiveIntTest {
    // ---------- Companion ----------

    @Test
    fun min_should_be_one(): Unit = StrictlyPositiveInt.min.value assertEquals 1

    @Test
    fun max_should_be_the_maximum_of_Int(): Unit =
        StrictlyPositiveInt.max.value assertEquals Int.MAX_VALUE

    @Suppress("DEPRECATION")
    @Test
    fun random_should_return_different_values() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val y: StrictlyPositiveInt = StrictlyPositiveInt.random()
        x.value assertNotEquals y.value
    }

    // ---------- Builders ----------

    @Test
    fun randomStrictlyPositiveInt_should_return_different_values() {
        val x: StrictlyPositiveInt = randomStrictlyPositiveInt()
        val y: StrictlyPositiveInt = randomStrictlyPositiveInt()
        x.value assertNotEquals y.value
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
        var x: StrictlyPositiveInt = randomStrictlyPositiveInt()
        while (x.value == StrictlyPositiveInt.max.value)
            x = randomStrictlyPositiveInt()
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
        var x: StrictlyPositiveInt = randomStrictlyPositiveInt()
        while (x.value == StrictlyPositiveInt.min.value)
            x = randomStrictlyPositiveInt()
        val initialValue: Int = x.value
        x--
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass() {
        val x: StrictlyPositiveInt = randomStrictlyPositiveInt()
        val result: StrictlyNegativeInt = -x
        result.value assertEquals -x.value
    }

    // ---------- Conversions ----------

    @Test
    fun toString_should_pass() {
        val x: StrictlyPositiveInt = randomStrictlyPositiveInt()
        val result: String = x.toString()
        result assertEquals x.value.toString()
    }
}

@Suppress("unused")
class StrictlyPositiveIntSerializerTest :
    IntHolderSerializerTest<StrictlyPositiveInt>(
        StrictlyPositiveIntSerializer,
        ::randomStrictlyPositiveInt
    )
