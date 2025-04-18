package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressCompanionCommonSample {
    @Test
    fun patternSample() {
        val actual: String = EmailAddress.PATTERN
        val expected = "^\\S+@\\S+\\.\\S+\$"
        assertEquals(expected, actual)
    }

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
    fun orNullStringString() {
        val text = "contact@kotools.org"
        val pattern = """^[a-z]+@[a-z]+\.[a-z]+$"""
        val emailAddress: EmailAddress? = EmailAddress.orNull(text, pattern)
        assertNotNull(emailAddress)
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
    fun orThrowStringString() {
        val text = "contact@kotools.org"
        val pattern = """^[a-z]+@[a-z]+\.[a-z]+$"""
        val isSuccess: Boolean = try {
            EmailAddress.orThrow(text, pattern)
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
