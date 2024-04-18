package org.kotools.types.kotlinx.serialization.internal

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.EmailAddress
import org.kotools.types.internal.InvalidEmailAddress
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EmailAddressAsStringSerializerTest {
    @OptIn(
        ExperimentalKotoolsTypesApi::class,
        ExperimentalSerializationApi::class
    )
    @Test
    fun descriptor_serialName_should_be_the_qualified_name_of_EmailAddress() {
        val actual: String =
            EmailAddressAsStringSerializer.descriptor.serialName
        val expected: String = serialNameOf<EmailAddress>()
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun descriptor_kind_should_be_PrimitiveKind_STRING() {
        val actual: SerialKind = EmailAddressAsStringSerializer.descriptor.kind
        val expected: SerialKind = PrimitiveKind.STRING
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun serialization_should_behave_like_for_the_String_type() {
        val value = "contact@kotools.org"
        val address: EmailAddress = EmailAddress.fromString(value)
        val actual: String =
            Json.encodeToString(EmailAddressAsStringSerializer, address)
        val expected: String = Json.encodeToString(value)
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun deserialization_should_pass_with_a_valid_String() {
        val value = "contact@kotools.org"
        val encoded: String = Json.encodeToString(value)
        val actual: EmailAddress =
            Json.decodeFromString(EmailAddressAsStringSerializer, encoded)
        val expected: EmailAddress = EmailAddress.fromString(value)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_fail_with_a_String_missing_an_at_sign(): Unit =
        this.failingDeserialization(value = "contactKotools.org")

    @Test
    fun deserialization_should_fail_with_a_String_missing_a_dot_in_domain(): Unit =
        this.failingDeserialization(value = "contact@kotoolsOrg")

    @Test
    fun deserialization_should_fail_with_a_String_having_whitespaces_in_local_part(): Unit =
        this.failingDeserialization(value = " cont act @kotools.org")

    @Test
    fun deserialization_should_fail_with_a_String_having_whitespaces_in_domain_first_label(): Unit =
        this.failingDeserialization(value = "contact@ ko tools .org")

    @Test
    fun deserialization_should_fail_with_a_String_having_whitespaces_in_domain_second_label(): Unit =
        this.failingDeserialization(value = "contact@kotools. or g  ")

    @OptIn(ExperimentalKotoolsTypesApi::class)
    private fun failingDeserialization(value: String) {
        val encoded: String = Json.encodeToString(value)
        val deserializer = EmailAddressAsStringSerializer
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString(deserializer, encoded)
        }
        val actual: String? = exception.message
        val reason = InvalidEmailAddress(value, EmailAddress.PATTERN)
        val error =
            DeserializationError(deserializer, decodedValue = value, "$reason")
        val expected: String = error.toString()
        assertEquals(expected, actual)
    }
}
