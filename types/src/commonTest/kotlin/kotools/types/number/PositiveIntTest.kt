package kotools.types.number

import kotools.assert.*
import kotools.types.assertEquals
import kotools.types.pairBy
import kotools.types.runMapSecond
import kotlin.test.Test

class PositiveIntTest {
    // ---------- Companion ----------

    @Test
    fun min_should_be_zero(): Unit = PositiveInt.min.value assertEquals 0

    @Test
    fun max_should_be_the_maximum_of_Int(): Unit =
        PositiveInt.max.value assertEquals Int.MAX_VALUE

    @Test
    fun random_should_return_different_values(): Unit =
        PositiveInt.random().value assertNotEquals PositiveInt.random().value

    // ---------- Unary operations ----------

    @Test
    fun inc_should_return_the_minimum_value_with_the_maximum_value() {
        var x: PositiveInt = PositiveInt.max
        x++
        x assertEquals PositiveInt.min
    }

    @Test
    fun inc_should_increment_the_value_by_1_with_an_initial_value_other_than_the_maximum_value() {
        var x: PositiveInt = PositiveInt.random()
        while (x.value == PositiveInt.max.value) x = PositiveInt.random()
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
        var x: PositiveInt = PositiveInt.random()
        while (x.value == PositiveInt.min.value) x = PositiveInt.random()
        val initialValue: Int = x.value
        x--
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass(): Unit = PositiveInt.random()
        .let { -it to it }
        .run { first.value assertEquals -second.value }

    // ---------- Binary operations ----------

    @Test
    fun div_should_pass_with_a_StrictlyPositiveInt(): Unit =
        (PositiveInt.random() to StrictlyPositiveInt.random())
            .run { first / second to first.value / second.value }
            .run { first.value assertEquals second }

    @Test
    fun div_should_pass_with_a_StrictlyNegativeInt(): Unit =
        (PositiveInt.random() to StrictlyNegativeInt.random())
            .run { first / second to first.value / second.value }
            .run { first.value assertEquals second }

    // ---------- Conversions ----------

    @Test
    fun toString_should_pass(): Unit = PositiveInt.random()
        .pairBy(PositiveInt::toString)
        .runMapSecond { value.toString() }
        .assertEquals()
}

@Suppress("unused")
class PositiveIntSerializerTest : IntHolderSerializerTest<PositiveInt>(
    PositiveIntSerializer,
    PositiveInt.Companion::random
)
