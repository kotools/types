package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.experimental.ExperimentalRangeApi
import kotools.types.range.NotEmptyRange
import kotools.types.shouldEqual
import kotools.types.shouldHaveAMessage
import kotools.types.shouldNotEqual
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

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

    @ExperimentalRangeApi
    @Test
    fun negativeRange_should_be_the_range_of_StrictlyNegativeInt() {
        val range: NotEmptyRange<StrictlyNegativeInt> = NonZeroInt.negativeRange
        range shouldEqual StrictlyNegativeInt.range
    }

    @ExperimentalRangeApi
    @Test
    fun positiveRange_should_be_the_range_of_StrictlyPositiveInt() {
        val range: NotEmptyRange<StrictlyPositiveInt> = NonZeroInt.positiveRange
        range shouldEqual StrictlyPositiveInt.range
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

    @Test
    fun toNonZeroInt_should_fail_with_an_Int_that_equals_zero() {
        val number: Number = 0
        val result: Result<NonZeroInt> = number.toNonZeroInt()
        val exception: IllegalArgumentException = assertFailsWith {
            result.getOrThrow()
        }
        val actualMessage: String = assertNotNull(exception.message)
        val expectedMessage: String? = number.shouldBe(otherThanZero).message
        assertEquals(expectedMessage, actualMessage)
    }

    @ExperimentalNumberApi
    @Test
    fun toNonZeroIntOrNull_should_pass_with_an_Int_other_than_zero() {
        val expected: Number = Random.nextInt(from = 1, until = Int.MAX_VALUE)
        val result: NonZeroInt? = expected.toNonZeroIntOrNull()
        val x: NonZeroInt = assertNotNull(result)
        val actual: Int = x.toInt()
        assertEquals(expected, actual)
    }

    @ExperimentalNumberApi
    @Test
    fun toNonZeroIntOrNull_should_fail_with_an_Int_that_equals_zero() {
        val number: Number = 0
        val result: NonZeroInt? = number.toNonZeroIntOrNull()
        assertNull(result)
    }

    @ExperimentalNumberApi
    @Test
    fun unaryMinus_should_pass() {
        // GIVEN
        val x: NonZeroInt = NonZeroInt.random()
        // WHEN
        val result: NonZeroInt = -x
        // THEN
        result.toInt() shouldEqual -x.toInt()
    }

    @Test
    fun int_div_should_pass_with_a_NonZeroInt() {
        val x: Int = Random.nextInt()
        val y: NonZeroInt = NonZeroInt.random()
        val result: Int = x / y
        result shouldEqual x / y.toInt()
    }

    @Test
    fun int_rem_should_return_an_Int_with_a_NonZeroInt() {
        val x: Int = Random.nextInt()
        val y: NonZeroInt = NonZeroInt.random()
        val result: Int = x % y
        result shouldEqual x % y.toInt()
    }
}

class NonZeroIntSerializerTest {
    private val serializer: KSerializer<NonZeroInt> = NonZeroIntSerializer

    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_NonZeroInt_as_serial_name(): Unit =
        serializer.descriptor
            .serialName shouldEqual "${Package.number}.NonZeroInt"

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: NonZeroInt = NonZeroInt.random()
        val result: String = Json.encodeToString(serializer, x)
        result shouldEqual Json.encodeToString(x.toInt())
    }

    @Test
    fun deserialization_should_pass_with_an_Int_other_than_zero() {
        val value: Int = NonZeroInt.random().toInt()
        val encoded: String = Json.encodeToString(value)
        val result: NonZeroInt = Json.decodeFromString(serializer, encoded)
        result.toInt() shouldEqual value
    }

    @Test
    fun deserialization_should_fail_with_an_Int_that_equals_zero() {
        val encoded: String = Json.encodeToString(ZeroInt.toInt())
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString(NonZeroIntSerializer, encoded)
        }
        exception.shouldHaveAMessage()
    }
}
