package kotools.types

import kotools.assert.assertEquals
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotools.assert.assertTrue
import kotlin.test.Test

@Suppress("TestFunctionName")
class PositiveIntTest {
    @Test
    fun PositiveInt_should_pass_with_a_positive_Int(): Unit =
        randomPositiveInt()
            .run { PositiveInt(value) to value }
            .run { first.value assertEquals second }

    @Test
    fun PositiveInt_should_throw_an_error_with_a_strictly_negative_Int(): Unit =
        randomStrictlyNegativeInt()
            .runCatching { PositiveInt(value) }
            .exceptionOrNull()
            .assertNotNull()
            .apply { message.assertNotNull() }
            .let { it is PositiveInt.ConstructionError }
            .assertTrue()

    @Test
    fun PositiveIntOrNull_should_pass_with_a_positive_Int(): Unit =
        randomPositiveInt()
            .run { PositiveIntOrNull(value) to value }
            .run { first.assertNotNull() to second }
            .run { first.value assertEquals second }

    @Test
    fun PositiveIntOrNull_should_return_null_with_a_strictly_negative_Int(): Unit =
        randomStrictlyNegativeInt()
            .run { PositiveIntOrNull(value) }
            .assertNull()

    @Test
    fun unaryMinus_should_pass(): Unit = randomPositiveInt()
        .let { -it to it }
        .run { first.value assertEquals -second.value }

    @Test
    fun div_should_pass_with_a_StrictlyPositiveInt(): Unit =
        (randomPositiveInt() to randomStrictlyPositiveInt())
            .run { first / second to first.value / second.value }
            .run { first.value assertEquals second }

    @Test
    fun div_should_pass_with_a_StrictlyNegativeInt(): Unit =
        (randomPositiveInt() to randomStrictlyNegativeInt())
            .run { first / second to first.value / second.value }
            .run { first.value assertEquals second }
}

@Suppress("unused")
class PositiveIntSerializerTest : IntHolderSerializerTest<PositiveInt>(
    PositiveInt.Serializer,
    ::randomPositiveInt
)
