package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.InvalidEmailAddress
import org.kotools.types.internal.InvalidEmailAddressPattern
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
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
    fun equalsShouldPassWithEmailAddressHavingSameStringRepresentation() {
        val value: Any = Values.VALID
        val first: EmailAddress = EmailAddress.fromString(value)
        val second: Any = EmailAddress.fromString(value)
        val actual: Boolean = first.equals(second)
        assertTrue(actual)
    }

    @Test
    fun equalsShouldFailWithAnotherTypeThanEmailAddress() {
        val value: Any = Values.VALID
        val first: EmailAddress = EmailAddress.fromString(value)
        val second: Any = value
        val actual: Boolean = first.equals(second)
        assertFalse(actual)
    }

    @Test
    fun equalsShouldFailWithEmailAddressHavingAnotherStringRepresentation() {
        val first: EmailAddress = EmailAddress.fromString(Values.VALID)
        val second: Any = EmailAddress.fromString("second@kotools.org")
        val actual: Boolean = first.equals(second)
        assertFalse(actual)
    }

    @Test
    fun hashCodeShouldPass() {
        val value = "contact@kotools.org"
        val actual: Int = EmailAddress.fromString(value)
            .hashCode()
        val expected: Int = hashCodeOf(value)
        assertEquals(expected, actual)
    }

    @Test
    fun toStringShouldPass() {
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
        val expected = """^\S+@\S+\.\S+$"""
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
        val value: String = Values.MISSING_AT_SIGN
        this.fromString_Any_failingTest(value)
    }

    @Test
    fun fromString_Any_should_fail_with_a_missing_dot_in_domain_of_value() {
        val value: String = Values.MISSING_DOMAIN_DOT
        this.fromString_Any_failingTest(value)
    }

    @Test
    fun fromString_Any_should_fail_with_whitespaces_in_local_part_of_value() {
        val value: String = Values.WHITESPACES_IN_LOCAL_PART
        this.fromString_Any_failingTest(value)
    }

    @Test
    fun fromString_Any_should_fail_with_whitespaces_in_domain_first_label_of_value() {
        val value: String = Values.WHITESPACES_IN_DOMAIN_FIRST_LABEL
        this.fromString_Any_failingTest(value)
    }

    @Test
    fun fromString_Any_should_fail_with_whitespaces_in_domain_second_label_of_value() {
        val value: String = Values.WHITESPACES_IN_DOMAIN_SECOND_LABEL
        this.fromString_Any_failingTest(value)
    }

    private fun fromString_Any_failingTest(value: String) {
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value)
        }
        val actual: String? = exception.message
        val expected: String = InvalidEmailAddress(value, EmailAddress.PATTERN)
            .toString()
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
        val value = "first-contact@kotools.org"
        val pattern = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value, pattern)
        }
        val actual: String? = exception.message
        val expected: String = InvalidEmailAddress(value, pattern)
            .toString()
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
        val expected: String = InvalidEmailAddressPattern(
            "$pattern",
            validationPattern = EmailAddress.PATTERN
        ).toString()
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

    @Test
    fun orNull_Any_should_pass_with_a_valid_value() {
        val value: Any = Values.VALID
        val actual: EmailAddress? = EmailAddress.orNull(value)
        assertNotNull(
            actual,
            message = "'EmailAddress.orNull(Any)' should pass with '$value'."
        )
    }

    @Test
    fun orNull_Any_should_fail_with_a_missing_at_sign_in_value(): Unit =
        this.orNullFailsWith(Values.MISSING_AT_SIGN)

    @Test
    fun orNull_Any_should_fail_with_a_missing_dot_in_domain_of_value(): Unit =
        this.orNullFailsWith(Values.MISSING_DOMAIN_DOT)

    @Test
    fun orNull_Any_should_fail_with_whitespaces_in_local_part_of_value(): Unit =
        this.orNullFailsWith(Values.WHITESPACES_IN_LOCAL_PART)

    @Test
    fun orNull_Any_should_fail_with_whitespaces_in_domain_first_label_of_value(): Unit =
        this.orNullFailsWith(Values.WHITESPACES_IN_DOMAIN_FIRST_LABEL)

    @Test
    fun orNull_Any_should_fail_with_whitespaces_in_domain_second_label_of_value(): Unit =
        this.orNullFailsWith(Values.WHITESPACES_IN_DOMAIN_SECOND_LABEL)

    private fun orNullFailsWith(value: Any) {
        val actual: EmailAddress? = EmailAddress.orNull(value)
        assertNull(
            actual,
            message = "'EmailAddress.orNull(Any)' should fail with '$value'."
        )
    }

    @Test
    fun orNull_Any_Any_should_pass_with_valid_value_and_pattern() {
        val value: Any = Values.VALID
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val actual: EmailAddress? = EmailAddress.orNull(value, pattern)
        assertNotNull(actual)
    }

    @Test
    fun orNull_Any_Any_should_fail_with_invalid_value() {
        val value: Any = "first-contact@kotools.org"
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val actual: EmailAddress? = EmailAddress.orNull(value, pattern)
        assertNull(actual)
    }

    @Test
    fun orNull_Any_Any_should_fail_with_invalid_pattern() {
        val value: Any = Values.VALID
        val pattern: Any = "^[a-z]+\\.[a-z]+\$"
        val actual: EmailAddress? = EmailAddress.orNull(value, pattern)
        assertNull(actual)
    }
}
