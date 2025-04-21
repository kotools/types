package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.ExperimentalSerializationApi
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

    @OptIn(
        ExperimentalKotoolsTypesApi::class,
        ExperimentalSerializationApi::class
    )
    @Test
    fun shortSerializerShouldReturnValidDescriptor() {
        val descriptor: SerialDescriptor = Zero.shortSerializer()
            .descriptor
        assertEquals(
            expected = "ZeroAsShortSerializer",
            actual = descriptor.serialName
        )
        assertEquals(expected = PrimitiveKind.SHORT, actual = descriptor.kind)
    }

    @OptIn(
        ExperimentalKotoolsTypesApi::class,
        ExperimentalSerializationApi::class
    )
    @Test
    fun intSerializerShouldReturnValidDescriptor() {
        val descriptor: SerialDescriptor = Zero.intSerializer()
            .descriptor
        assertEquals(
            expected = "ZeroAsIntSerializer",
            actual = descriptor.serialName
        )
        assertEquals(expected = PrimitiveKind.INT, actual = descriptor.kind)
    }

    @OptIn(
        ExperimentalKotoolsTypesApi::class,
        ExperimentalSerializationApi::class
    )
    @Test
    fun longSerializerShouldReturnValidDescriptor() {
        val descriptor: SerialDescriptor = Zero.longSerializer()
            .descriptor
        assertEquals(
            expected = "ZeroAsLongSerializer",
            actual = descriptor.serialName
        )
        assertEquals(expected = PrimitiveKind.LONG, actual = descriptor.kind)
    }

    @OptIn(
        ExperimentalKotoolsTypesApi::class,
        ExperimentalSerializationApi::class
    )
    @Test
    fun floatSerializerShouldReturnValidDescriptor() {
        val descriptor: SerialDescriptor = Zero.floatSerializer()
            .descriptor
        assertEquals(
            expected = "ZeroAsFloatSerializer",
            actual = descriptor.serialName
        )
        assertEquals(expected = PrimitiveKind.FLOAT, actual = descriptor.kind)
    }

    @OptIn(
        ExperimentalKotoolsTypesApi::class,
        ExperimentalSerializationApi::class
    )
    @Test
    fun doubleSerializerShouldReturnValidDescriptor() {
        val descriptor: SerialDescriptor = Zero.doubleSerializer()
            .descriptor
        assertEquals(
            expected = "ZeroAsDoubleSerializer",
            actual = descriptor.serialName
        )
        assertEquals(expected = PrimitiveKind.DOUBLE, actual = descriptor.kind)
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
