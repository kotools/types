package kotools.types.number

import kotools.assert.*
import kotools.types.assertEquals
import kotools.types.pairBy
import kotools.types.runMapSecond
import kotlin.test.Test

@Suppress("TestFunctionName")
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

    // ---------- Builders ----------

    @Test
    fun StrictlyPositiveInt_should_pass_with_a_strictly_positive_Int(): Unit =
        randomStrictlyPositiveInt()
            .run { StrictlyPositiveInt(value) to value }
            .run { first.value assertEquals second }

    @Test
    fun StrictlyPositiveInt_should_throw_an_error_with_a_negative_Int(): Unit =
        randomNegativeInt()
            .runCatching { StrictlyPositiveInt(value) }
            .exceptionOrNull()
            .assertNotNull()
            .apply { message.assertNotNull() }
            .let { it is StrictlyPositiveInt.ConstructionError }
            .assertTrue()

    @Test
    fun StrictlyPositiveIntOrNull_should_pass_with_a_strictly_positive_Int(): Unit =
        randomStrictlyPositiveInt()
            .run { StrictlyPositiveIntOrNull(value) to value }
            .run { first.assertNotNull() to second }
            .run { first.value assertEquals second }

    @Test
    fun StrictlyPositiveIntOrNull_should_return_null_with_a_negative_Int(): Unit =
        randomNegativeInt()
            .run { StrictlyPositiveIntOrNull(value) }
            .assertNull()

    // ---------- Unary operations ----------

    @Test
    fun unaryMinus_should_pass(): Unit = randomStrictlyPositiveInt()
        .let { -it to it }
        .run { first.value assertEquals -second.value }

    // ---------- Conversions ----------

    @Test
    fun toString_should_pass(): Unit = randomStrictlyPositiveInt()
        .pairBy(StrictlyPositiveInt::toString)
        .runMapSecond { value.toString() }
        .assertEquals()
}

@Suppress("unused")
class StrictlyPositiveIntSerializerTest :
    IntHolderSerializerTest<StrictlyPositiveInt>(
        StrictlyPositiveIntSerializer,
        ::randomStrictlyPositiveInt
    )
