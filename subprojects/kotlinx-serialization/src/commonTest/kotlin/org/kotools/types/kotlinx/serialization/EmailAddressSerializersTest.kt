package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import org.kotools.types.EmailAddress
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

class EmailAddressSerializersTest {
    @OptIn(
        ExperimentalKotoolsTypesApi::class,
        ExperimentalSerializationApi::class
    )
    @Test
    fun stringSerializerShouldReturnValidDescriptor() {
        val descriptor: SerialDescriptor = EmailAddress.stringSerializer()
            .descriptor
        assertEquals(
            expected = "EmailAddressAsStringSerializer",
            actual = descriptor.serialName
        )
        assertEquals(expected = PrimitiveKind.STRING, actual = descriptor.kind)
    }
}
