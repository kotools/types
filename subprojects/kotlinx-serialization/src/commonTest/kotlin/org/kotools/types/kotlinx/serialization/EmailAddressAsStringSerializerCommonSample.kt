package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.kotools.types.EmailAddress
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
internal class EmailAddressAsStringSerializerCommonSample {
    @Test
    fun primaryConstructor() {
        EmailAddressAsStringSerializer()
    }

    @Test
    fun descriptor() {
        val serializer = EmailAddressAsStringSerializer()
        val descriptor: SerialDescriptor = serializer.descriptor
        val expected: SerialDescriptor = PrimitiveSerialDescriptor(
            serialName = "$serializer",
            PrimitiveKind.STRING
        )
        assertEquals(expected, descriptor)
    }

    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsOverride() {
        val serializer = EmailAddressAsStringSerializer()
        val other = EmailAddressAsStringSerializer()
        val equality: Boolean = serializer == other
        val message = "Instances of '$serializer' should be equal."
        assertTrue(equality, message)
    }

    @Test
    fun hashCodeOverride() {
        val serializer = EmailAddressAsStringSerializer()
        val other = EmailAddressAsStringSerializer()
        val equality: Boolean = serializer.hashCode() == other.hashCode()
        val message = "Hash codes of '$serializer' should be equal."
        assertTrue(equality, message)
    }

    // ----------------------- Serialization operations ------------------------

    @Test
    fun serialize() {
        val serializer = EmailAddressAsStringSerializer()
        val emailAddress: EmailAddress =
            EmailAddress.orThrow("contact@kotools.org")
        val encoded: String = Json.encodeToString(serializer, emailAddress)
        val expected: String = Json.encodeToString("$emailAddress")
        assertEquals(expected, encoded)
    }

    @Test
    fun deserialize() {
        val deserializer = EmailAddressAsStringSerializer()
        val text = "contact@kotools.org"
        val encoded: String = Json.encodeToString(text)
        val decoded: EmailAddress = Json.decodeFromString(deserializer, encoded)
        val expected: EmailAddress = EmailAddress.orThrow(text)
        assertEquals(expected, decoded)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        val serializer = EmailAddressAsStringSerializer()
        val serializerAsString: String = serializer.toString()
        val expected: String = checkNotNull(serializer::class.simpleName)
        assertEquals(expected, serializerAsString)
    }
}
