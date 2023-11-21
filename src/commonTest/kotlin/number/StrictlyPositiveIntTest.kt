/*
 * Copyright 2022-2023 Loïc Lamarque.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.experimental.ExperimentalRangeApi
import kotools.types.internal.unexpectedCreationFailure
import kotools.types.range.InclusiveBound
import kotools.types.range.NotEmptyRange
import kotools.types.shouldBeNotNull
import kotools.types.shouldEqual
import kotools.types.shouldFailWithIllegalArgumentException
import kotools.types.shouldFailWithSerializationException
import kotools.types.shouldNotEqual
import kotools.types.text.toNotBlankString
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StrictlyPositiveIntCompanionTest {
    @Test
    fun min_should_equal_one() {
        val actual: StrictlyPositiveInt = StrictlyPositiveInt.min
        val expectedValue = 1
        val expected: StrictlyPositiveInt = expectedValue
            .toStrictlyPositiveInt()
            .getOrNull()
            ?: unexpectedCreationFailure<StrictlyPositiveInt>(expectedValue)
        assertEquals(expected, actual)
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
    fun toStrictlyPositiveInt_should_pass_with_a_strictly_positive_Number() {
        val number: Number = Random.nextInt(1..Int.MAX_VALUE)
        val result: Result<StrictlyPositiveInt> = number.toStrictlyPositiveInt()
        result.getOrThrow().toInt() shouldEqual number
    }

    @Test
    fun toStrictlyPositiveInt_should_fail_with_a_negative_Number() {
        val number: Number = Random.nextInt(Int.MIN_VALUE..0)
        val result: Result<StrictlyPositiveInt> = number.toStrictlyPositiveInt()
        result.shouldFailWithIllegalArgumentException { getOrThrow() }
            .message
            .shouldBeNotNull()
            .toNotBlankString()
            .getOrThrow()
            .shouldEqual(StrictlyPositiveInt errorMessageFor number)
    }

    @ExperimentalNumberApi
    @Test
    fun unaryMinus_should_pass() {
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val result: StrictlyNegativeInt = -x
        result.toInt() shouldEqual -x.toInt()
    }

    @Test
    fun toString_should_behave_like_an_Int(): Unit = StrictlyPositiveInt
        .random()
        .run { "$this" shouldEqual "${toInt()}" }
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
        val error: SerializationException =
            Json.shouldFailWithSerializationException {
                decodeFromString<StrictlyPositiveInt>(encoded)
            }
        error.message.shouldBeNotNull()
            .toNotBlankString()
            .getOrThrow()
            .shouldEqual(StrictlyPositiveInt errorMessageFor value)
    }
}
