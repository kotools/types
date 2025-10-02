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
import kotools.types.internal.shouldBePositive
import kotools.types.internal.simpleNameOf
import kotools.types.internal.unexpectedCreationFailure
import kotools.types.shouldEqual
import kotools.types.shouldNotEqual
import org.kotools.types.internal.InternalKotoolsTypesApi
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PositiveIntCompanionTest {
    @Test
    fun min_should_equal_zero() {
        val result: ZeroInt = PositiveInt.min
        result shouldEqual ZeroInt
    }

    @Test
    fun max_should_equal_the_maximum_value_of_Int() {
        val result: StrictlyPositiveInt = PositiveInt.max
        result.toInt() shouldEqual Int.MAX_VALUE
    }

    @Test
    fun random_should_return_different_values() {
        val result: PositiveInt = PositiveInt.random()
        result.toInt() shouldNotEqual PositiveInt.random().toInt()
    }
}

class PositiveIntTest {
    @Test
    fun toPositiveInt_should_pass_with_a_positive_Int() {
        val expected: Number = Random.nextInt(from = 0, until = Int.MAX_VALUE)
        val result: Result<PositiveInt> = expected.toPositiveInt()
        val actual: Int = result.getOrThrow()
            .toInt()
        assertEquals(expected, actual)
    }

    @OptIn(InternalKotoolsTypesApi::class)
    @Test
    fun toPositiveInt_should_fail_with_a_strictly_negative_Int() {
        val number: Number = Random.nextInt(from = Int.MIN_VALUE, until = 0)
        val result: Result<PositiveInt> = number.toPositiveInt()
        val exception: IllegalArgumentException = assertFailsWith {
            result.getOrThrow()
        }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage = number.shouldBePositive()
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun div_should_return_a_PositiveInt_with_a_StrictlyPositiveInt() {
        val x: PositiveInt = PositiveInt.random()
        val y: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val actual: PositiveInt = x / y
        val expected: PositiveInt = (x.toInt() / y.toInt())
            .toPositiveIntOrFailure()
        assertEquals(expected, actual)
    }

    @Test
    fun div_should_return_a_NegativeInt_with_a_StrictlyNegativeInt() {
        val x: PositiveInt = PositiveInt.random()
        val y: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val actual: NegativeInt = x / y
        val expected: NegativeInt = (x.toInt() / y.toInt())
            .toNegativeIntOrFailure()
        assertEquals(expected, actual)
    }

    @Test
    fun rem_should_return_a_PositiveInt_with_a_NonZeroInt() {
        val x: PositiveInt = PositiveInt.random()
        val y: NonZeroInt = NonZeroInt.random()
        val actual: PositiveInt = x % y
        val expected: PositiveInt = (x.toInt() % y.toInt())
            .toPositiveIntOrFailure()
        assertEquals(expected, actual)
    }
}

class PositiveIntSerializerTest {
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun descriptor_serial_name_should_be_the_qualified_name_of_PositiveInt() {
        val actual: String = serializer<PositiveInt>().descriptor.serialName
        val simpleName: String = simpleNameOf<PositiveInt>()
        val expected = "kotools.types.number.$simpleName"
        assertEquals(expected, actual)
    }

    @ExperimentalSerializationApi
    @Test
    fun descriptor_kind_should_be_PrimitiveKind_INT() {
        val actual: SerialKind = serializer<PositiveInt>().descriptor.kind
        val expected: SerialKind = PrimitiveKind.INT
        assertEquals(expected, actual)
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        val number: PositiveInt = PositiveInt.random()
        val actual: String = Json.encodeToString(number)
        val intNumber: Int = number.toInt()
        val expected: String = Json.encodeToString(intNumber)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_pass_with_a_positive_Int() {
        val expected: PositiveInt = PositiveInt.random()
        val value: Int = expected.toInt()
        val encoded: String = Json.encodeToString(value)
        val actual: PositiveInt = Json.decodeFromString(encoded)
        assertEquals(expected, actual)
    }

    @OptIn(InternalKotoolsTypesApi::class)
    @Test
    fun deserialization_should_fail_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.random()
            .toInt()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<PositiveInt>(encoded)
        }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage = value.shouldBePositive()
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun serialization_processes_of_wrapped_PositiveInt_should_pass() {
        @Serializable
        data class Wrapper(val value: PositiveInt = PositiveInt.random())

        val wrapper = Wrapper()
        val encoded: String = Json.encodeToString(wrapper)
        val decoded: Wrapper = Json.decodeFromString(encoded)
        assertEquals(wrapper.value, decoded.value)
    }
}

internal fun Number.toPositiveIntOrFailure(): PositiveInt = toPositiveInt()
    .getOrNull()
    ?: unexpectedCreationFailure<PositiveInt>(value = this)
