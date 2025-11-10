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
import org.kotools.types.EmailAddressRegex
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.fail

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

class EmailAddressRegexAsStringSerializerTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun descriptor() {
        // Given
        val module: SerializersModule = KotoolsTypesSerializersModule()
        val serializer: KSerializer<EmailAddressRegex> = module.serializer()
        // When
        val result: SerialDescriptor = serializer.descriptor
        // Then
        val serialName: String = EmailAddressRegex::class.simpleName
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
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        // When
        val result: String = format.encodeToString(regex)
        // Then
        val expected: String = regex.toString()
            .replace(oldValue = "\\", newValue = "\\\\")
            .let { "\"$it\"" }
        assertEquals(expected, result)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun deserializeWithValidRegex() {
        // Given
        val format =
            Json { this.serializersModule = KotoolsTypesSerializersModule() }
        val encoded = """"^\\S+@\\S+\\.\\S+$""""
        // When
        val result: EmailAddressRegex = format.decodeFromString(encoded)
        // Then
        val expected: EmailAddressRegex = EmailAddressRegex.default()
        assertEquals(expected, result)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun deserializeWithInvalidRegex() {
        // Given
        val format =
            Json { this.serializersModule = KotoolsTypesSerializersModule() }
        val encoded = """"^\\S+\\S+\\.\\S+$""""
        // When
        val result: IllegalArgumentException = assertFailsWith {
            format.decodeFromString<EmailAddressRegex>(encoded)
        }
        // Then
        val text: String = encoded.trim('"')
            .replace(oldValue = "\\\\", newValue = "\\")
        val expected = "Invalid email address regex (was: $text)."
        assertEquals(expected, result.message)
    }
}
