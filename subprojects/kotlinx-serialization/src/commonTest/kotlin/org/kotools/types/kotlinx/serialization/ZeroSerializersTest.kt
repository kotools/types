package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.json.Json
import kotools.types.internal.simpleNameOf
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import kotlin.test.Test
import kotlin.test.assertEquals

class ZeroSerializersTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun byteSerializerDescriptor() {
        val serializer: KSerializer<Zero> = Zero.byteSerializer()
        val actual: SerialDescriptor = serializer.descriptor
        val expected: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = simpleNameOf(serializer::class),
            kind = PrimitiveKind.BYTE
        )
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun shortSerializerDescriptor() {
        val serializer: KSerializer<Zero> = Zero.shortSerializer()
        val actual: SerialDescriptor = serializer.descriptor
        val expected: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = simpleNameOf(serializer::class),
            kind = PrimitiveKind.SHORT
        )
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun intSerializerDescriptor() {
        val serializer: KSerializer<Zero> = Zero.intSerializer()
        val actual: SerialDescriptor = serializer.descriptor
        val expected: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = simpleNameOf(serializer::class),
            kind = PrimitiveKind.INT
        )
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun longSerializerDescriptor() {
        val serializer: KSerializer<Zero> = Zero.longSerializer()
        val actual: SerialDescriptor = serializer.descriptor
        val expected: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = simpleNameOf(serializer::class),
            kind = PrimitiveKind.LONG
        )
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun floatSerializerDescriptor() {
        val serializer: KSerializer<Zero> = Zero.floatSerializer()
        val actual: SerialDescriptor = serializer.descriptor
        val expected: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = simpleNameOf(serializer::class),
            kind = PrimitiveKind.FLOAT
        )
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun doubleSerializerDescriptor() {
        val serializer: KSerializer<Zero> = Zero.doubleSerializer()
        val actual: SerialDescriptor = serializer.descriptor
        val expected: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = simpleNameOf(serializer::class),
            kind = PrimitiveKind.DOUBLE
        )
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun stringSerializerShouldPass() {
        val serializer: KSerializer<Zero> = Zero.stringSerializer()
        val actualDescriptor: SerialDescriptor = serializer.descriptor
        val expectedDescriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = simpleNameOf(serializer::class),
            kind = PrimitiveKind.STRING
        )
        assertEquals(expectedDescriptor, actualDescriptor)
        val zero = Zero()
        val encoded: String = Json.encodeToString(serializer, zero)
        assertEquals(expected = "\"$zero\"", actual = encoded)
        val decoded: Zero = Json.decodeFromString(serializer, encoded)
        assertEquals(expected = zero, actual = decoded)
    }
}
