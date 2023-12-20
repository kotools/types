/*
 * Copyright 2022-2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.experimental.ExperimentalRangeApi
import kotools.types.experimental.InclusiveBound
import kotools.types.experimental.NotEmptyRange
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.simpleNameOf
import kotools.types.shouldBeNotNull
import kotools.types.shouldEqual
import kotools.types.shouldFailWithIllegalArgumentException
import kotools.types.shouldNotEqual
import kotools.types.text.toNotBlankString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

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
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun range_should_start_with_an_InclusiveBound_that_equals_the_minimum_value_of_Int() {
        val range: NotEmptyRange<StrictlyNegativeInt> =
            StrictlyNegativeInt.range
        assertTrue { range.start is InclusiveBound }
        range.start.value.toInt() shouldEqual Int.MIN_VALUE
    }

    @ExperimentalRangeApi
    @OptIn(ExperimentalKotoolsTypesApi::class)
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

class StrictlyNegativeIntTest {
    @Test
    fun number_toStrictlyNegativeInt_should_pass_with_a_strictly_negative_Int() {
        val value: Number = StrictlyNegativeInt.random().toInt()
        val result: Result<StrictlyNegativeInt> = value.toStrictlyNegativeInt()
        result.getOrThrow().toInt() shouldEqual value
    }

    @Test
    fun number_toStrictlyNegativeInt_should_fail_with_a_positive_Int() {
        val number: Number = PositiveInt.random().toInt()
        val result: Result<StrictlyNegativeInt> = number.toStrictlyNegativeInt()
        result.shouldFailWithIllegalArgumentException { getOrThrow() }
            .message
            .shouldBeNotNull()
            .toNotBlankString()
            .getOrThrow()
            .shouldEqual(StrictlyNegativeInt errorMessageFor number)
    }

    @Test
    fun toString_should_behave_like_an_Int() {
        val x: StrictlyNegativeInt = StrictlyNegativeInt.random()
        "$x" shouldEqual "${x.toInt()}"
    }
}

class StrictlyNegativeIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_serial_name_should_be_the_qualified_name_of_StrictlyNegativeInt() {
        val actual: String = serializer<StrictlyNegativeInt>()
            .descriptor
            .serialName
        val simpleName: String = simpleNameOf<StrictlyNegativeInt>()
        val expected = "${KotoolsTypesPackage.Number}.$simpleName"
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

    @Test
    fun deserialization_should_fail_with_a_positive_Int() {
        val value: Int = PositiveInt.random()
            .toInt()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<StrictlyNegativeInt>(encoded)
        }
        exception.message.shouldBeNotNull()
            .toNotBlankString()
            .getOrThrow()
            .shouldEqual(StrictlyNegativeInt errorMessageFor value)
    }
}
