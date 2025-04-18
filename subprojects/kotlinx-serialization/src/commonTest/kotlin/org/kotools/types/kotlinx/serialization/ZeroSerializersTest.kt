package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import kotlin.test.Test
import kotlin.test.assertEquals

class ZeroSerializersTest {
    @OptIn(
        ExperimentalKotoolsTypesApi::class,
        ExperimentalSerializationApi::class
    )
    @Test
    fun byteSerializerShouldReturnValidDescriptor() {
        val descriptor: SerialDescriptor = Zero.byteSerializer()
            .descriptor
        assertEquals(
            expected = "ZeroAsByteSerializer",
            actual = descriptor.serialName
        )
        assertEquals(expected = PrimitiveKind.BYTE, actual = descriptor.kind)
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
}
