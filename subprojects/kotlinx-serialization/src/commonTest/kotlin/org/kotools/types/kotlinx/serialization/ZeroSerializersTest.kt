package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import kotlin.test.Test
import kotlin.test.assertEquals

class ZeroSerializersTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun byteSerializerShouldReturnValidDescriptor() {
        val actual: SerialDescriptor = Zero.byteSerializer()
            .descriptor
        val expected: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = "ZeroAsByteSerializer",
            PrimitiveKind.STRING
        )
        assertEquals(expected, actual)
    }
}
