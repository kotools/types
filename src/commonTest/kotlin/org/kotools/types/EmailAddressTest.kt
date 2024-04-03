package org.kotools.types

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.qualifiedNameOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

private object Values {
    const val VALID: String = "contact@kotools.org"
    const val MISSING_AT_SIGN: String = "contactKotools.org"
    const val MISSING_DOMAIN_DOT: String = "contact@kotoolsOrg"
    const val WHITESPACES_IN_LOCAL_PART: String = " cont  act @kotools.org"
    const val WHITESPACES_IN_DOMAIN_FIRST_LABEL: String =
        "contact@ ko tools .org"
    const val WHITESPACES_IN_DOMAIN_SECOND_LABEL: String =
        "contact@kotools. or  g "
}

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressTest {
    @Test
    fun structural_equality_should_pass_with_the_same_instance() {
        val first: EmailAddress = EmailAddress.fromString(Values.VALID)
        val second: Any = first
        val equality: Boolean = first.equals(second)
        assertTrue(equality)
        val firstHashCode: Int = first.hashCode()
        val secondHashCode: Int = second.hashCode()
        assertEquals(firstHashCode, secondHashCode)
    }

    @Test
    fun structural_equality_should_pass_with_another_EmailAddress_having_the_same_string_representation() {
        val value: Any = Values.VALID
        val first: EmailAddress = EmailAddress.fromString(value)
        val second: Any = EmailAddress.fromString(value)
        val equality: Boolean = first.equals(second)
        assertTrue(equality)
        val firstHashCode: Int = first.hashCode()
        val secondHashCode: Int = second.hashCode()
        assertEquals(firstHashCode, secondHashCode)
    }

    @Test
    fun structural_equality_should_fail_with_null() {
        val first: EmailAddress = EmailAddress.fromString(Values.VALID)
        val second: Any? = null
        val equality: Boolean = first.equals(second)
        assertFalse(equality)
        val firstHashCode: Int = first.hashCode()
        val secondHashCode: Int = second.hashCode()
        assertNotEquals(firstHashCode, secondHashCode)
    }

    @Test
    fun structural_equality_should_fail_with_another_object_having_another_type_than_EmailAddress() {
        val value: Any = Values.VALID
        val first: EmailAddress = EmailAddress.fromString(value)
        val second: Any = value
        val equality: Boolean = first.equals(second)
        assertFalse(equality)
        val firstHashCode: Int = first.hashCode()
        val secondHashCode: Int = second.hashCode()
        assertNotEquals(firstHashCode, secondHashCode)
    }

    @Test
    fun structural_equality_should_fail_with_another_EmailAddress_having_another_string_representation() {
        val first: EmailAddress = EmailAddress.fromString(Values.VALID)
        val second: Any = EmailAddress.fromString("second@kotools.org")
        val equality: Boolean = first.equals(second)
        assertFalse(equality)
        val firstHashCode: Int = first.hashCode()
        val secondHashCode: Int = second.hashCode()
        assertNotEquals(firstHashCode, secondHashCode)
    }

    @Test
    fun toString_should_pass() {
        val value: Any = Values.VALID
        val address: EmailAddress = EmailAddress.fromString(value)
        val actual: String = address.toString()
        assertEquals(expected = value, actual)
    }
}

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressCompanionTest {
    @Test
    fun pattern_should_pass() {
        val actual: String = EmailAddress.PATTERN
        val expected = "^\\S+@\\S+\\.\\S+\$"
        assertEquals(expected, actual)
    }

    @Test
    fun fromString_Any_should_pass_with_a_valid_value() {
        val value: Any = Values.VALID
        val actual: Result<EmailAddress> = kotlin.runCatching {
            EmailAddress.fromString(value)
        }
        assertTrue(actual.isSuccess)
    }

    @Test
    fun fromString_Any_should_fail_with_a_missing_at_sign_in_value() {
        val value: Any = Values.MISSING_AT_SIGN
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value)
        }
        val actual: String? = exception.message
        val expected = "${InvalidEmailAddress(value)}"
        assertEquals(expected, actual)
    }

    @Test
    fun fromString_Any_should_fail_with_a_missing_dot_in_domain_of_value() {
        val value: Any = Values.MISSING_DOMAIN_DOT
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value)
        }
        val actual: String? = exception.message
        val expected = "${InvalidEmailAddress(value)}"
        assertEquals(expected, actual)
    }

    @Test
    fun fromString_Any_should_fail_with_whitespaces_in_local_part_of_value() {
        val value: Any = Values.WHITESPACES_IN_LOCAL_PART
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value)
        }
        val actual: String? = exception.message
        val expected = "${InvalidEmailAddress(value)}"
        assertEquals(expected, actual)
    }

    @Test
    fun fromString_Any_should_fail_with_whitespaces_in_domain_first_label_of_value() {
        val value: Any = Values.WHITESPACES_IN_DOMAIN_FIRST_LABEL
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value)
        }
        val actual: String? = exception.message
        val expected = "${InvalidEmailAddress(value)}"
        assertEquals(expected, actual)
    }

    @Test
    fun fromString_Any_should_fail_with_whitespaces_in_domain_second_label_of_value() {
        val value: Any = Values.WHITESPACES_IN_DOMAIN_SECOND_LABEL
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value)
        }
        val actual: String? = exception.message
        val expected = "${InvalidEmailAddress(value)}"
        assertEquals(expected, actual)
    }

    @Test
    fun fromString_Any_Any_should_pass_with_valid_value_and_pattern() {
        val value: Any = Values.VALID
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val result: Result<EmailAddress> = kotlin.runCatching {
            EmailAddress.fromString(value, pattern)
        }
        assertTrue(result.isSuccess)
    }

    @Test
    fun fromString_Any_Any_should_fail_with_invalid_value() {
        val value: Any = "first-contact@kotools.org"
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value, pattern)
        }
        val actual: String? = exception.message
        val expected = "${InvalidEmailAddress(value, pattern)}"
        assertEquals(expected, actual)
    }

    @Test
    fun fromString_Any_Any_should_fail_with_invalid_pattern() {
        val value: Any = Values.VALID
        val pattern: Any = "^[a-z]+\\.[a-z]+\$"
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value, pattern)
        }
        val actual: String? = exception.message
        val expected = "${InvalidEmailAddressPattern(pattern)}"
        assertEquals(expected, actual)
    }

    @Test
    fun fromStringOrNull_Any_should_pass_with_a_valid_value() {
        val value: Any = Values.VALID
        val actual: EmailAddress? = EmailAddress.fromStringOrNull(value)
        assertNotNull(actual)
    }

    @Test
    fun fromStringOrNull_Any_should_fail_with_a_missing_at_sign_in_value() {
        val value: Any = Values.MISSING_AT_SIGN
        val actual: EmailAddress? = EmailAddress.fromStringOrNull(value)
        assertNull(actual)
    }

    @Test
    fun fromStringOrNull_Any_should_fail_with_a_missing_dot_in_domain_of_value() {
        val value: Any = Values.MISSING_DOMAIN_DOT
        val actual: EmailAddress? = EmailAddress.fromStringOrNull(value)
        assertNull(actual)
    }

    @Test
    fun fromStringOrNull_Any_should_fail_with_whitespaces_in_local_part_of_value() {
        val value: Any = Values.WHITESPACES_IN_LOCAL_PART
        val actual: EmailAddress? = EmailAddress.fromStringOrNull(value)
        assertNull(actual)
    }

    @Test
    fun fromStringOrNull_Any_should_fail_with_whitespaces_in_domain_first_label_of_value() {
        val value: Any = Values.WHITESPACES_IN_DOMAIN_FIRST_LABEL
        val actual: EmailAddress? = EmailAddress.fromStringOrNull(value)
        assertNull(actual)
    }

    @Test
    fun fromStringOrNull_Any_should_fail_with_whitespaces_in_domain_second_label_of_value() {
        val value: Any = Values.WHITESPACES_IN_DOMAIN_SECOND_LABEL
        val actual: EmailAddress? = EmailAddress.fromStringOrNull(value)
        assertNull(actual)
    }

    @Test
    fun fromStringOrNull_Any_Any_should_pass_with_valid_value_and_pattern() {
        val value: Any = Values.VALID
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val actual: EmailAddress? =
            EmailAddress.fromStringOrNull(value, pattern)
        assertNotNull(actual)
    }

    @Test
    fun fromStringOrNull_Any_Any_should_fail_with_invalid_value() {
        val value: Any = "first-contact@kotools.org"
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val actual: EmailAddress? =
            EmailAddress.fromStringOrNull(value, pattern)
        assertNull(actual)
    }

    @Test
    fun fromStringOrNull_Any_Any_should_fail_with_invalid_pattern() {
        val value: Any = Values.VALID
        val pattern: Any = "^[a-z]+\\.[a-z]+\$"
        val actual: EmailAddress? =
            EmailAddress.fromStringOrNull(value, pattern)
        assertNull(actual)
    }
}

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressAsStringSerializerTest {
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun descriptor_should_be_valid() {
        val actual: SerialDescriptor = serializer<EmailAddress>().descriptor
        val expectedSerialName: String = qualifiedNameOf<EmailAddress>()
        assertEquals(expectedSerialName, actual.serialName)
        assertEquals(expected = PrimitiveKind.STRING, actual.kind)
    }

    @Test
    fun serialization_should_behave_like_for_the_String_type() {
        val value: String = Values.VALID
        val address: EmailAddress = EmailAddress.fromString(value)
        val actual: String = Json.encodeToString(address)
        val expected: String = Json.encodeToString(value)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_pass_from_a_valid_String() {
        val value: String = Values.VALID
        val encoded: String = Json.encodeToString(value)
        val actual: EmailAddress = Json.decodeFromString(encoded)
        val expected: EmailAddress = EmailAddress.fromString(value)
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_fail_from_a_String_missing_an_at_sign() {
        val value: String = Values.MISSING_AT_SIGN
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<EmailAddress>(encoded)
        }
        val actual: String? = exception.message
        val expected = "${InvalidEmailAddress(value)}"
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_fail_from_a_String_missing_a_dot_in_domain() {
        val value: String = Values.MISSING_DOMAIN_DOT
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<EmailAddress>(encoded)
        }
        val actual: String? = exception.message
        val expected = "${InvalidEmailAddress(value)}"
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_fail_from_a_String_having_whitespaces_in_local_part() {
        val value: String = Values.WHITESPACES_IN_LOCAL_PART
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<EmailAddress>(encoded)
        }
        val actual: String? = exception.message
        val expected = "${InvalidEmailAddress(value)}"
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_fail_from_a_String_having_whitespaces_in_domain_first_label() {
        val value: String = Values.WHITESPACES_IN_DOMAIN_FIRST_LABEL
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<EmailAddress>(encoded)
        }
        val actual: String? = exception.message
        val expected = "${InvalidEmailAddress(value)}"
        assertEquals(expected, actual)
    }

    @Test
    fun deserialization_should_fail_from_a_String_having_whitespaces_in_domain_second_label() {
        val value: String = Values.WHITESPACES_IN_DOMAIN_SECOND_LABEL
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<EmailAddress>(encoded)
        }
        val actual: String? = exception.message
        val expected = "${InvalidEmailAddress(value)}"
        assertEquals(expected, actual)
    }
}
