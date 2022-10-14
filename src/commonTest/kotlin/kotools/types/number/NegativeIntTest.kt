package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertNotEquals
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotools.types.*
import kotlin.test.Test

@Suppress("TestFunctionName")
class NegativeIntTest {
    // ---------- Companion ----------

    @Test
    fun min_should_be_the_minimum_of_Int(): Unit =
        NegativeInt.min.value assertEquals Int.MIN_VALUE

    @Test
    fun max_should_be_zero(): Unit = NegativeInt.max.value assertEquals 0

    @Test
    fun random_should_return_different_values(): Unit =
        NegativeInt.random().value assertNotEquals NegativeInt.random().value

    // ---------- Builders ----------

    @Test
    fun NegativeInt_should_pass_with_a_negative_Int(): Unit =
        NegativeInt.random()
            .value
            .pairBy(::NegativeInt)
            .mapFirst(NegativeInt::value)
            .assertEquals()

    @Test
    fun NegativeInt_should_throw_an_error_with_a_strictly_positive_Int(): Unit =
        StrictlyPositiveInt.random()
            .runCatching { NegativeInt(value) }
            .exceptionOrNull()
            .assertNotNull()
            .apply { message.assertNotNull() }
            .let { it is NegativeInt.ConstructionError }
            .assertTrue()

    @Test
    fun NegativeIntOrNull_should_pass_with_a_negative_Int(): Unit =
        NegativeInt.random()
            .value
            .pairBy(::NegativeIntOrNull)
            .assertFirstIsNotNull()
            .mapFirst(NegativeInt::value)
            .assertEquals()

    @Test
    fun NegativeIntOrNull_should_return_null_with_a_strictly_positive_Int(): Unit =
        StrictlyPositiveInt.random()
            .value
            .pairBy(::NegativeIntOrNull)
            .assertFirstIsNull()

    // ---------- Unary operations ----------

    @Test
    fun unaryMinus_should_pass(): Unit = NegativeInt.random()
        .pairBy { -it }
        .runMap({ first.value }) { -second.value }
        .assertEquals()

    // ---------- Binary operations ----------

    @Test
    fun div_should_pass_with_a_StrictlyPositiveInt(): Unit =
        (NegativeInt.random() to StrictlyPositiveInt.random())
            .runMap({ first / second }) { first.value / second.value }
            .mapFirst(NegativeInt::value)
            .assertEquals()

    @Test
    fun div_should_pass_with_a_StrictlyNegativeInt() {
        (NegativeInt.random() to StrictlyNegativeInt.random())
            .runMap({ first / second }) { first.value / second.value }
            .mapFirst(PositiveInt::value)
            .assertEquals()
    }

    // ---------- Conversions ----------

    @Test
    fun toString_should_pass(): Unit = NegativeInt.random()
        .pairBy(NegativeInt::toString)
        .runMapSecond { value.toString() }
        .assertEquals()
}

@Suppress("unused")
class NegativeIntSerializerTest : IntHolderSerializerTest<NegativeInt>(
    NegativeIntSerializer,
    NegativeInt.Companion::random
)
