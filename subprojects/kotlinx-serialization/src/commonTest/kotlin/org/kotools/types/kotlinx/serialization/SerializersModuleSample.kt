package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.kotools.types.EmailAddress
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

class SerializersModuleSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun kotoolsTypesSerializersModule() {
        val format = Json {
            serializersModule = KotoolsTypesSerializersModule()
        }
        val address: EmailAddress = EmailAddress.orThrow("contact@kotools.org")
        val encoded: String = format.encodeToString(address)
        assertEquals(expected = "\"$address\"", encoded)
        val decoded: EmailAddress = format.decodeFromString(encoded)
        assertEquals(expected = address, decoded)
    }
}
