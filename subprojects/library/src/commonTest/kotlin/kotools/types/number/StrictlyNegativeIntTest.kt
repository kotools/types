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
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.shouldBeStrictlyNegative
import kotools.types.internal.simpleNameOf
import kotools.types.shouldEqual
import kotools.types.shouldNotEqual
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class StrictlyNegativeIntCompanionTest {
    @Test
    fun min_should_equal_the_minimum_value_of_Int() {
        val result: StrictlyNegativeInt = StrictlyNegativeInt.min
        result.toInt() shouldEqual Int.MIN_VALUE
    }

    @Test
    fun max_should_equal_minus_one() {
        val result: StrictlyNegativeInt = StrictlyNegativeInt.max
        result.toInt() shouldEqual -1
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun create_should_pass_with_a_Number_that_is_less_than_zero() {
        val number: Number = Random.nextInt(from = Int.MIN_VALUE, until = 0)
        val result: StrictlyNegativeInt = StrictlyNegativeInt.create(number)
        val actual: Int = result.toInt()
        assertEquals(expected = number, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
    @Test
    fun create_should_fail_with_a_Number_that_equals_zero() {
        val number: Number = 0
        val exception: IllegalArgumentException = assertFailsWith {
            StrictlyNegativeInt.create(number)
        }
        val actual = ErrorMessage(exception)
        val expected: ErrorMessage = number.shouldBeStrictlyNegative()
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
    @Test
    fun create_should_fail_with_a_Number_that_is_greater_than_zero() {
        val number: Number = Random.nextInt(from = 1, until = Int.MAX_VALUE)
        val exception: IllegalArgumentException = assertFailsWith {
            StrictlyNegativeInt.create(number)
        }
        val actual = ErrorMessage(exception)
        val expected: ErrorMessage = number.shouldBeStrictlyNegative()
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull_should_pass_with_a_Number_that_is_less_than_zero() {
        val number: Number = Random.nextInt(from = Int.MIN_VALUE, until = 0)
        val actual: StrictlyNegativeInt? =
            StrictlyNegativeInt.createOrNull(number)
        assertNotNull(actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull_should_fail_with_a_Number_that_equals_zero() {
        val actual: StrictlyNegativeInt? =
            StrictlyNegativeInt.createOrNull(0)
        assertNull(actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun createOrNull_should_fail_with_a_Number_that_is_greater_than_zero() {
        val number: Number = Random.nextInt(from = 1, until = Int.MAX_VALUE)
        val actual: StrictlyNegativeInt? =
            StrictlyNegativeInt.createOrNull(number)
        assertNull(actual)
    }

    @Test
    fun random_should_return_different_values() {
        val result: StrictlyNegativeInt = StrictlyNegativeInt.random()
        result.toInt() shouldNotEqual StrictlyNegativeInt.random()
    }
}

class StrictlyNegativeIntTest {
    @Test
    fun number_toStrictlyNegativeInt_should_pass_with_a_strictly_negative_Int() {
        val value: Number = StrictlyNegativeInt.random().toInt()
        val result: Result<StrictlyNegativeInt> = value.toStrictlyNegativeInt()
        result.getOrThrow().toInt() shouldEqual value
    }

    @OptIn(InternalKotoolsTypesApi::class)
    @Test
    fun number_toStrictlyNegativeInt_should_fail_with_a_positive_Int() {
        val number: Number = PositiveInt.random()
            .toInt()
        val result: Result<StrictlyNegativeInt> = number.toStrictlyNegativeInt()
        val exception: IllegalArgumentException =
            assertFailsWith { result.getOrThrow() }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage = number.shouldBeStrictlyNegative()
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun toString_should_behave_like_an_Int() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random()
        "$x" shouldEqual "${x.toInt()}"
    }
}

class StrictlyNegativeIntSerializerTest {
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun descriptor_serial_name_should_be_the_qualified_name_of_StrictlyNegativeInt() {
        val actual: String = serializer<StrictlyNegativeInt>()
            .descriptor
            .serialName
        val simpleName: String = simpleNameOf<StrictlyNegativeInt>()
        val expected = "kotools.types.number.$simpleName"
        assertEquals(expected, actual)
    }

    @ExperimentalSerializationApi
    @Test
    fun descriptor_kind_should_be_PrimitiveKind_INT() {
        val actual: SerialKind = serializer<StrictlyNegativeInt>()
            .descriptor
            .kind
        val expected: SerialKind = PrimitiveKind.INT
        assertEquals(expected, actual)
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        val number: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val actual: String = Json.encodeToString(number)
        val numberValue: Int = number.toInt()
        val expected: String = Json.encodeToString(numberValue)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_pass_with_a_strictly_negative_Int() {
        val expected: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val value: Int = expected.toInt()
        val encoded: String = Json.encodeToString(value)
        val actual: StrictlyNegativeInt = Json.decodeFromString(encoded)
        assertEquals(expected, actual)
    }

    @OptIn(InternalKotoolsTypesApi::class)
    @Test
    fun deserialization_should_fail_with_a_positive_Int() {
        val value: Int = PositiveInt.random()
            .toInt()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<StrictlyNegativeInt>(encoded)
        }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage = value.shouldBeStrictlyNegative()
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun serialization_processes_of_wrapped_StrictlyNegativeInt_should_pass() {
        @Serializable
        data class Wrapper(
            val value: StrictlyNegativeInt = StrictlyNegativeInt.random()
        )

        val wrapper = Wrapper()
        val encoded: String = Json.encodeToString(wrapper)
        val decoded: Wrapper = Json.decodeFromString(encoded)
        assertEquals(wrapper.value, decoded.value)
    }
}
