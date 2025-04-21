package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.kotools.types.EmailAddressRegex
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

class EmailAddressRegexSerializersSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun stringSerializer() {
        val serializer: KSerializer<EmailAddressRegex> =
            EmailAddressRegex.stringSerializer()
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        val encoded: String = Json.encodeToString(serializer, regex)
        regex.toString()
            .replace(oldValue = "\\", newValue = "\\\\")
            .let { assertEquals("\"$it\"", encoded) }
        val decoded: EmailAddressRegex =
            Json.decodeFromString(serializer, encoded)
        assertEquals(regex, decoded)
    }
}
