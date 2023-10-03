package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.NUMBER_PACKAGE
import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.experimental.ExperimentalRangeApi
import kotools.types.range.InclusiveBound
import kotools.types.range.NotEmptyRange
import kotools.types.shouldBeNotNull
import kotools.types.shouldBeNull
import kotools.types.shouldEqual
import kotools.types.shouldNotEqual
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class StrictlyNegativeIntTest {
    @Test
    fun toStrictlyNegativeInt_should_pass_with_a_strictly_negative_Number() {
        val number: Number = Random.nextInt(from = Int.MIN_VALUE, until = 0)
        val result: Result<StrictlyNegativeInt> = number.toStrictlyNegativeInt()
        result.getOrThrow()
            .toInt()
            .shouldEqual(number)
    }

    @Test
    fun toStrictlyNegativeInt_should_fail_with_a_positive_Number() {
        val number: Number = Random.nextInt(from = 0, until = Int.MAX_VALUE)
        val result: Result<StrictlyNegativeInt> = number.toStrictlyNegativeInt()
        assertFailsWith<IllegalArgumentException> { result.getOrThrow() }
            .message
            .shouldBeNotNull()
            .shouldEqual { number.shouldBeStrictlyNegativeMessage() }
    }

    @ExperimentalNumberApi
    @Test
    fun number_toStrictlyNegativeIntOrNull_should_not_be_null_with_a_strictly_negative_Int() {
        val number: Number = StrictlyNegativeInt.random().toInt()
        val result: StrictlyNegativeInt? = number.toStrictlyNegativeIntOrNull()
        result.shouldBeNotNull().toInt() shouldEqual number
    }

    @ExperimentalNumberApi
    @Test
    fun number_toStrictlyNegativeIntOrNull_should_be_null_with_a_positive_Int() {
        val number: Number = PositiveInt.random().toInt()
        val result: StrictlyNegativeInt? = number.toStrictlyNegativeIntOrNull()
        result.shouldBeNull()
    }

    @ExperimentalNumberApi
    @Test
    fun number_toStrictlyNegativeIntOrThrow_should_pass_with_a_negative_Int() {
        val number: Number = StrictlyNegativeInt.random().toInt()
        val result: StrictlyNegativeInt = number.toStrictlyNegativeIntOrThrow()
        result.toInt() shouldEqual number
    }

    @ExperimentalNumberApi
    @Test
    fun number_toStrictlyNegativeIntOrThrow_should_throw_with_a_positive_Int() {
        val number: Number = PositiveInt.random().toInt()
        val exception: IllegalArgumentException =
            assertFailsWith { number.toStrictlyNegativeIntOrThrow() }
        val actualMessage: String = assertNotNull(exception.message)
        val expectedMessage: String = number.shouldBeStrictlyNegativeMessage()
        assertEquals(expectedMessage, actualMessage)
    }

    @ExperimentalNumberApi
    @Test
    fun unaryMinus_should_pass() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val result: StrictlyPositiveInt = -x
        result.toInt() shouldEqual -x.toInt()
    }

    @Test
    fun toString_should_behave_like_an_Int() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random()
        "$x" shouldEqual "${x.toInt()}"
    }
}

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

    @ExperimentalRangeApi
    @Test
    fun range_should_start_with_an_InclusiveBound_that_equals_the_minimum_value_of_Int() {
        val range: NotEmptyRange<StrictlyNegativeInt> =
            StrictlyNegativeInt.range
        assertTrue { range.start is InclusiveBound }
        range.start.value.toInt() shouldEqual Int.MIN_VALUE
    }

    @ExperimentalRangeApi
    @Test
    fun range_should_end_with_an_InclusiveBound_that_equals_minus_one() {
        val range: NotEmptyRange<StrictlyNegativeInt> =
            StrictlyNegativeInt.range
        assertTrue { range.end is InclusiveBound }
        range.end.value.toInt() shouldEqual -1
    }

    @Test
    fun random_should_return_different_values() {
        val result: StrictlyNegativeInt = StrictlyNegativeInt.random()
        result.toInt() shouldNotEqual StrictlyNegativeInt.random()
    }
}

class StrictlyNegativeIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_StrictlyNegativeInt_as_serial_name(): Unit =
        StrictlyNegativeInt.serializer()
            .descriptor
            .serialName shouldEqual "$NUMBER_PACKAGE.StrictlyNegativeInt"

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val result: String = Json.encodeToString(x)
        result shouldEqual Json.encodeToString(x.toInt())
    }

    @Test
    fun deserialization_should_pass_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.random().toInt()
        val encoded: String = Json.encodeToString(value)
        val result: StrictlyNegativeInt = Json.decodeFromString(encoded)
        result.toInt() shouldEqual value
    }

    @Test
    fun deserialization_should_fail_with_a_positive_Int() {
        val value: Int = PositiveInt.random().toInt()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<StrictlyNegativeInt>(encoded)
        }
        val actualMessage: String = assertNotNull(exception.message)
        val expectedMessage: String = value.shouldBeStrictlyNegativeMessage()
        assertEquals(expectedMessage, actualMessage)
    }
}
