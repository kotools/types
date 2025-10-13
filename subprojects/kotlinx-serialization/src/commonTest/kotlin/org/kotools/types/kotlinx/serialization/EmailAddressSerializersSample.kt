package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.kotools.types.EmailAddress
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class EmailAddressSerializersSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun stringSerializer() {
        val serializer: KSerializer<EmailAddress> =
            EmailAddress.stringSerializer()
        val address: EmailAddress = EmailAddress.of("contact@kotools.org")
            ?: fail()
        val encoded: String = Json.encodeToString(serializer, address)
        assertEquals("\"$address\"", encoded)
        val decoded: EmailAddress = Json.decodeFromString(serializer, encoded)
        assertEquals(address, decoded)
    }
}
