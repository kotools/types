package kotools.types.number

import kotools.assert.*
import kotools.types.assertEquals
import kotools.types.pairBy
import kotools.types.runMapSecond
import kotlin.test.Test

class StrictlyPositiveIntTest {
    // ---------- Companion ----------

    @Test
    fun min_should_be_one(): Unit = StrictlyPositiveInt.min.value assertEquals 1

    @Test
    fun max_should_be_the_maximum_of_Int(): Unit =
        StrictlyPositiveInt.max.value assertEquals Int.MAX_VALUE

    @Test
    fun random_should_return_different_values(): Unit = StrictlyPositiveInt
        .random()
        .value assertNotEquals StrictlyPositiveInt.random().value

    // ---------- Unary operations ----------

    @Test
    fun inc_should_return_the_minimum_value_with_the_maximum_value() {
        var x: StrictlyPositiveInt = StrictlyPositiveInt.max
        x++
        x assertEquals StrictlyPositiveInt.min
    }

    @Test
    fun inc_should_increment_the_value_by_one_with_an_initial_value_other_than_the_maximum() {
        var x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        while (x.value == StrictlyPositiveInt.max.value)
            x = StrictlyPositiveInt.random()
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
        var x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        while (x.value == StrictlyPositiveInt.min.value)
            x = StrictlyPositiveInt.random()
        val initialValue: Int = x.value
        x--
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass(): Unit = StrictlyPositiveInt.random()
        .let { -it to it }
        .run { first.value assertEquals -second.value }

    // ---------- Conversions ----------

    @Test
    fun toString_should_pass(): Unit = StrictlyPositiveInt.random()
        .pairBy(StrictlyPositiveInt::toString)
        .runMapSecond { value.toString() }
        .assertEquals()
}

@Suppress("unused")
class StrictlyPositiveIntSerializerTest :
    IntHolderSerializerTest<StrictlyPositiveInt>(
        StrictlyPositiveIntSerializer,
        StrictlyPositiveInt.Companion::random
    )
