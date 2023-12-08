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
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.experimental.ExperimentalRangeApi
import kotools.types.experimental.NotEmptyRange
import kotools.types.shouldEqual
import kotools.types.shouldHaveAMessage
import kotools.types.shouldNotEqual
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

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
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun negativeRange_should_be_the_range_of_StrictlyNegativeInt() {
        val range: NotEmptyRange<StrictlyNegativeInt> = NonZeroInt.negativeRange
        range shouldEqual StrictlyNegativeInt.range
    }

    @ExperimentalRangeApi
    @OptIn(ExperimentalKotoolsTypesApi::class)
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
        val expectedMessage: String = NonZeroIntConstructionException.message
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
    private val serializer: KSerializer<NonZeroInt> = NonZeroIntSerializer

    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_NonZeroInt_as_serial_name() {
        val actual: String = serializer.descriptor.serialName
        val expected = "${Package.NUMBER}.NonZeroInt"
        assertEquals(expected, actual)
    }

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
