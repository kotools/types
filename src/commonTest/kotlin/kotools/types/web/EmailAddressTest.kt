package kotools.types.web

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.ErrorMessage
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.deserializationErrorMessage
import kotools.types.internal.simpleNameOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

private object Texts {
    const val INVALID_DOMAIN: String = "contact@ko tools. org"
    const val INVALID_LOCAL_PART: String = " contact@kotools.org"
    const val VALID: String = "contact@kotools.org"
    const val WITHOUT_AT_SIGN: String = "contact-kotools.org"
    const val WITHOUT_DOT: String = "contact@kotools_org"
}

@ExperimentalKotoolsTypesApi
class EmailAddressCompanionTest {
    @Test
    fun regex_should_pass() {
        val actual: Regex = EmailAddress.regex
        val expected = Regex("^\\S+@\\S+\\.\\S+\$")
        assertEquals(expected.pattern, actual.pattern)
    }

    @Test
    fun create_should_pass_with_a_valid_String() {
        EmailAddress.create(Texts.VALID)
    }

    @Test
    fun create_should_fail_with_a_String_that_does_not_have_an_at_sign() {
        val text: String = Texts.WITHOUT_AT_SIGN
        val exception: IllegalArgumentException =
            assertFailsWith { EmailAddress.create(text) }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage =
            EmailAddress.creationErrorMessage(text)
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun create_should_fail_with_a_String_that_does_not_have_a_dot() {
        val text: String = Texts.WITHOUT_DOT
        val exception: IllegalArgumentException =
            assertFailsWith { EmailAddress.create(text) }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage =
            EmailAddress.creationErrorMessage(text)
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun create_should_fail_with_a_String_having_an_invalid_local_part() {
        val text: String = Texts.INVALID_LOCAL_PART
        val exception: IllegalArgumentException =
            assertFailsWith { EmailAddress.create(text) }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage =
            EmailAddress.creationErrorMessage(text)
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun create_should_fail_with_a_String_having_an_invalid_domain() {
        val text: String = Texts.INVALID_DOMAIN
        val exception: IllegalArgumentException =
            assertFailsWith { EmailAddress.create(text) }
        val actualMessage = ErrorMessage(exception)
        val expectedMessage: ErrorMessage =
            EmailAddress.creationErrorMessage(text)
        assertEquals(expectedMessage, actualMessage)
    }

    @Test
    fun createOrNull_should_pass_with_a_valid_String() {
        val actual: EmailAddress? = EmailAddress.createOrNull(Texts.VALID)
        assertNotNull(actual)
    }

    @Test
    fun createOrNull_should_fail_with_a_String_that_does_not_have_an_at_sign() {
        val actual: EmailAddress? =
            EmailAddress.createOrNull(Texts.WITHOUT_AT_SIGN)
        assertNull(actual)
    }

    @Test
    fun createOrNull_should_fail_with_a_String_that_does_not_have_a_dot() {
        val actual: EmailAddress? = EmailAddress.createOrNull(Texts.WITHOUT_DOT)
        assertNull(actual)
    }

    @Test
    fun createOrNull_should_fail_with_a_String_having_an_invalid_local_part() {
        val actual: EmailAddress? =
            EmailAddress.createOrNull(Texts.INVALID_LOCAL_PART)
        assertNull(actual)
    }

    @Test
    fun createOrNull_should_fail_with_a_String_having_an_invalid_domain() {
        val actual: EmailAddress? =
            EmailAddress.createOrNull(Texts.INVALID_DOMAIN)
        assertNull(actual)
    }
}

@ExperimentalKotoolsTypesApi
class EmailAddressTest {
    @Test
    fun structural_equality_should_pass_with_the_same_object() {
        val first: EmailAddress =
            requireNotNull(EmailAddress.createOrNull(Texts.VALID))
        val second: EmailAddress = first
        assertEquals(first, second)
        assertEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun structural_equality_should_pass_with_another_EmailAddress_having_the_same_string_representation() {
        val text: String = Texts.VALID
        val first: EmailAddress =
            requireNotNull(EmailAddress.createOrNull(text))
        val second: EmailAddress =
            requireNotNull(EmailAddress.createOrNull(text))
        assertEquals(first, second)
        assertEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun structural_equality_should_fail_with_null() {
        val first: EmailAddress =
            requireNotNull(EmailAddress.createOrNull(Texts.VALID))
        val second: Any? = null
        assertNotEquals(first, second)
    }

    @Test
    fun structural_equality_should_fail_with_another_object_that_is_not_an_EmailAddress() {
        val text: String = Texts.VALID
        val first: EmailAddress =
            requireNotNull(EmailAddress.createOrNull(text))
        val second: Any = text
        assertNotEquals(first, second)
        assertNotEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun structural_equality_should_fail_with_another_EmailAddress_having_another_string_representation() {
        val text: String = Texts.VALID
        val first: EmailAddress =
            requireNotNull(EmailAddress.createOrNull(text))
        val second: EmailAddress =
            requireNotNull(EmailAddress.createOrNull("${text}x"))
        assertNotEquals(first, second)
        assertNotEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun toString_should_pass() {
        val text: String = Texts.VALID
        val address: EmailAddress =
            requireNotNull(EmailAddress.createOrNull(text))
        val actual: String = address.toString()
        assertEquals(expected = text, actual)
    }
}

@ExperimentalKotoolsTypesApi
class EmailAddressSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_serialName_should_be_the_qualified_name_of_EmailAddress() {
        val actual: String = serializer<EmailAddress>().descriptor.serialName
        val type: String = simpleNameOf<EmailAddress>()
        val expected = "${KotoolsTypesPackage.Experimental}.$type"
        assertEquals(expected, actual)
    }

    @ExperimentalSerializationApi
    @Test
    fun descriptor_kind_should_be_PrimitiveKind_STRING() {
        val actual: SerialKind = serializer<EmailAddress>().descriptor.kind
        val expected: SerialKind = PrimitiveKind.STRING
        assertEquals(expected, actual)
    }

    @Test
    fun serialization_should_behave_like_a_String() {
        val text: String = Texts.VALID
        val address: EmailAddress =
            requireNotNull(EmailAddress.createOrNull(text))
        val actual: String = Json.encodeToString(address)
        val expected: String = Json.encodeToString(text)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_pass_from_a_valid_String() {
        val text: String = Texts.VALID
        val encoded: String = Json.encodeToString(text)
        val actual: EmailAddress = Json.decodeFromString(encoded)
        val expected: EmailAddress =
            requireNotNull(EmailAddress.createOrNull(text))
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_fail_from_a_String_that_does_not_have_an_at_sign() {
        val text: String = Texts.WITHOUT_AT_SIGN
        val exception: SerializationException = assertFailsWith {
            val encoded: String = Json.encodeToString(text)
            Json.decodeFromString<EmailAddress>(encoded)
        }
        val actual = ErrorMessage(exception)
        val expected: ErrorMessage =
            deserializationErrorMessage<EmailAddress>(text)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_fail_from_a_String_that_does_not_have_a_dot() {
        val text: String = Texts.WITHOUT_DOT
        val exception: SerializationException = assertFailsWith {
            val encoded: String = Json.encodeToString(text)
            Json.decodeFromString<EmailAddress>(encoded)
        }
        val actual = ErrorMessage(exception)
        val expected: ErrorMessage =
            deserializationErrorMessage<EmailAddress>(text)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_fail_from_a_String_having_an_invalid_local_part() {
        val text: String = Texts.INVALID_LOCAL_PART
        val exception: SerializationException = assertFailsWith {
            val encoded: String = Json.encodeToString(text)
            Json.decodeFromString<EmailAddress>(encoded)
        }
        val actual = ErrorMessage(exception)
        val expected: ErrorMessage =
            deserializationErrorMessage<EmailAddress>(text)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_fail_from_a_String_having_an_invalid_domain() {
        val text: String = Texts.INVALID_DOMAIN
        val exception: SerializationException = assertFailsWith {
            val encoded: String = Json.encodeToString(text)
            Json.decodeFromString<EmailAddress>(encoded)
        }
        val actual = ErrorMessage(exception)
        val expected: ErrorMessage =
            deserializationErrorMessage<EmailAddress>(text)
        assertEquals(expected, actual)
    }
}
