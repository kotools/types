package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotools.assert.assertTrue
import kotools.types.assertEquals
import kotools.types.pairBy
import kotools.types.runMapSecond
import kotlin.test.Test

@Suppress("TestFunctionName")
class StrictlyPositiveIntTest {
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
