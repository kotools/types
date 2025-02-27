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
}
