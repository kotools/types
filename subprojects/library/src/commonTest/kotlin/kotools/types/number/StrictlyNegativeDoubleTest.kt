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
import kotools.types.internal.deserializationErrorMessage
import kotools.types.internal.simpleNameOf
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.InternalKotoolsTypesApi
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class StrictlyNegativeDoubleCompanionTest {
    @OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
    @Test
    fun create_should_pass_with_a_Number_that_is_less_than_zero() {
        val number: Number = Random.nextInt(Int.MIN_VALUE until 0)
        val result: Result<StrictlyNegativeDouble> = kotlin.runCatching {
            StrictlyNegativeDouble.create(number)
        }
        val actual: Boolean = result.isSuccess
        val typeName: String = simpleNameOf<StrictlyNegativeDouble>()
        assertTrue(actual, "Converting $number to $typeName should pass.")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
    @Test
    fun create_should_fail_with_a_Number_that_equals_zero() {
        val number: Number = 0
        val exception: IllegalArgumentException = assertFailsWith {
            StrictlyNegativeDouble.create(number)
        }
        val actual = ErrorMessage(exception)
        val expected: ErrorMessage = ErrorMessage.shouldBeLessThanZero(number)
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
    @Test
    fun create_should_fail_with_a_Number_that_is_greater_than_zero() {
        val number: Number = Random.nextInt(1..Int.MAX_VALUE)
        val exception: IllegalArgumentException = assertFailsWith {
            StrictlyNegativeDouble.create(number)
        }
        val actual = ErrorMessage(exception)
        val expected: ErrorMessage = ErrorMessage.shouldBeLessThanZero(number)
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
    @Test
    fun createOrNull_should_pass_with_a_Number_that_is_less_than_zero() {
        val number: Number = Random.nextInt(Int.MIN_VALUE until 0)
        val actual: StrictlyNegativeDouble? =
            StrictlyNegativeDouble.createOrNull(number)
        val typeName: String = simpleNameOf<StrictlyNegativeDouble>()
        assertNotNull(actual, "Converting $number to $typeName should pass")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
    @Test
    fun createOrNull_should_fail_with_a_Number_that_equals_zero() {
        val number: Number = 0
        val actual: StrictlyNegativeDouble? =
            StrictlyNegativeDouble.createOrNull(number)
        val typeName: String = simpleNameOf<StrictlyNegativeDouble>()
        assertNull(actual, "Converting $number to $typeName should fail")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
    @Test
    fun createOrNull_should_fail_with_a_Number_that_is_greater_than_zero() {
        val number: Number = Random.nextInt(1..Int.MAX_VALUE)
        val actual: StrictlyNegativeDouble? =
            StrictlyNegativeDouble.createOrNull(number)
        val typeName: String = simpleNameOf<StrictlyNegativeDouble>()
        assertNull(actual, "Converting $number to $typeName should fail")
    }
}

class StrictlyNegativeDoubleTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun structural_equality_should_pass_with_the_same_object() {
        val value: Number = Random.nextInt(Int.MIN_VALUE until 0)
        val first: StrictlyNegativeDouble = StrictlyNegativeDouble.create(value)
        val second: StrictlyNegativeDouble = first
        assertTrue { first.equals(second) }
        val firstHashCode: Int = first.hashCode()
        val secondHashCode: Int = second.hashCode()
        assertEquals(firstHashCode, secondHashCode)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun structural_equality_should_pass_with_another_StrictlyNegativeDouble_having_the_same_value() {
        val value: Number = Random.nextInt(Int.MIN_VALUE until 0)
        val first: StrictlyNegativeDouble = StrictlyNegativeDouble.create(value)
        val second: StrictlyNegativeDouble =
            StrictlyNegativeDouble.create(value)
        assertTrue { first.equals(second) }
        val firstHashCode: Int = first.hashCode()
        val secondHashCode: Int = second.hashCode()
        assertEquals(firstHashCode, secondHashCode)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun structural_equality_should_fail_with_null() {
        val value: Number = Random.nextInt(Int.MIN_VALUE until 0)
        val first: StrictlyNegativeDouble = StrictlyNegativeDouble.create(value)
        val second: StrictlyNegativeDouble? = null
        assertFalse { first.equals(second) }
        val firstHashCode: Int = first.hashCode()
        val secondHashCode: Int = second.hashCode()
        assertNotEquals(illegal = secondHashCode, actual = firstHashCode)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun structural_equality_should_fail_with_another_object_that_is_not_a_StrictlyNegativeDouble() {
        val value: Number = Random.nextInt(Int.MIN_VALUE until 0)
        val first: StrictlyNegativeDouble = StrictlyNegativeDouble.create(value)
        assertFalse { first.equals(value) }
        val firstHashCode: Int = first.hashCode()
        val secondHashCode: Int = value.hashCode()
        assertNotEquals(illegal = secondHashCode, actual = firstHashCode)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun structural_equality_should_fail_with_another_StrictlyNegativeDouble_having_another_value() {
        val firstValue: Number = Random.nextInt(Int.MIN_VALUE until 0)
        val first: StrictlyNegativeDouble =
            StrictlyNegativeDouble.create(firstValue)
        val secondValue: Number = Random.nextInt(Int.MIN_VALUE until 0)
        val second: StrictlyNegativeDouble =
            StrictlyNegativeDouble.create(secondValue)
        assertFalse { first.equals(second) }
        val firstHashCode: Int = first.hashCode()
        val secondHashCode: Int = second.hashCode()
        assertNotEquals(illegal = secondHashCode, actual = firstHashCode)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun toDouble_should_pass() {
        val value: Number = Random.nextInt(Int.MIN_VALUE until 0)
        val number: StrictlyNegativeDouble =
            StrictlyNegativeDouble.create(value)
        val actual: Double = number.toDouble()
        val expected: Double = value.toDouble()
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun toString_should_pass() {
        val value: Number = Random.nextInt(Int.MIN_VALUE until 0)
        val number: StrictlyNegativeDouble =
            StrictlyNegativeDouble.create(value)
        val actual: String = number.toString()
        val expected: String = value.toDouble()
            .toString()
        assertEquals(expected, actual)
    }
}

class StrictlyNegativeDoubleSerializerTest {
    @OptIn(
        ExperimentalKotoolsTypesApi::class,
        ExperimentalSerializationApi::class
    )
    @Test
    fun descriptor_serialName_should_be_the_qualified_name_of_StrictlyNegativeDouble() {
        val actual: String = serializer<StrictlyNegativeDouble>()
            .descriptor
            .serialName
        val type: String = simpleNameOf<StrictlyNegativeDouble>()
        val expected = "kotools.types.number.$type"
        assertEquals(expected, actual)
    }

    @ExperimentalSerializationApi
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun descriptor_kind_should_be_PrimitiveKind_DOUBLE() {
        val actual: SerialKind = serializer<StrictlyNegativeDouble>()
            .descriptor
            .kind
        val expected: SerialKind = PrimitiveKind.DOUBLE
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun serialization_should_behave_like_a_Double() {
        val value: Double = Random.nextInt(Int.MIN_VALUE until 0)
            .toDouble()
        val number: StrictlyNegativeDouble =
            StrictlyNegativeDouble.create(value)
        val actual: String = Json.encodeToString(number)
        val expected: String = Json.encodeToString(value)
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun deserialization_should_pass_from_a_Double_that_is_less_than_zero() {
        val value: Double = Random.nextInt(Int.MIN_VALUE until 0)
            .toDouble()
        val encoded: String = Json.encodeToString(value)
        val number: StrictlyNegativeDouble = Json.decodeFromString(encoded)
        val actual: Double = number.toDouble()
        assertEquals(expected = value, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
    @Test
    fun deserialization_should_fail_from_a_Double_that_equals_zero() {
        val value = 0.0
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<StrictlyNegativeDouble>(encoded)
        }
        val actual = ErrorMessage(exception)
        val expected: ErrorMessage =
            deserializationErrorMessage<StrictlyNegativeDouble>(value)
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class, InternalKotoolsTypesApi::class)
    @Test
    fun deserialization_should_fail_from_a_Double_that_is_greater_than_zero() {
        val value: Double = Random.nextInt(1..Int.MAX_VALUE)
            .toDouble()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<StrictlyNegativeDouble>(encoded)
        }
        val actual = ErrorMessage(exception)
        val expected: ErrorMessage =
            deserializationErrorMessage<StrictlyNegativeDouble>(value)
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun processes_should_pass_with_a_wrapped_StrictlyNegativeDouble() {
        @Serializable
        data class Wrapper(val number: StrictlyNegativeDouble)

        val value: Number = Random.nextInt(Int.MIN_VALUE until 0)
        val number: StrictlyNegativeDouble =
            StrictlyNegativeDouble.create(value)
        val wrapper = Wrapper(number)
        val encoded: String = Json.encodeToString(wrapper)
        val decoded: Wrapper = Json.decodeFromString(encoded)
        assertEquals(expected = number, actual = decoded.number)
    }
}
