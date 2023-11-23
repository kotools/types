/*
 * Copyright 2022-2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.experimental.ExperimentalRangeApi
import kotools.types.internal.unexpectedCreationFailure
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
    fun unaryMinus_should_pass() {
        val number: PositiveInt = PositiveInt.random()
        val actual: NegativeInt = -number
        val expected: NegativeInt = number.toInt()
            .unaryMinus()
            .toNegativeIntOrFailure()
        assertEquals(expected, actual)
    }

    @Test
    fun div_should_return_a_PositiveInt_with_a_StrictlyPositiveInt() {
        val x: PositiveInt = PositiveInt.random()
        val y: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val actual: PositiveInt = x / y
        val expected: PositiveInt = (x.toInt() / y.toInt())
            .toPositiveIntOrFailure()
        assertEquals(expected, actual)
    }

    @Test
    fun div_should_return_a_NegativeInt_with_a_StrictlyNegativeInt() {
        val x: PositiveInt = PositiveInt.random()
        val y: StrictlyNegativeInt = StrictlyNegativeInt.random()
        val actual: NegativeInt = x / y
        val expected: NegativeInt = (x.toInt() / y.toInt())
            .toNegativeIntOrFailure()
        assertEquals(expected, actual)
    }

    @Test
    fun rem_should_return_a_PositiveInt_with_a_NonZeroInt() {
        val x: PositiveInt = PositiveInt.random()
        val y: NonZeroInt = NonZeroInt.random()
        val actual: PositiveInt = x % y
        val expected: PositiveInt = (x.toInt() % y.toInt())
            .toPositiveIntOrFailure()
        assertEquals(expected, actual)
    }
}

class PositiveIntSerializerTest {
    private val serializer: KSerializer<PositiveInt> = PositiveIntSerializer

    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_PositiveInt_as_serial_name() {
        val actual: String = serializer.descriptor.serialName
        val expected = "${Package.NUMBER}.PositiveInt"
        assertEquals(expected, actual)
    }

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

internal fun Number.toPositiveIntOrFailure(): PositiveInt = toPositiveInt()
    .getOrNull()
    ?: unexpectedCreationFailure<PositiveInt>(value = this)
