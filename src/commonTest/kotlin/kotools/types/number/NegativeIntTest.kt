package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.experimental.ExperimentalRangeApi
import kotools.types.range.InclusiveBound
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
import kotlin.test.assertTrue

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

    @ExperimentalRangeApi
    @Test
    fun range_should_start_with_an_InclusiveBound_that_equals_the_minimum_value_of_Int() {
        val range: NotEmptyRange<NegativeInt> = NegativeInt.range
        assertTrue { range.start is InclusiveBound }
        range.start.value.toInt() shouldEqual Int.MIN_VALUE
    }

    @ExperimentalRangeApi
    @Test
    fun range_should_end_with_an_InclusiveBound_that_equals_zero() {
        val range: NotEmptyRange<NegativeInt> = NegativeInt.range
        assertTrue { range.end is InclusiveBound }
        range.end.value shouldEqual ZeroInt
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

    @Test
    fun toNegativeInt_should_fail_with_a_strictly_positive_Int() {
        val number: Number = Random.nextInt(from = 1, until = Int.MAX_VALUE)
        val result: Result<NegativeInt> = number.toNegativeInt()
        val exception: IllegalArgumentException = assertFailsWith {
            result.getOrThrow()
        }
        val actualMessage: String = assertNotNull(exception.message)
        val expectedMessage: String = NegativeIntConstructionException(number)
            .message
        assertEquals(expectedMessage, actualMessage)
    }

    @ExperimentalNumberApi
    @Test
    fun toNegativeIntOrNull_should_pass_with_a_negative_Int() {
        val expected: Number = Random.nextInt(from = Int.MIN_VALUE, until = 0)
        val result: NegativeInt? = expected.toNegativeIntOrNull()
        val x: NegativeInt = assertNotNull(result)
        val actual: Int = x.toInt()
        assertEquals(expected, actual)
    }

    @ExperimentalNumberApi
    @Test
    fun toNegativeIntOrNull_should_fail_with_a_strictly_positive_Int() {
        val number: Number = Random.nextInt(from = 1, until = Int.MAX_VALUE)
        val result: NegativeInt? = number.toNegativeIntOrNull()
        assertNull(result)
    }

    @ExperimentalNumberApi
    @Test
    fun toNegativeIntOrThrow_should_pass_with_a_negative_Int() {
        val expected: Number = Random.nextInt(from = Int.MIN_VALUE, until = 0)
        val result: NegativeInt = expected.toNegativeIntOrThrow()
        val actual: Int = result.toInt()
        assertEquals(expected, actual)
    }

    @ExperimentalNumberApi
    @Test
    fun toNegativeIntOrThrow_should_fail_with_a_strictly_positive_Int() {
        val number: Number = Random.nextInt(from = 1, until = Int.MAX_VALUE)
        val exception: IllegalArgumentException = assertFailsWith {
            number.toNegativeIntOrThrow()
        }
        val actualMessage: String = assertNotNull(exception.message)
        val expectedMessage: String = NegativeIntConstructionException(number)
            .message
        assertEquals(expectedMessage, actualMessage)
    }

    @ExperimentalNumberApi
    @Test
    fun unaryMinus_should_pass() {
        // GIVEN
        val x: NegativeInt = NegativeInt.random()
        // WHEN
        val result: PositiveInt = -x
        // THEN
        result.toInt() shouldEqual -x.toInt()
    }

    @Test
    fun div_should_return_a_NegativeInt_with_a_StrictlyPositiveInt() {
        val x: NegativeInt = NegativeInt.random()
        val y: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val result: NegativeInt = x / y
        result.toInt() shouldEqual x.toInt() / y.toInt()
    }

    @Test
    fun div_should_return_a_PositiveInt_with_a_StrictlyNegativeInt() {
        val x: NegativeInt = NegativeInt.random()
        val y: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val result: PositiveInt = x / y
        result.toInt() shouldEqual x.toInt() / y.toInt()
    }

    @Test
    fun rem_should_return_a_NegativeInt_with_a_NonZeroInt() {
        val x: NegativeInt = NegativeInt.random()
        val y: NonZeroInt = NonZeroInt.random()
        val result: NegativeInt = x % y
        result.toInt() shouldEqual x.toInt() % y.toInt()
    }
}

class NegativeIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_NegativeInt_as_serial_name() {
        val result: String = NegativeIntSerializer.descriptor.serialName
        result shouldEqual "${Package.number}.NegativeInt"
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: NegativeInt = NegativeInt.random()
        val result: String = Json.encodeToString(NegativeIntSerializer, x)
        result shouldEqual Json.encodeToString(x.toInt())
    }

    @Test
    fun deserialization_should_pass_with_a_negative_Int() {
        val value: Int = NegativeInt.random()
            .toInt()
        val encoded: String = Json.encodeToString(value)
        val result: NegativeInt =
            Json.decodeFromString(NegativeIntSerializer, encoded)
        result.toInt() shouldEqual value
    }

    @Test
    fun deserialization_should_fail_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.random()
            .toInt()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString(NegativeIntSerializer, encoded)
        }
        exception.shouldHaveAMessage()
    }
}
