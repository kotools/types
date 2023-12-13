/*
 * Copyright 2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotools.types.Package
import kotools.types.internal.ErrorMessage
import kotools.types.internal.hashCodeOf
import kotools.types.internal.shouldBeGreaterThanZero
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalKotoolsTypesApi
class StrictlyPositiveDoubleTest {
    @Test
    fun toStrictlyPositiveDouble_should_pass_with_a_strictly_positive_Number() {
        val value: Number =
            Random.nextDouble(from = 0.1, until = Double.MAX_VALUE)
        val result: Result<StrictlyPositiveDouble> =
            value.toStrictlyPositiveDouble()
        val actualValue: Double = result.getOrThrow()
            .toDouble()
        assertEquals(expected = value, actualValue)
    }

    @Test
    fun toStrictlyPositiveDouble_should_fail_with_a_negative_Number() {
        val value: Number = Random
            .nextDouble(from = 0.1, until = Double.MAX_VALUE)
            .unaryMinus()
        val result: Result<StrictlyPositiveDouble> =
            value.toStrictlyPositiveDouble()
        val exception: IllegalArgumentException =
            assertFailsWith { result.getOrThrow() }
        val actual = ErrorMessage(exception)
        val expected: ErrorMessage = value.shouldBeGreaterThanZero()
        assertEquals(expected, actual)
    }

    @Test
    fun equals_should_pass_with_the_same_instance() {
        val x: StrictlyPositiveDouble = Random
            .nextDouble(from = 0.1, until = Double.MAX_VALUE)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val y: StrictlyPositiveDouble = x
        val actual: Boolean = x.equals(y)
        assertTrue(actual)
    }

    @Test
    fun equals_should_pass_with_another_StrictlyPositiveDouble_having_the_same_value() {
        val x: StrictlyPositiveDouble = Random
            .nextDouble(from = 0.1, until = Double.MAX_VALUE)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val y: StrictlyPositiveDouble = x.toDouble()
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val actual: Boolean = x.equals(y)
        assertTrue(actual)
    }

    @Test
    fun equals_should_fail_with_null() {
        val x: StrictlyPositiveDouble = Random
            .nextDouble(from = 0.1, until = Double.MAX_VALUE)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val y: Any? = null
        val actual: Boolean = x.equals(y)
        assertFalse(actual)
    }

    @Test
    fun equals_should_fail_with_an_object_other_than_StrictlyPositiveDouble() {
        val x: StrictlyPositiveDouble = Random
            .nextDouble(from = 0.1, until = Double.MAX_VALUE)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val y = Any()
        val actual: Boolean = x.equals(y)
        assertFalse(actual)
    }

    @Test
    fun equals_should_fail_with_another_StrictlyPositiveDouble_having_a_different_value() {
        val x: StrictlyPositiveDouble = Random
            .nextDouble(from = 0.1, until = Double.MAX_VALUE)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val y: StrictlyPositiveDouble = Random
            .nextDouble(from = 0.1, until = Double.MAX_VALUE)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val actual: Boolean = x.equals(y)
        assertFalse(actual)
    }

    @Test
    fun hashCode_should_pass() {
        val value: Double =
            Random.nextDouble(from = 0.1, until = Double.MAX_VALUE)
        val actual: Int = value.toStrictlyPositiveDouble()
            .getOrThrow()
            .hashCode()
        val expected: Int = hashCodeOf(value)
        assertEquals(expected, actual)
    }

    @Test
    fun toString_should_return_the_string_representation_of_its_value() {
        val value: Double = Random.nextDouble()
        val actual: String = value.toStrictlyPositiveDouble()
            .getOrThrow()
            .toString()
        val expected: String = value.toString()
        assertEquals(expected, actual)
    }

    @Test
    fun compareTo_should_return_zero_with_the_same_StrictlyPositiveDouble() {
        val x: StrictlyPositiveDouble = Random.nextDouble()
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val y: StrictlyPositiveDouble = x.toDouble()
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val actual: Int = x.compareTo(y)
        val expected = 0
        assertEquals(expected, actual)
    }

    @Test
    fun compareTo_should_return_a_negative_Int_with_a_greater_StrictlyPositiveDouble() {
        val x: StrictlyPositiveDouble = Random.nextDouble(until = 0.5)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val y: StrictlyPositiveDouble = Random
            .nextDouble(from = 0.6, until = Double.MAX_VALUE)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val actual: Int = x.compareTo(y)
        assertTrue(actual < 0)
    }

    @Test
    fun compareTo_should_return_a_positive_Int_with_a_lower_StrictlyPositiveDouble() {
        val x: StrictlyPositiveDouble = Random
            .nextDouble(from = 0.6, until = Double.MAX_VALUE)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val y: StrictlyPositiveDouble = Random.nextDouble(until = 0.5)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val actual: Int = x.compareTo(y)
        assertTrue(actual > 0)
    }
}

@ExperimentalKotoolsTypesApi
class StrictlyPositiveDoubleSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_serial_name_should_be_the_qualified_name_of_StrictlyPositiveDouble() {
        val actual: String = serializer<StrictlyPositiveDouble>()
            .descriptor
            .serialName
        val expected = "${Package.NUMBER}.StrictlyPositiveDouble"
        assertEquals(expected, actual)
    }

    @ExperimentalSerializationApi
    @Test
    fun descriptor_kind_should_be_a_PrimitiveKind_Double() {
        val actual: SerialKind = serializer<StrictlyPositiveDouble>()
            .descriptor
            .kind
        val expected: SerialKind = PrimitiveKind.DOUBLE
        assertEquals(expected, actual)
    }

    @Test
    fun serialize_should_behave_like_a_Double() {
        val value: Double =
            Random.nextDouble(from = 0.1, until = Double.MAX_VALUE)
        val number: StrictlyPositiveDouble = value.toStrictlyPositiveDouble()
            .getOrThrow()
        val actual: String = Json.encodeToString(number)
        val expected: String = Json.encodeToString(value)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialize_should_pass_with_a_strictly_positive_Double() {
        val value: Double =
            Random.nextDouble(from = 0.1, until = Double.MAX_VALUE)
        val actual: StrictlyPositiveDouble = Json.decodeFromString("$value")
        val expected: StrictlyPositiveDouble = value.toStrictlyPositiveDouble()
            .getOrThrow()
        assertEquals(expected, actual)
    }

    @Test
    fun deserialize_should_pass_with_a_negative_Double() {
        val value: Double = Random
            .nextDouble(from = 0.1, until = Double.MAX_VALUE)
            .unaryMinus()
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<StrictlyPositiveDouble>("$value")
        }
        val actual = ErrorMessage(exception)
        val expected: ErrorMessage = value.shouldBeGreaterThanZero()
        assertEquals(expected, actual)
    }
}
