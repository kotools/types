package kotools.types.number

import kotools.assert.*
import kotools.types.*
import kotlin.test.Test

@Suppress("TestFunctionName")
class StrictlyNegativeIntTest {
    // ---------- Companion ----------

    @Test
    fun min_should_be_the_minimum_of_Int(): Unit =
        StrictlyNegativeInt.min.value assertEquals Int.MIN_VALUE

    @Test
    fun max_should_be_minus1(): Unit =
        StrictlyNegativeInt.max.value assertEquals -1

    @Test
    fun random_should_return_different_values(): Unit = StrictlyNegativeInt
        .random()
        .value assertNotEquals StrictlyNegativeInt.random().value

    // ---------- Builders ----------

    @Test
    fun StrictlyNegativeInt_should_pass_with_a_strictly_negative_Int(): Unit =
        StrictlyNegativeInt.random()
            .value
            .pairBy(::StrictlyNegativeInt)
            .mapFirst(StrictlyNegativeInt::value)
            .assertEquals()

    @Test
    fun StrictlyNegativeInt_should_throw_an_error_with_a_positive_Int(): Unit =
        PositiveInt.random()
            .runCatching { StrictlyNegativeInt(value) }
            .exceptionOrNull()
            .assertNotNull()
            .apply { message.assertNotNull() }
            .let { it is StrictlyNegativeInt.ConstructionError }
            .assertTrue()

    @Test
    fun StrictlyNegativeIntOrNull_should_pass_with_a_strictly_negative_Int(): Unit =
        StrictlyNegativeInt.random()
            .value
            .pairBy(::StrictlyNegativeIntOrNull)
            .assertFirstIsNotNull()
            .mapFirst(StrictlyNegativeInt::value)
            .assertEquals()

    @Test
    fun StrictlyNegativeIntOrNull_should_return_null_with_a_positive_Int(): Unit =
        PositiveInt.random()
            .value
            .let(::StrictlyNegativeIntOrNull)
            .assertNull()

    // ---------- Unary operations ----------

    @Test
    fun unaryMinus_should_pass(): Unit = StrictlyNegativeInt.random()
        .pairBy { -it }
        .runMap({ first.value }) { -second.value }
        .assertEquals()

    // ---------- Conversions ----------

    @Test
    fun toString_should_pass(): Unit = StrictlyNegativeInt.random()
        .pairBy(StrictlyNegativeInt::toString)
        .runMapSecond { value.toString() }
        .assertEquals()
}

@Suppress("unused")
class StrictlyNegativeIntSerializerTest :
    IntHolderSerializerTest<StrictlyNegativeInt>(
        StrictlyNegativeIntSerializer,
        StrictlyNegativeInt.Companion::random
    )
