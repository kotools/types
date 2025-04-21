package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.descriptors.PrimitiveKind
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import kotlin.test.Test

class ZeroSerializersTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun byteSerializerDescriptor(): Unit = Zero.byteSerializer()
        .assertDescriptor(PrimitiveKind.BYTE)

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun shortSerializerDescriptor(): Unit = Zero.shortSerializer()
        .assertDescriptor(PrimitiveKind.SHORT)

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun intSerializerDescriptor(): Unit = Zero.intSerializer()
        .assertDescriptor(PrimitiveKind.INT)

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun longSerializerDescriptor(): Unit = Zero.longSerializer()
        .assertDescriptor(PrimitiveKind.LONG)

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun floatSerializerDescriptor(): Unit = Zero.floatSerializer()
        .assertDescriptor(PrimitiveKind.FLOAT)

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun doubleSerializerDescriptor(): Unit = Zero.doubleSerializer()
        .assertDescriptor(PrimitiveKind.DOUBLE)

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun stringSerializerDescriptor(): Unit = Zero.stringSerializer()
        .assertDescriptor(PrimitiveKind.STRING)
}
