package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.NUMBER_PACKAGE
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

    @ExperimentalRangeApi
    @Test
    fun range_should_start_with_an_InclusiveBound_that_equals_zero() {
        val range: NotEmptyRange<PositiveInt> = PositiveInt.range
        assertTrue { range.start is InclusiveBound }
        range.start.value shouldEqual ZeroInt
    }

    @ExperimentalRangeApi
    @Test
    fun range_should_end_with_an_InclusiveBound_that_equals_the_maximum_value_of_Int() {
        val range: NotEmptyRange<PositiveInt> = PositiveInt.range
        assertTrue { range.end is InclusiveBound }
        range.end.value.toInt() shouldEqual Int.MAX_VALUE
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

    @Test
    fun toPositiveInt_should_fail_with_a_strictly_negative_Int() {
        val number: Number = Random.nextInt(from = Int.MIN_VALUE, until = 0)
        val result: Result<PositiveInt> = number.toPositiveInt()
        val exception: IllegalArgumentException = assertFailsWith {
            result.getOrThrow()
        }
        val actualMessage: String = assertNotNull(exception.message)
        val expectedMessage: String = PositiveIntConstructionException(number)
            .message
        assertEquals(expectedMessage, actualMessage)
    }

    @ExperimentalNumberApi
    @Test
    fun toPositiveIntOrNull_should_pass_with_a_positive_Int() {
        val expected: Number = Random.nextInt(from = 0, until = Int.MAX_VALUE)
        val result: PositiveInt? = expected.toPositiveIntOrNull()
        val x: PositiveInt = assertNotNull(result)
        val actual: Int = x.toInt()
        assertEquals(expected, actual)
    }

    @ExperimentalNumberApi
    @Test
    fun toPositiveIntOrNull_should_fail_with_a_strictly_negative_Int() {
        val number: Number = Random.nextInt(from = Int.MIN_VALUE, until = 0)
        val result: PositiveInt? = number.toPositiveIntOrNull()
        assertNull(result)
    }

    @ExperimentalNumberApi
    @Test
    fun toPositiveIntOrThrow_should_pass_with_a_positive_Int() {
        val expected: Number = Random.nextInt(from = 0, until = Int.MAX_VALUE)
        val result: PositiveInt = expected.toPositiveIntOrThrow()
        val actual: Int = result.toInt()
        assertEquals(expected, actual)
    }

    @ExperimentalNumberApi
    @Test
    fun toPositiveIntOrThrow_should_fail_with_a_strictly_negative_Int() {
        val number: Number = Random.nextInt(from = Int.MIN_VALUE, until = 0)
        val exception: IllegalArgumentException = assertFailsWith {
            number.toPositiveIntOrThrow()
        }
        val actualMessage: String = assertNotNull(exception.message)
        val expectedMessage: String = PositiveIntConstructionException(number)
            .message
        assertEquals(expectedMessage, actualMessage)
    }

    @ExperimentalNumberApi
    @Test
    fun unaryMinus_should_pass() {
        // GIVEN
        val x: PositiveInt = PositiveInt.random()
        // WHEN
        val result: NegativeInt = -x
        // THEN
        result.toInt() shouldEqual -x.toInt()
    }

    @Test
    fun div_should_return_a_PositiveInt_with_a_StrictlyPositiveInt() {
        val x: PositiveInt = PositiveInt.random()
        val y: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val result: PositiveInt = x / y
        result.toInt() shouldEqual x.toInt() / y.toInt()
    }

    @Test
    fun div_should_return_a_NegativeInt_with_a_StrictlyNegativeInt() {
        val x: PositiveInt = PositiveInt.random()
        val y: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val result: NegativeInt = x / y
        result.toInt() shouldEqual x.toInt() / y.toInt()
    }

    @Test
    fun rem_should_return_a_PositiveInt_with_a_NonZeroInt() {
        val x: PositiveInt = PositiveInt.random()
        val y: NonZeroInt = NonZeroInt.random()
        val result: PositiveInt = x % y
        result.toInt() shouldEqual x.toInt() % y.toInt()
    }
}

class PositiveIntSerializerTest {
    private val serializer: KSerializer<PositiveInt> = PositiveIntSerializer

    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_PositiveInt_as_serial_name(): Unit =
        serializer.descriptor
            .serialName shouldEqual "$NUMBER_PACKAGE.PositiveInt"

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: PositiveInt = PositiveInt.random()
        val result: String = Json.encodeToString(serializer, x)
        result shouldEqual Json.encodeToString(x.toInt())
    }

    @Test
    fun deserialization_should_pass_with_a_positive_Int() {
        val expected: Int = PositiveInt.random().toInt()
        val encoded: String = Json.encodeToString(expected)
        val result: PositiveInt =
            Json.decodeFromString(PositiveIntSerializer, encoded)
        result.toInt() shouldEqual expected
    }

    @Test
    fun deserialization_should_fail_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.random().toInt()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString(PositiveIntSerializer, encoded)
        }
        exception.shouldHaveAMessage()
    }
}
