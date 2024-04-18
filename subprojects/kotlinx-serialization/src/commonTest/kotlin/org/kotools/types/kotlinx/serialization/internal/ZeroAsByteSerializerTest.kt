package org.kotools.types.kotlinx.serialization.internal

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroAsByteSerializerTest {
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun descriptor_serialName_should_be_the_qualified_name_of_Zero() {
        val serializer: KSerializer<Zero> = ZeroAsByteSerializer
        val actual: String = serializer.descriptor.serialName
        val expected: String = serialNameOf<Zero>()
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun descriptor_kind_should_be_PrimitiveKind_BYTE() {
        val serializer: KSerializer<Zero> = ZeroAsByteSerializer
        val actual: SerialKind = serializer.descriptor.kind
        val expected: SerialKind = PrimitiveKind.BYTE
        assertEquals(expected, actual)
    }

    @Test
    fun serialization_should_behave_like_for_the_Byte_type() {
        val serializer: KSerializer<Zero> = ZeroAsByteSerializer
        val zero = Zero()
        val actual: String = Json.encodeToString(serializer, zero)
        val zeroAsByte: Byte = 0.toByte()
        val expected: String = Json.encodeToString(zeroAsByte)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_pass_with_the_zero_number() {
        val zeroAsByte: Byte = 0.toByte()
        val encoded: String = Json.encodeToString(zeroAsByte)
        val deserializer: KSerializer<Zero> = ZeroAsByteSerializer
        val actual: Zero = Json.decodeFromString(deserializer, encoded)
        val expected = Zero()
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_fail_with_a_Byte_other_than_zero() {
        val number: Byte = Random
            .nextInt(from = 1, until = Byte.MAX_VALUE.toInt())
            .toByte()
        val encoded: String = Json.encodeToString(number)
        val deserializer: KSerializer<Zero> = ZeroAsByteSerializer
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString(deserializer, encoded)
        }
        val actual: String? = exception.message
        val reason = "It should be equal to zero."
        val error =
            DeserializationError(deserializer, decodedValue = number, reason)
        val expected: String = error.toString()
        assertEquals(expected, actual)
    }
}
