package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

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
        val value: Any = "contact@kotools.org"
        val actual: Result<EmailAddress> = kotlin.runCatching {
            EmailAddress.fromString(value)
        }
        assertTrue(actual.isSuccess)
    }

    @Test
    fun fromString_Any_should_fail_with_a_missing_at_sign_in_value() {
        val value: Any = "contactKotools.org"
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value)
        }
        val actual: String? = exception.message
        val expected: String = InvalidEmailAddress(value).message
        assertEquals(expected, actual)
    }

    @Test
    fun fromString_Any_should_fail_with_a_missing_dot_in_domain_of_value() {
        val value: Any = "contact@kotoolsOrg"
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value)
        }
        val actual: String? = exception.message
        val expected: String = InvalidEmailAddress(value).message
        assertEquals(expected, actual)
    }

    @Test
    fun fromString_Any_should_fail_with_whitespaces_in_local_part_of_value() {
        val value: Any = " cont  act @kotools.org"
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value)
        }
        val actual: String? = exception.message
        val expected: String = InvalidEmailAddress(value).message
        assertEquals(expected, actual)
    }

    @Test
    fun fromString_Any_should_fail_with_whitespaces_in_domain_first_label_of_value() {
        val value: Any = "contact@ ko tools .org"
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value)
        }
        val actual: String? = exception.message
        val expected: String = InvalidEmailAddress(value).message
        assertEquals(expected, actual)
    }

    @Test
    fun fromString_Any_should_fail_with_whitespaces_in_domain_second_label_of_value() {
        val value: Any = "contact@kotools. or  g "
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value)
        }
        val actual: String? = exception.message
        val expected: String = InvalidEmailAddress(value).message
        assertEquals(expected, actual)
    }

    @Test
    fun fromString_Any_Any_should_pass_with_valid_value_and_pattern() {
        val value: Any = "contact@kotools.org"
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
        val expected: String = InvalidEmailAddress(value, pattern).message
        assertEquals(expected, actual)
    }

    @Test
    fun fromString_Any_Any_should_fail_with_invalid_pattern() {
        val value: Any = "contact@kotools.org"
        val pattern: Any = "^[a-z]+\\.[a-z]+\$"
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value, pattern)
        }
        val actual: String? = exception.message
        val expected: String = InvalidEmailAddressPattern(pattern).message
        assertEquals(expected, actual)
    }

    @Test
    fun fromStringOrNull_Any_should_pass_with_a_valid_value() {
        val value: Any = "contact@kotools.org"
        val actual: EmailAddress? = EmailAddress.fromStringOrNull(value)
        assertNotNull(actual)
    }

    @Test
    fun fromStringOrNull_Any_should_fail_with_a_missing_at_sign_in_value() {
        val value: Any = "contactKotools.org"
        val actual: EmailAddress? = EmailAddress.fromStringOrNull(value)
        assertNull(actual)
    }

    @Test
    fun fromStringOrNull_Any_should_fail_with_a_missing_dot_in_domain_of_value() {
        val value: Any = "contact@kotoolsOrg"
        val actual: EmailAddress? = EmailAddress.fromStringOrNull(value)
        assertNull(actual)
    }

    @Test
    fun fromStringOrNull_Any_should_fail_with_whitespaces_in_local_part_of_value() {
        val value: Any = " cont  act @kotools.org"
        val actual: EmailAddress? = EmailAddress.fromStringOrNull(value)
        assertNull(actual)
    }

    @Test
    fun fromStringOrNull_Any_should_fail_with_whitespaces_in_domain_first_label_of_value() {
        val value: Any = "contact@ ko tools .org"
        val actual: EmailAddress? = EmailAddress.fromStringOrNull(value)
        assertNull(actual)
    }

    @Test
    fun fromStringOrNull_Any_should_fail_with_whitespaces_in_domain_second_label_of_value() {
        val value: Any = "contact@kotools. or  g "
        val actual: EmailAddress? = EmailAddress.fromStringOrNull(value)
        assertNull(actual)
    }

    @Test
    fun fromStringOrNull_Any_Any_should_pass_with_valid_value_and_pattern() {
        val value: Any = "contact@kotools.org"
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
        val value: Any = "contact@kotools.org"
        val pattern: Any = "^[a-z]+\\.[a-z]+\$"
        val actual: EmailAddress? =
            EmailAddress.fromStringOrNull(value, pattern)
        assertNull(actual)
    }
}
