package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressCompanionCommonSample {
    @Test
    fun orNullString() {
        val text = "contact@kotools.org"
        val emailAddress: EmailAddress? = EmailAddress.orNull(text)
        assertNotNull(emailAddress)
    }

    @Test
    fun orNullStringEmailAddressRegex() {
        val text = "contact@kotools.org"
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        val emailAddress: EmailAddress? = EmailAddress.orNull(text, regex)
        val message =
            "'$text' matches the following regular expression: '$regex'."
        assertNotNull(emailAddress, message)
    }

    @Test
    fun orThrowString() {
        val text = "contact@kotools.org"
        val isSuccess: Boolean = try {
            EmailAddress.orThrow(text)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @Test
    fun orThrowStringEmailAddressRegex() {
        val text = "contact@kotools.org"
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        EmailAddress.orThrow(text, regex)
    }
}
