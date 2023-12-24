/*
 * Copyright 2023 Kotools S.A.S.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@ExperimentalKotoolsTypesApi
class EmailAddressTest {
    @Test
    fun toString_should_pass() {
        val text = "contact@kotools.org"
        val emailAddress: EmailAddress = checkNotNull(EmailAddress from text)
        assertEquals(expected = text, actual = "$emailAddress")
    }
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
