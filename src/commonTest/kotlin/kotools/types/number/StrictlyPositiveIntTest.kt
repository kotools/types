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
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.simpleNameOf
import kotools.types.internal.unexpectedCreationFailure
import kotools.types.shouldBeNotNull
import kotools.types.shouldEqual
import kotools.types.shouldFailWithIllegalArgumentException
import kotools.types.shouldNotEqual
import kotools.types.text.toNotBlankString
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class StrictlyPositiveIntCompanionTest {
    @Test
    fun min_should_equal_one() {
        val actual: StrictlyPositiveInt = StrictlyPositiveInt.min
        val expected: StrictlyPositiveInt = 1.toStrictlyPositiveIntOrFailure()
        assertEquals(expected, actual)
    }

    @Test
    fun max_should_equal_the_maximum_value_of_Int() {
        val actual: StrictlyPositiveInt = StrictlyPositiveInt.max
        val expected: StrictlyPositiveInt = Int.MAX_VALUE
            .toStrictlyPositiveIntOrFailure()
        assertEquals(expected, actual)
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

    @Test
    fun toString_should_behave_like_an_Int(): Unit = StrictlyPositiveInt
        .random()
        .run { "$this" shouldEqual "${toInt()}" }
}

class StrictlyPositiveIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_serial_name_should_be_the_qualified_name_of_StrictlyPositiveInt() {
        val actual: String = serializer<StrictlyPositiveInt>()
            .descriptor
            .serialName
        val simpleName: String = simpleNameOf<StrictlyPositiveInt>()
        val expected = "${KotoolsTypesPackage.Number}.$simpleName"
        assertEquals(expected, actual)
    }

    @ExperimentalSerializationApi
    @Test
    fun descriptor_kind_should_be_PrimitiveKind_INT() {
        val actual: SerialKind = serializer<StrictlyPositiveInt>()
            .descriptor
            .kind
        val expected: SerialKind = PrimitiveKind.INT
        assertEquals(expected, actual)
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        val number: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val actual: String = Json.encodeToString(number)
        val numberValue: Int = number.toInt()
        val expected: String = Json.encodeToString(numberValue)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_pass_with_a_strictly_positive_Int() {
        val expected: StrictlyPositiveInt = StrictlyPositiveInt.random()
        val value: Int = expected.toInt()
        val encoded: String = Json.encodeToString(value)
        val actual: StrictlyPositiveInt = Json.decodeFromString(encoded)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_fail_with_a_negative_Int() {
        val value: Int = NegativeInt.random()
            .toInt()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<StrictlyPositiveInt>(encoded)
        }
        exception.message.shouldBeNotNull()
            .toNotBlankString()
            .getOrThrow()
            .shouldEqual(StrictlyPositiveInt errorMessageFor value)
    }
}

private fun Number.toStrictlyPositiveIntOrFailure(): StrictlyPositiveInt =
    toStrictlyPositiveInt()
        .getOrNull()
        ?: unexpectedCreationFailure<StrictlyPositiveInt>(value = this)
