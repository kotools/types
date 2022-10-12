package kotools.types

import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

@Suppress("TestFunctionName")
class NegativeIntTest {
    @Test
    fun NegativeInt_should_pass_with_a_negative_Int(): Unit =
        randomNegativeInt()
            .value
            .pairBy(::NegativeInt)
            .mapFirst(NegativeInt::value)
            .assertEquals()

    @Test
    fun NegativeInt_should_throw_an_error_with_a_strictly_positive_Int(): Unit =
        randomStrictlyPositiveInt()
            .runCatching { NegativeInt(value) }
            .exceptionOrNull()
            .assertNotNull()
            .apply { message.assertNotNull() }
            .let { it is NegativeInt.ConstructionError }
            .assertTrue()

    @Test
    fun NegativeIntOrNull_should_pass_with_a_negative_Int(): Unit =
        randomNegativeInt()
            .value
            .pairBy(::NegativeIntOrNull)
            .assertFirstIsNotNull()
            .mapFirst(NegativeInt::value)
            .assertEquals()

    @Test
    fun NegativeIntOrNull_should_return_null_with_a_strictly_positive_Int(): Unit =
        randomStrictlyPositiveInt()
            .value
            .pairBy(::NegativeIntOrNull)
            .assertFirstIsNull()

    @Test
    fun unaryMinus_should_pass(): Unit = randomNegativeInt()
        .pairBy { -it }
        .runMap({ first.value }) { -second.value }
        .assertEquals()

    @Test
    fun div_should_pass_with_a_StrictlyPositiveInt(): Unit =
        (randomNegativeInt() to randomStrictlyPositiveInt())
            .runMap({ first / second }) { first.value / second.value }
            .mapFirst(NegativeInt::value)
            .assertEquals()

    @Test
    fun div_should_pass_with_a_StrictlyNegativeInt() {
        (randomNegativeInt() to randomStrictlyNegativeInt())
            .runMap({ first / second }) { first.value / second.value }
            .mapFirst(PositiveInt::value)
            .assertEquals()
    }
}

@Suppress("unused")
class NegativeIntSerializerTest : IntHolderSerializerTest<NegativeInt>(
    NegativeInt.Serializer,
    ::randomNegativeInt
)
