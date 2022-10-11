package kotools.types

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*
import kotools.types.core.RandomValueHolder
import kotlin.test.Ignore
import kotlin.test.Test

private fun randomNonZeroInt(): NonZeroInt =
    setOf(Int.MIN_VALUE..-1, 1..Int.MAX_VALUE).random() random ::NonZeroInt

private inline infix fun <T : IntHolder> IntRange.random(
    builder: (Int) -> T
): T = random().let(builder)

private fun randomPositiveInt(): PositiveInt =
    0..Int.MAX_VALUE random ::PositiveInt

private fun randomStrictlyPositiveInt(): StrictlyPositiveInt =
    1..Int.MAX_VALUE random ::StrictlyPositiveInt

private fun randomNegativeInt(): NegativeInt =
    Int.MIN_VALUE..0 random ::NegativeInt

fun randomStrictlyNegativeInt(): StrictlyNegativeInt =
    Int.MIN_VALUE..-1 random ::StrictlyNegativeInt

class IntHolderTest : RandomValueHolder {
    private val randomHolders: Set<IntHolder> = setOf(
        randomNonZeroInt(),
        randomPositiveInt(),
        randomStrictlyPositiveInt(),
        randomNegativeInt(),
        randomStrictlyNegativeInt()
    )

    @Test
    fun compareTo_should_pass_when_comparing_an_Int_with_an_IntHolder() {
        val x: Int = randomInt
        val y: IntHolder = randomHolders.random()
        val result: Int = x.compareTo(y)
        result assertEquals x.compareTo(y.value)
    }

    @Test
    fun compareTo_should_pass_with_an_Int() {
        val x: IntHolder = randomHolders.random()
        val y: Int = randomInt
        val result: Int = x.compareTo(y)
        result assertEquals x.value.compareTo(y)
    }

    @Test
    fun compareTo_should_pass_with_an_IntHolder() {
        val x: IntHolder = randomHolders.random()
        val y: IntHolder = randomHolders.random()
        val result: Int = x.compareTo(y)
        result assertEquals x.value.compareTo(y.value)
    }

    @Test
    fun plus_should_pass_when_adding_an_IntHolder_to_an_Int() {
        val x: Int = randomInt
        val y: IntHolder = randomHolders.random()
        val result: Int = x + y
        result assertEquals x + y.value
    }

    @Test
    fun plus_should_pass_with_an_Int() {
        val x: IntHolder = randomHolders.random()
        val y: Int = randomInt
        val result: Int = x + y
        result assertEquals x.value + y
    }

    @Test
    fun plus_should_pass_with_an_IntHolder() {
        val x: IntHolder = randomHolders.random()
        val y: IntHolder = randomHolders.random()
        val result: Int = x + y
        result assertEquals x.value + y.value
    }

    @Test
    fun minus_should_pass_when_subtracting_an_IntHolder_from_an_Int() {
        val x: Int = randomInt
        val y: IntHolder = randomHolders.random()
        val result: Int = x - y
        result assertEquals x - y.value
    }

    @Test
    fun minus_should_pass_with_an_Int() {
        val x: IntHolder = randomHolders.random()
        val y: Int = randomInt
        val result: Int = x - y
        result assertEquals x.value - y
    }

    @Test
    fun minus_should_pass_with_an_IntHolder() {
        val x: IntHolder = randomHolders.random()
        val y: IntHolder = randomHolders.random()
        val result: Int = x - y
        result assertEquals x.value - y.value
    }

    @Test
    fun times_should_pass_when_multiplying_an_Int_by_an_IntHolder() {
        val x: Int = randomInt
        val y: IntHolder = randomHolders.random()
        val result: Int = x * y
        result assertEquals x * y.value
    }

    @Test
    fun times_should_pass_with_an_Int() {
        val x: IntHolder = randomHolders.random()
        val y: Int = randomInt
        val result: Int = x * y
        result assertEquals x.value * y
    }

    @Test
    fun times_should_pass_with_an_IntHolder() {
        val x: IntHolder = randomHolders.random()
        val y: IntHolder = randomHolders.random()
        val result: Int = x * y
        result assertEquals x.value * y.value
    }

    @Test
    fun div_should_pass_with_a_NonZeroInt() {
        val x: IntHolder = randomHolders.random()
        val y: NonZeroInt = randomNonZeroInt()
        val result: Int = x / y
        result assertEquals x.value / y.value
    }
}

