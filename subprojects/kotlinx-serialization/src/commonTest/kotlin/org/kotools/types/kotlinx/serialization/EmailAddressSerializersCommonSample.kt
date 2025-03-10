package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.kotools.types.EmailAddress
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

class EmailAddressSerializersCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun stringSerializer() {
        val serializer: KSerializer<EmailAddress> =
            EmailAddress.stringSerializer()
        val address: EmailAddress = EmailAddress.orThrow("contact@kotools.org")
        val encoded: String = Json.encodeToString(serializer, address)
        assertEquals("\"$address\"", encoded)
        val decoded: EmailAddress = Json.decodeFromString(serializer, encoded)
        assertEquals(address, decoded)
    }
}
