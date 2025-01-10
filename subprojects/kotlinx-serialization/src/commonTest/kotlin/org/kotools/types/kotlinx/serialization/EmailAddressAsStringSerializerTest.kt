package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.json.Json
import kotools.types.internal.hashCodeOf
import org.kotools.types.EmailAddress
import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.ErrorMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressAsStringSerializerTest {
    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsShouldFailWithAnotherTypeThanEmailAddressAsStringSerializer() {
        val serializer = EmailAddressAsStringSerializer()
        val other: String = serializer.toString()
        val actual: Boolean = serializer.equals(other)
        val message = "Instance of '$serializer' shouldn't equal another type."
        assertFalse(actual, message)
    }

    @Test
    fun hashCodeShouldUseToStringMethod() {
        val serializer = EmailAddressAsStringSerializer()
        val actual: Int = serializer.hashCode()
        val expected: Int = hashCodeOf("$serializer")
        val message = "Hash code of '$serializer' should be calculated from " +
                "its string representation."
        assertEquals(expected, actual, message)
    }

    // ----------------------- Serialization operations ------------------------

    @Test
    fun deserializeShouldFailWithInvalidText(): Unit = setOf(
        "contactKotools.org", // missing at-sign
        "contact@kotoolsOrg", // missing domain dot
        " cont  act @kotools.org", // whitespaces in local-part
        "contact@ ko tools .org", // whitespaces in domain's first label
        "contact@kotools. or  g " // whitespaces in domain's second label
    ).forEach {
        val deserializer = EmailAddressAsStringSerializer()
        val exception: IllegalArgumentException = assertFailsWith {
            Json.decodeFromString(deserializer, "\"$it\"")
        }
        val actual: String? = exception.message
        val expected: String =
            ErrorMessage.invalidEmailAddress(it, EmailAddress.PATTERN)
        assertEquals(expected, actual)
    }
}