@Ignore // Because we test the implementations of the class IntHolderSerializer.
sealed class IntHolderSerializerTest<T : IntHolder>(
    private val serializer: IntHolder.Serializer<T>,
    private val generator: () -> T
) {
    @Test
    fun serialization_should_pass(): Unit = generator().let {
        val result: String = Json.encodeToString(serializer, it)
        result assertEquals Json.encodeToString(it.value)
    }

    @Test
    fun deserialization_should_pass(): Unit = generator().let {
        val encoded: String = Json.encodeToString(serializer, it)
        val result: T = Json.decodeFromString(serializer, encoded)
        result.value assertEquals it.value
    }
}

@Suppress("TestFunctionName")
class NonZeroIntTest : RandomValueHolder {
    @Test
    fun NonZeroInt_should_pass_with_a_non_zero_Int(): Unit =
        randomNonZeroInt().value.let { NonZeroInt(it).value assertEquals it }

    @Test
    fun NonZeroInt_should_throw_an_error_with_an_Int_that_equals_zero() {
        assertFailsWith<NonZeroInt.ConstructionError> { NonZeroInt(0) }
            .message.assertNotNull()
    }

    @Test
    fun NonZeroIntOrNull_should_pass_with_a_non_zero_Int() {
        val value: Int = randomNonZeroInt().value
        val result: NonZeroInt? = NonZeroIntOrNull(value)
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun NonZeroIntOrNull_should_return_null_with_an_Int_that_equals_zero() {
        val result: NonZeroInt? = NonZeroIntOrNull(0)
        result.assertNull()
    }

    @Test
    fun unaryMinus_should_pass() {
        val x: NonZeroInt = randomNonZeroInt()
        val result: NonZeroInt = -x
        result.value assertEquals -x.value
    }

    @Test
    fun times_should_pass_with_a_NonZeroInt() {
        val x: NonZeroInt = randomNonZeroInt()
        val y: NonZeroInt = randomNonZeroInt()
        val result: NonZeroInt = x * y
        result.value assertEquals x.value * y.value
    }

    @Test
    fun div_should_pass_when_dividing_an_Int_by_a_NonZeroInt() {
        val x: Int = randomInt
        val y: NonZeroInt = randomNonZeroInt()
        val result: Int = x / y
        result assertEquals x / y.value
    }
}

@Suppress("unused")
class NonZeroIntSerializerTest : IntHolderSerializerTest<NonZeroInt>(
    NonZeroInt.Serializer,
    ::randomNonZeroInt
)

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

@Suppress("TestFunctionName")
class StrictlyPositiveIntTest {
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

    @Test
    fun unaryMinus_should_pass(): Unit = randomStrictlyPositiveInt()
        .let { -it to it }
        .run { first.value assertEquals -second.value }
}

@Suppress("unused")
class StrictlyPositiveIntSerializerTest :
    IntHolderSerializerTest<StrictlyPositiveInt>(
        StrictlyPositiveInt.Serializer,
        ::randomStrictlyPositiveInt
    )

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

@Suppress("TestFunctionName")
class StrictlyNegativeIntTest {
    @Test
    fun StrictlyNegativeInt_should_pass_with_a_strictly_negative_Int() {
        val value: Int = randomStrictlyNegativeInt().value
        val result = StrictlyNegativeInt(value)
        result.value assertEquals value
    }

    @Test
    fun StrictlyNegativeInt_should_throw_an_error_with_a_positive_Int() {
        val value: Int = randomPositiveInt().value
        assertFailsWith<IllegalArgumentException> { StrictlyNegativeInt(value) }
    }

    @Test
    fun StrictlyNegativeIntOrNull_should_pass_with_a_strictly_negative_Int() {
        val value: Int = randomStrictlyNegativeInt().value
        val result: StrictlyNegativeInt? = StrictlyNegativeIntOrNull(value)
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun StrictlyNegativeIntOrNull_should_return_null_with_a_positive_Int() {
        val value: Int = randomPositiveInt().value
        val result: StrictlyNegativeInt? = StrictlyNegativeIntOrNull(value)
        result.assertNull()
    }

    @Test
    fun unaryMinus_should_pass() {
        val x: StrictlyNegativeInt = randomStrictlyNegativeInt()
        val result: StrictlyPositiveInt = -x
        result.value assertEquals -x.value
    }
}

@Suppress("unused")
class StrictlyNegativeIntSerializerTest :
    IntHolderSerializerTest<StrictlyNegativeInt>(
        StrictlyNegativeInt.Serializer,
        ::randomStrictlyNegativeInt
    )
