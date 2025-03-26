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
import kotools.types.internal.shouldBeStrictlyPositive
import kotools.types.internal.simpleNameOf
import kotools.types.internal.unexpectedCreationFailure
import kotools.types.shouldEqual
import kotools.types.shouldNotEqual
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.InternalKotoolsTypesApi
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class StrictlyPositiveIntCompanionTest {
    @Test
    fun min_should_equal_one() {
        val actual: StrictlyPositiveInt = StrictlyPositiveInt.min
        val expected: StrictlyPositiveInt = 1.toStrictlyPositiveIntOrFailure()
        assertEquals(expected, actual)
    }

    @Test
    fun max_should_equal_the_maximum_value_of_Int() {
        val actual: StrictlyPositiveInt = StrictlyPositiveInt.max
        val expected: StrictlyPositiveInt = Int.MAX_VALUE
            .toStrictlyPositiveIntOrFailure()
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull_should_pass_with_a_Number_that_is_greater_than_zero() {
        val number: Number = Random.nextInt(1..Int.MAX_VALUE)
        val actual: StrictlyPositiveInt? =
            StrictlyPositiveInt.createOrNull(number)
        assertNotNull(actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull_should_fail_with_a_Number_that_equals_zero() {
        val actual: StrictlyPositiveInt? =
            StrictlyPositiveInt.createOrNull(0)
        assertNull(actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull_should_fail_with_a_Number_that_is_less_than_zero() {
        val number: Number = Random.nextInt(from = Int.MIN_VALUE, until = 0)
        val actual: StrictlyPositiveInt? =
            StrictlyPositiveInt.createOrNull(number)
        assertNull(actual)
    }

    @Test
    fun random_should_return_different_values() {
        val result: StrictlyPositiveInt = StrictlyPositiveInt.random()
        result.toInt() shouldNotEqual StrictlyPositiveInt.random().toInt()
    }
}

class StrictlyPositiveIntTest {
    @Test
    fun toStrictlyPositiveInt_should_pass_with_a_strictly_positive_Number() {
        val number: Number = Random.nextInt(1..Int.MAX_VALUE)
        val result: Result<StrictlyPositiveInt> = number.toStrictlyPositiveInt()
        result.getOrThrow().toInt() shouldEqual number
    }

    @OptIn(InternalKotoolsTypesApi::class)
    @Test
    fun toStrictlyPositiveInt_should_fail_with_a_negative_Number() {
        val number: Number = Random.nextInt(Int.MIN_VALUE..0)
        val result: Result<StrictlyPositiveInt> = number.toStrictlyPositiveInt()
        val exception: IllegalArgumentException =
            assertFailsWith { result.getOrThrow() }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage = number.shouldBeStrictlyPositive()
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun toString_should_behave_like_an_Int(): Unit = StrictlyPositiveInt
        .random()
        .run { "$this" shouldEqual "${toInt()}" }
}

class StrictlyPositiveIntSerializerTest {
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun descriptor_serial_name_should_be_the_qualified_name_of_StrictlyPositiveInt() {
        val actual: String = serializer<StrictlyPositiveInt>()
            .descriptor
            .serialName
        val simpleName: String = simpleNameOf<StrictlyPositiveInt>()
        val expected = "kotools.types.number.$simpleName"
        assertEquals(expected, actual)
    }

    @ExperimentalSerializationApi
    @Test
    fun descriptor_kind_should_be_PrimitiveKind_INT() {
        val actual: SerialKind = serializer<StrictlyPositiveInt>()
            .descriptor
            .kind
        val expected: SerialKind = PrimitiveKind.INT
        assertEquals(expected, actual)
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        val number: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val actual: String = Json.encodeToString(number)
        val numberValue: Int = number.toInt()
        val expected: String = Json.encodeToString(numberValue)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_pass_with_a_strictly_positive_Int() {
        val expected: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val value: Int = expected.toInt()
        val encoded: String = Json.encodeToString(value)
        val actual: StrictlyPositiveInt = Json.decodeFromString(encoded)
        assertEquals(expected, actual)
    }

    @OptIn(InternalKotoolsTypesApi::class)
    @Test
    fun deserialization_should_fail_with_a_negative_Int() {
        val value: Int = NegativeInt.random()
            .toInt()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<StrictlyPositiveInt>(encoded)
        }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage = value.shouldBeStrictlyPositive()
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun serialization_processes_of_wrapped_StrictlyPositiveInt_should_pass() {
        @Serializable
        data class Wrapper(
            val value: StrictlyPositiveInt = StrictlyPositiveInt.random()
        )

        val wrapper = Wrapper()
        val encoded: String = Json.encodeToString(wrapper)
        val decoded: Wrapper = Json.decodeFromString(encoded)
        assertEquals(wrapper.value, decoded.value)
    }
}

private fun Number.toStrictlyPositiveIntOrFailure(): StrictlyPositiveInt =
    toStrictlyPositiveInt()
        .getOrNull()
        ?: unexpectedCreationFailure<StrictlyPositiveInt>(value = this)
