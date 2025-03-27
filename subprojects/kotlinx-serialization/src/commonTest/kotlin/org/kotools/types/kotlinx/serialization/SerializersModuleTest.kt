package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import org.kotools.types.EmailAddress
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.Zero
import kotlin.test.Test
import kotlin.test.assertEquals

class SerializersModuleTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun kotoolsTypesSerializersModuleIncludesEmailAddressAsStringSerializer() {
        val actual: KSerializer<EmailAddress> = KotoolsTypesSerializersModule()
            .serializer<EmailAddress>()
        val expected: KSerializer<EmailAddress> =
            EmailAddress.stringSerializer()
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun kotoolsTypesSerializersModuleIncludesZeroAsIntSerializer() {
        val actual: KSerializer<Zero> = KotoolsTypesSerializersModule()
            .serializer<Zero>()
        val expected: KSerializer<Zero> = Zero.intSerializer()
        assertEquals(expected, actual)
    }
}
