package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotools.types.internal.ErrorMessage
import kotools.types.internal.shouldBeNegative
import kotools.types.internal.simpleNameOf
import kotools.types.internal.unexpectedCreationFailure
import kotools.types.shouldEqual
import kotools.types.shouldNotEqual
import org.kotools.types.internal.InternalKotoolsTypesApi
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NegativeIntCompanionTest {
    @Test
    fun min_should_equal_the_minimum_value_of_Int() {
        val result: StrictlyNegativeInt = NegativeInt.min
        result.toInt() shouldEqual Int.MIN_VALUE
    }

    @Test
    fun max_should_equal_zero() {
        val result: ZeroInt = NegativeInt.max
        result shouldEqual ZeroInt
    }

    @Test
    fun random_should_return_different_values() {
        val result: NegativeInt = NegativeInt.random()
        result.toInt() shouldNotEqual NegativeInt.random().toInt()
    }
}

class NegativeIntTest {
    @Test
    fun toNegativeInt_should_pass_with_a_negative_Int() {
        val expected: Number = Random.nextInt(from = Int.MIN_VALUE, until = 0)
        val result: Result<NegativeInt> = expected.toNegativeInt()
        val actual: Int = result.getOrThrow()
            .toInt()
        assertEquals(expected, actual)
    }

    @OptIn(InternalKotoolsTypesApi::class)
    @Test
    fun toNegativeInt_should_fail_with_a_strictly_positive_Int() {
        val number: Number = Random.nextInt(from = 1, until = Int.MAX_VALUE)
        val result: Result<NegativeInt> = number.toNegativeInt()
        val exception: IllegalArgumentException = assertFailsWith {
            result.getOrThrow()
        }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage = number.shouldBeNegative()
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun div_should_return_a_NegativeInt_with_a_StrictlyPositiveInt() {
        val x: NegativeInt = NegativeInt.random()
        val y: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val actual: NegativeInt = x / y
        val expected: NegativeInt = (x.toInt() / y.toInt())
            .toNegativeIntOrFailure()
        assertEquals(expected, actual)
    }

    @Test
    fun div_should_return_a_PositiveInt_with_a_StrictlyNegativeInt() {
        val x: NegativeInt = NegativeInt.random()
        val y: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val actual: PositiveInt = x / y
        val expected: PositiveInt = (x.toInt() / y.toInt())
            .toPositiveIntOrFailure()
        assertEquals(expected, actual)
    }

    @Test
    fun rem_should_return_a_NegativeInt_with_a_NonZeroInt() {
        val x: NegativeInt = NegativeInt.random()
        val y: NonZeroInt = NonZeroInt.random()
        val actual: NegativeInt = x % y
        val expected: NegativeInt = (x.toInt() % y.toInt())
            .toNegativeIntOrFailure()
        assertEquals(expected, actual)
    }
}

class NegativeIntSerializerTest {
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun descriptor_serial_name_should_be_the_qualified_name_of_NegativeInt() {
        val actual: String = serializer<NegativeInt>().descriptor.serialName
        val simpleName: String = simpleNameOf<NegativeInt>()
        val expected = "kotools.types.number.$simpleName"
        assertEquals(expected, actual)
    }

    @ExperimentalSerializationApi
    @Test
    fun descriptor_kind_should_be_PrimitiveKind_INT() {
        val actual: SerialKind = serializer<NegativeInt>().descriptor.kind
        assertEquals(expected = PrimitiveKind.INT, actual)
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        val number: NegativeInt = NegativeInt.random()
        val actual: String = Json.encodeToString(number)
        val value: Int = number.toInt()
        val expected: String = Json.encodeToString(value)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_pass_with_a_negative_Int() {
        val value: Int = NegativeInt.random()
            .toInt()
        val encoded: String = Json.encodeToString(value)
        val actual: NegativeInt = Json.decodeFromString(encoded)
        val expected: NegativeInt = value.toNegativeInt()
            .getOrThrow()
        assertEquals(expected, actual)
    }

    @OptIn(InternalKotoolsTypesApi::class)
    @Test
    fun deserialization_should_fail_with_an_Int_that_is_greater_than_zero() {
        val value: Int = StrictlyPositiveInt.random()
            .toInt()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<NegativeInt>(encoded)
        }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage = value.shouldBeNegative()
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun serialization_processes_of_wrapped_NegativeInt_should_pass() {
        @Serializable
        data class Wrapper(val value: NegativeInt = NegativeInt.random())

        val wrapper = Wrapper()
        val encoded: String = Json.encodeToString(wrapper)
        val decoded: Wrapper = Json.decodeFromString(encoded)
        assertEquals(wrapper.value, decoded.value)
    }
}

internal fun Number.toNegativeIntOrFailure(): NegativeInt = toNegativeInt()
    .getOrNull()
    ?: unexpectedCreationFailure<NegativeInt>(value = this)
