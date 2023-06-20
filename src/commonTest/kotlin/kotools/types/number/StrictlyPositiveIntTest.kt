package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
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
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class StrictlyPositiveIntCompanionTest {
    @ExperimentalNumberApi
    @Test
    fun unaryMinus_should_pass() {
        // GIVEN
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        // WHEN
        val result: StrictlyNegativeInt = -x
        // THEN
        result.toInt() shouldEqual -x.toInt()
    }

    @Test
    fun min_should_equal_one() {
        val result: StrictlyPositiveInt = StrictlyPositiveInt.min
        result.toInt() shouldEqual 1
    }

    @Test
    fun max_should_equal_the_maximum_value_of_Int() {
        val result: StrictlyPositiveInt = StrictlyPositiveInt.max
        result.toInt() shouldEqual Int.MAX_VALUE
    }

    @ExperimentalRangeApi
    @Test
    fun range_should_start_with_an_inclusive_bound_that_equals_1() {
        val range: NotEmptyRange<StrictlyPositiveInt> =
            StrictlyPositiveInt.range
        assertTrue { range.start is InclusiveBound }
        range.start.value.toInt() shouldEqual 1
    }

    @ExperimentalRangeApi
    @Test
    fun range_should_end_with_an_inclusive_bound_that_equals_the_maximum_value_of_Int() {
        val range: NotEmptyRange<StrictlyPositiveInt> =
            StrictlyPositiveInt.range
        assertTrue { range.end is InclusiveBound }
        range.end.value.toInt() shouldEqual Int.MAX_VALUE
    }

    @Test
    fun random_should_return_different_values() {
        val result: StrictlyPositiveInt = StrictlyPositiveInt.random()
        result.toInt() shouldNotEqual StrictlyPositiveInt.random().toInt()
    }
}

class StrictlyPositiveIntTest {
    @Test
    fun toString_should_behave_like_an_Int(): Unit = StrictlyPositiveInt
        .random()
        .run { "$this" shouldEqual "${toInt()}" }

    @Test
    fun number_toStrictlyPositiveInt_should_pass_with_a_strictly_positive_Number() {
        val number: Number = StrictlyPositiveInt.random().toInt()
        val result: Result<StrictlyPositiveInt> = number.toStrictlyPositiveInt()
        result.getOrThrow().toInt() shouldEqual number
    }

    @Test
    fun number_toStrictlyPositiveInt_should_fail_with_a_negative_Number() {
        val number: Number = NegativeInt.random().toInt()
        val result: Result<StrictlyPositiveInt> = number.toStrictlyPositiveInt()
        assertFailsWith<IllegalArgumentException>(block = result::getOrThrow)
            .shouldHaveAMessage()
    }
}

class StrictlyPositiveIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_StrictlyPositiveInt_as_serial_name(): Unit =
        StrictlyPositiveInt.serializer()
            .descriptor
            .serialName shouldEqual "${Package.number}.StrictlyPositiveInt"

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val result: String = Json.encodeToString(x)
        result shouldEqual Json.encodeToString(x.toInt())
    }

    @Test
    fun deserialization_should_pass_with_a_strictly_positive_Int() {
        val value: Int = StrictlyPositiveInt.random().toInt()
        val encoded: String = Json.encodeToString(value)
        val result: StrictlyPositiveInt = Json.decodeFromString(encoded)
        result.toInt() shouldEqual value
    }

    @Test
    fun deserialization_should_fail_with_a_negative_Int() {
        val value: Int = NegativeInt.random().toInt()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<StrictlyPositiveInt>(encoded)
        }
        exception.shouldHaveAMessage()
    }
}
