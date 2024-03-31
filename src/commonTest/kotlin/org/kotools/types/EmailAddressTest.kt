package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressCompanionTest {
    @Test
    fun pattern_should_pass() {
        val actual: String = EmailAddress.PATTERN
        val expected = "^\\S+@\\S+\\.\\S+\$"
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
}
