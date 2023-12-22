/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.experimental

import kotools.types.internal.unexpectedCreationFailure
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@ExperimentalKotoolsTypesApi
class EmailAddressTest {
    @Test
    fun toString_should_pass() {
        val text = "contact@kotools.org"
        val emailAddress: EmailAddress = EmailAddress.from(text)
            ?: unexpectedCreationFailure<EmailAddress>(value = text)
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
    fun from_should_pass_with_valid_String() {
        val actual: EmailAddress? = EmailAddress from "contact@kotools.org"
        assertNotNull(actual)
    }

    @Test
    fun from_should_fail_with_a_String_having_an_invalid_local_part() {
        val actual: EmailAddress? = EmailAddress from " contact@kotools.org"
        assertNull(actual)
    }

    @Test
    fun from_should_fail_with_a_String_that_does_not_have_an_at_sign() {
        val actual: EmailAddress? = EmailAddress from "contact-kotools.org"
        assertNull(actual)
    }

    @Test
    fun from_should_fail_with_a_String_having_an_invalid_domain() {
        val actual: EmailAddress? = EmailAddress from "contact@ko tools. org"
        assertNull(actual)
    }

    @Test
    fun from_should_fail_with_a_String_that_does_not_have_a_dot() {
        val actual: EmailAddress? = EmailAddress from "contact@kotools_org"
        assertNull(actual)
    }
}
