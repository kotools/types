package kotools.types.number

import kotools.assert.*
import kotools.types.assertEquals
import kotools.types.pairBy
import kotools.types.runMapSecond
import kotlin.test.Test

@Suppress("TestFunctionName")
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

    // ---------- Builders ----------

    @Test
    fun PositiveInt_should_pass_with_a_positive_Int(): Unit =
        PositiveInt.random()
            .run { PositiveInt(value) to value }
            .run { first.value assertEquals second }

    @Test
    fun PositiveInt_should_throw_an_error_with_a_strictly_negative_Int(): Unit =
        StrictlyNegativeInt.random()
            .runCatching { PositiveInt(value) }
            .exceptionOrNull()
            .assertNotNull()
            .apply { message.assertNotNull() }
            .let { it is PositiveInt.ConstructionError }
            .assertTrue()

    @Test
    fun PositiveIntOrNull_should_pass_with_a_positive_Int(): Unit =
        PositiveInt.random()
            .run { PositiveIntOrNull(value) to value }
            .run { first.assertNotNull() to second }
            .run { first.value assertEquals second }

    @Test
    fun PositiveIntOrNull_should_return_null_with_a_strictly_negative_Int(): Unit =
        StrictlyNegativeInt.random()
            .run { PositiveIntOrNull(value) }
            .assertNull()

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
