package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import org.kotools.types.EmailAddress
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.kotlinx.serialization.internal.EmailAddressRegexAsStringSerializer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.fail

@OptIn(ExperimentalKotoolsTypesApi::class)
class SerializersModuleTest {
    @Test
    fun includesEmailAddressRegexAsStringSerializer() {
        // When
        val result: SerializersModule = KotoolsTypesSerializersModule()
        // Then
        val expected = EmailAddressRegexAsStringSerializer()
        result.assertIncludes(expected)
    }
}

class EmailAddressAsStringSerializerTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun descriptor() {
        // Given
        val module: SerializersModule = KotoolsTypesSerializersModule()
        val serializer: KSerializer<EmailAddress> = module.serializer()
        // When
        val result: SerialDescriptor = serializer.descriptor
        // Then
        val serialName: String = EmailAddress::class.simpleName
            ?: fail("Serial name can't be null.")
        val expected: SerialDescriptor =
            PrimitiveSerialDescriptor(serialName, PrimitiveKind.STRING)
        assertEquals(expected, result)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun serialize() {
        // Given
        val format =
            Json { this.serializersModule = KotoolsTypesSerializersModule() }
        val text = "contact@kotools.org"
        val emailAddress: EmailAddress = EmailAddress.of(text)
            ?: fail("Email address can't be null.")
        // When
        val result: String = format.encodeToString(emailAddress)
        // Then
        assertEquals(expected = "\"$text\"", result)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun deserializeWithValidEmailAddress() {
        // Given
        val format =
            Json { this.serializersModule = KotoolsTypesSerializersModule() }
        val encoded = "\"contact@kotools.org\""
        // When
        val result: EmailAddress = format.decodeFromString(encoded)
        // Then
        val text: String = encoded.trim('"')
        val expected: EmailAddress = EmailAddress.of(text)
            ?: fail("Email address can't be null.")
        assertEquals(expected, result)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun deserializeWithInvalidEmailAddress() {
        // Given
        val format =
            Json { this.serializersModule = KotoolsTypesSerializersModule() }
        val encoded = "\"contactKotools.org\""
        // When
        val result: IllegalArgumentException = assertFailsWith {
            format.decodeFromString<EmailAddress>(encoded)
        }
        // Then
        val text: String = encoded.trim('"')
        val expected = "Invalid email address (was: $text)."
        assertEquals(expected, result.message)
    }
}
