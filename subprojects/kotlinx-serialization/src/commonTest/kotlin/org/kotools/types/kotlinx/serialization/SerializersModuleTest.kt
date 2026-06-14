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
import org.kotools.types.number.Integer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.fail

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressAsStringSerializerTest {
    @Test
    fun descriptor() {
        val module: SerializersModule = KotoolsTypesSerializersModule()
        val serializer: KSerializer<EmailAddress> = module.serializer()

        val actual: SerialDescriptor = serializer.descriptor

        val expected: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = "org.kotools.types.EmailAddress",
            PrimitiveKind.STRING
        )
        assertEquals(expected, actual)
    }

    @Test
    fun serialize() {
        val format =
            Json { this.serializersModule = KotoolsTypesSerializersModule() }
        val text = "test@domain.org"
        val emailAddress: EmailAddress = EmailAddress.of(text)
            ?: fail("Null email address")

        val actual: String = format.encodeToString(emailAddress)

        assertEquals(expected = "\"$text\"", actual)
    }

    @Test
    fun deserializeWithValidEmailAddress() {
        val format =
            Json { this.serializersModule = KotoolsTypesSerializersModule() }
        val encoded = "\"test@domain.org\""

        val actual: EmailAddress = format.decodeFromString(encoded)

        val text: String = encoded.trim('"')
        val expected: EmailAddress = EmailAddress.of(text)
            ?: fail("Null email address")
        assertEquals(expected, actual)
    }

    @Test
    fun deserializeWithInvalidEmailAddress() {
        val format =
            Json { this.serializersModule = KotoolsTypesSerializersModule() }
        val encoded = "\"testDomain.org\""

        val actual: IllegalStateException = assertFailsWith {
            format.decodeFromString<EmailAddress>(encoded)
        }

        val text: String = encoded.trim('"')
        val expected = "Invalid email address: '$text'"
        assertEquals(expected, actual.message)
    }
}

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressRegexAsStringSerializerTest {
    @Test
    fun descriptor() {
        val module: SerializersModule = KotoolsTypesSerializersModule()
        val serializer: KSerializer<EmailAddressRegex> = module.serializer()

        val actual: SerialDescriptor = serializer.descriptor

        val expected: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = "org.kotools.types.EmailAddressRegex",
            PrimitiveKind.STRING
        )
        assertEquals(expected, actual)
    }

    @Test
    fun serialize() {
        val format =
            Json { this.serializersModule = KotoolsTypesSerializersModule() }
        val regex: EmailAddressRegex = EmailAddressRegex.default()

        val actual: String = format.encodeToString(regex)

        val expected: String = regex.toString()
            .replace(oldValue = "\\", newValue = "\\\\")
            .let { "\"$it\"" }
        assertEquals(expected, actual)
    }

    @Test
    fun deserializeWithValidRegex() {
        val format =
            Json { this.serializersModule = KotoolsTypesSerializersModule() }
        val encoded = """"^\\S+@\\S+\\.\\S+$""""

        val actual: EmailAddressRegex = format.decodeFromString(encoded)

        val expected: EmailAddressRegex = EmailAddressRegex.default()
        assertEquals(expected, actual)
    }

    @Test
    fun deserializeWithInvalidRegex() {
        val format =
            Json { this.serializersModule = KotoolsTypesSerializersModule() }
        val encoded = """"^\\S+\\S+\\.\\S+$""""

        val actual: IllegalStateException = assertFailsWith {
            format.decodeFromString<EmailAddressRegex>(encoded)
        }

        val text: String = encoded.trim('"')
            .replace(oldValue = "\\\\", newValue = "\\")
        val expected = "Invalid email address regex: '$text'"
        assertEquals(expected, actual.message)
    }
}

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerAsStringSerializerTest {
    @Test
    fun descriptor() {
        val module: SerializersModule = KotoolsTypesSerializersModule()
        val serializer: KSerializer<Integer> = module.serializer()

        val actual: SerialDescriptor = serializer.descriptor

        val expected: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = "org.kotools.types.number.Integer",
            PrimitiveKind.STRING
        )
        assertEquals(expected, actual)
    }

    @Test
    fun serialize() {
        val format =
            Json { this.serializersModule = KotoolsTypesSerializersModule() }
        val value: Integer = Integer.of(150)

        val actual: String = format.encodeToString(value)

        assertEquals(expected = "\"${value}\"", actual)
    }

    @Test
    fun deserializeWithValidInteger() {
        val format =
            Json { this.serializersModule = KotoolsTypesSerializersModule() }
        val encoded = "\"150\""

        val actual: Integer = format.decodeFromString(encoded)

        val text: String = encoded.trim('"')
        val expected: Integer = Integer.parse(text)
        assertEquals(expected, actual)
    }

    @Test
    fun deserializeWithInvalidInteger() {
        val format =
            Json { this.serializersModule = KotoolsTypesSerializersModule() }
        val encoded = "\"abc\""

        val actual: IllegalStateException = assertFailsWith {
            format.decodeFromString<Integer>(encoded)
        }

        val text: String = encoded.trim('"')
        val expected = "Invalid integer representation: '$text'"
        assertEquals(expected, actual.message)
    }
}
