package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressCommonSample {
    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsOverride() {
        val text = "contact@kotools.org"
        val first: EmailAddress = EmailAddress.orThrow(text)
        val second: EmailAddress = EmailAddress.orThrow(text)
        val actual: Boolean = first == second // or first.equals(second)
        assertTrue(actual)
    }

    @Test
    fun hashCodeOverride() {
        val text = "contact@kotools.org"
        val first: EmailAddress = EmailAddress.orThrow(text)
        val second: EmailAddress = EmailAddress.orThrow(text)
        val actual: Boolean = first.hashCode() == second.hashCode()
        assertTrue(actual)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        val text = "contact@kotools.org"
        val address: EmailAddress = EmailAddress.orThrow(text)
        val actual = "$address" // or address.toString()
        assertEquals(expected = text, actual)
    }
}
