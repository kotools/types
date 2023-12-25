/*
 * Copyright 2023 Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@ExperimentalKotoolsTypesApi
class EmailAddressCompanionTest {
    @Test
    fun regex_should_pass() {
        val actual: Regex = EmailAddress.regex
        val expected = Regex("^\\S+@\\S+\\.\\S+\$")
        assertEquals(expected.pattern, actual.pattern)
    }

    @Test
    fun from_should_pass_with_a_valid_CharSequence() {
        val text: CharSequence = "contact@kotools.org"
        val actual: EmailAddress? = EmailAddress from text
        assertNotNull(actual)
    }

    @Test
    fun from_should_fail_with_a_CharSequence_having_an_invalid_local_part() {
        val text: CharSequence = " contact@kotools.org"
        val actual: EmailAddress? = EmailAddress from text
        assertNull(actual)
    }

    @Test
    fun from_should_fail_with_a_CharSequence_that_does_not_have_an_at_sign() {
        val text: CharSequence = "contact-kotools.org"
        val actual: EmailAddress? = EmailAddress from text
        assertNull(actual)
    }

    @Test
    fun from_should_fail_with_a_CharSequence_having_an_invalid_domain() {
        val text: CharSequence = "contact@ko tools. org"
        val actual: EmailAddress? = EmailAddress from text
        assertNull(actual)
    }

    @Test
    fun from_should_fail_with_a_CharSequence_that_does_not_have_a_dot() {
        val text: CharSequence = "contact@kotools_org"
        val actual: EmailAddress? = EmailAddress from text
        assertNull(actual)
    }
}

@ExperimentalKotoolsTypesApi
class EmailAddressTest {
    @Test
    fun structural_equality_should_pass_with_the_same_object() {
        val first: EmailAddress =
            checkNotNull(EmailAddress from "contact@kotools.org")
        val second: EmailAddress = first
        assertEquals(first, second)
        assertEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun structural_equality_should_pass_with_another_EmailAddress_having_the_same_string_representation() {
        val text = "contact@kotools.org"
        val first: EmailAddress = checkNotNull(EmailAddress from text)
        val second: EmailAddress = checkNotNull(EmailAddress from text)
        assertEquals(first, second)
        assertEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun structural_equality_should_fail_with_null() {
        val first: EmailAddress =
            checkNotNull(EmailAddress from "contact@kotools.org")
        val second: Any? = null
        assertNotEquals(first, second)
    }

    @Test
    fun structural_equality_should_fail_with_another_object_that_is_not_an_EmailAddress() {
        val text = "contact@kotools.org"
        val first: EmailAddress = checkNotNull(EmailAddress from text)
        val second: Any = text
        assertNotEquals(first, second)
        assertNotEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun structural_equality_should_fail_with_another_EmailAddress_having_another_string_representation() {
        val first: EmailAddress =
            checkNotNull(EmailAddress from "contact-1@kotools.org")
        val second: EmailAddress =
            checkNotNull(EmailAddress from "contact-2@kotools.org")
        assertNotEquals(first, second)
        assertNotEquals(first.hashCode(), second.hashCode())
    }

    @Test
    fun toString_should_pass() {
        val text = "contact@kotools.org"
        val emailAddress: EmailAddress = checkNotNull(EmailAddress from text)
        assertEquals(expected = text, actual = "$emailAddress")
    }
}
