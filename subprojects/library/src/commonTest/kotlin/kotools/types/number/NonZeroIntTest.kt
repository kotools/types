package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotools.types.internal.ErrorMessage
import kotools.types.internal.simpleNameOf
import kotools.types.shouldEqual
import kotools.types.shouldNotEqual
import org.kotools.types.internal.InternalKotoolsTypesApi
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NonZeroIntCompanionTest {
    @Test
    fun min_should_equal_the_minimum_value_of_Int() {
        val result: StrictlyNegativeInt = NonZeroInt.min
        result.toInt() shouldEqual Int.MIN_VALUE
    }

    @Test
    fun max_should_equal_the_maximum_value_of_Int() {
        val result: StrictlyPositiveInt = NonZeroInt.max
        result.toInt() shouldEqual Int.MAX_VALUE
    }

    @Test
    fun random_should_return_different_values() {
        val x: NonZeroInt = NonZeroInt.random()
        x.toInt() shouldNotEqual NonZeroInt.random().toInt()
    }
}

class NonZeroIntTest {
    @Test
    fun toNonZeroInt_should_pass_with_an_Int_other_than_zero() {
        val expected: Number = Random.nextInt(from = 1, until = Int.MAX_VALUE)
        val result: Result<NonZeroInt> = expected.toNonZeroInt()
        val actual: Int = result.getOrThrow()
            .toInt()
        assertEquals(expected, actual)
    }

    @OptIn(InternalKotoolsTypesApi::class)
    @Test
    fun toNonZeroInt_should_fail_with_an_Int_that_equals_zero() {
        val number: Number = 0
        val result: Result<NonZeroInt> = number.toNonZeroInt()
        val exception: IllegalArgumentException = assertFailsWith {
            result.getOrThrow()
        }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage = ErrorMessage.zeroNumber
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun int_div_should_pass_with_a_NonZeroInt() {
        val x: Int = Random.nextInt()
        val y: NonZeroInt = NonZeroInt.random()
        val actual: Int = x / y
        val expected: Int = x / y.toInt()
        assertEquals(expected, actual)
    }

    @Test
    fun int_rem_should_return_an_Int_with_a_NonZeroInt() {
        val x: Int = Random.nextInt()
        val y: NonZeroInt = NonZeroInt.random()
        val actual: Int = x % y
        val expected: Int = x % y.toInt()
        assertEquals(expected, actual)
    }
}

class NonZeroIntSerializerTest {
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun descriptor_serial_name_should_be_the_qualified_name_of_NonZeroInt() {
        val actual: String = serializer<NonZeroInt>().descriptor.serialName
        val simpleName: String = simpleNameOf<NonZeroInt>()
        val expected = "kotools.types.number.$simpleName"
        assertEquals(expected, actual)
    }

    @ExperimentalSerializationApi
    @Test
    fun descriptor_kind_should_be_PrimitiveKind_INT() {
        val actual: SerialKind = serializer<NonZeroInt>().descriptor.kind
        val expected: SerialKind = PrimitiveKind.INT
        assertEquals(expected, actual)
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        val number: NonZeroInt = NonZeroInt.random()
        val actual: String = Json.encodeToString(number)
        val intNumber: Int = number.toInt()
        val expected: String = Json.encodeToString(intNumber)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_pass_with_an_Int_other_than_zero() {
        val value: Int = NonZeroInt.random()
            .toInt()
        val encoded: String = Json.encodeToString(value)
        val actual: NonZeroInt = Json.decodeFromString(encoded)
        val expected: NonZeroInt = value.toNonZeroInt()
            .getOrThrow()
        assertEquals(expected, actual)
    }

    @OptIn(InternalKotoolsTypesApi::class)
    @Test
    fun deserialization_should_fail_with_an_Int_that_equals_zero() {
        val encoded: String = Json.encodeToString(0)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<NonZeroInt>(encoded)
        }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage = ErrorMessage.zeroNumber
        assertEquals(expectedMessage, actualMessage)
    }
}
