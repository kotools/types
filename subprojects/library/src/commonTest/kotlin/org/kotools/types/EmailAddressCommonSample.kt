package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressCommonSample {
    @Test
    fun equalsOverride() {
        val text = "contact@kotools.org"
        val first: EmailAddress = EmailAddress.orThrow(text)
        val second: EmailAddress = EmailAddress.orThrow(text)
        val result: Boolean = first == second // or first.equals(second)
        assertTrue(result)
    }

    @Test
    fun hashCodeOverride() {
        val text = "contact@kotools.org"
        val hashCode: Int = EmailAddress.orThrow(text)
            .hashCode()
        val other: Int = EmailAddress.orThrow(text)
            .hashCode()
        assertEquals(hashCode, other)
    }

    @Test
    fun toStringOverride() {
        val text = "contact@kotools.org"
        val emailAddress: EmailAddress = EmailAddress.orThrow(text)
        val result = "$emailAddress" // or emailAddress.toString()
        assertEquals(expected = text, result)
    }

    // ------------------------------- Companion -------------------------------

    @Test
    fun ofText() {
        assertNotNull(EmailAddress of "contact@kotools.org")
    }

    @Test
    fun ofTextRegex() {
        val text = "contact@kotools.org"
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        val result: EmailAddress? = EmailAddress.of(text, regex)
        assertNotNull(result)
    }

    @Test
    fun orThrowString() {
        val text = "contact@kotools.org"
        val emailAddress: EmailAddress = EmailAddress.orThrow(text)
        assertEquals(expected = text, "$emailAddress")
    }

    @Test
    fun orThrowStringEmailAddressRegex() {
        val text = "contact@kotools.org"
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        val emailAddress: EmailAddress = EmailAddress.orThrow(text, regex)
        assertEquals(expected = text, "$emailAddress")
    }
}
