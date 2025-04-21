package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.json.Json
import kotools.types.internal.simpleNameOf
import org.kotools.types.EmailAddressRegex
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

class EmailAddressRegexSerializersTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun stringSerializerShouldPass() {
        val serializer: KSerializer<EmailAddressRegex> =
            EmailAddressRegex.stringSerializer()
        val expectedDescriptor: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = simpleNameOf(serializer::class),
            kind = PrimitiveKind.STRING
        )
        assertEquals(expectedDescriptor, serializer.descriptor)
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        val encoded: String = Json.encodeToString(serializer, regex)
        regex.toString()
            .replace(oldValue = "\\", newValue = "\\\\")
            .let { assertEquals(expected = "\"$it\"", actual = encoded) }
        val decoded: EmailAddressRegex =
            Json.decodeFromString(serializer, encoded)
        assertEquals(expected = regex, actual = decoded)
    }
}
