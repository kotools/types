package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressCommonSample {
    @Test
    fun equalsOverride() {
        val value: Any = "contact@kotools.org"
        val first: EmailAddress = EmailAddress.fromString(value)
        val second: EmailAddress = EmailAddress.fromString(value)
        val actual: Boolean = first == second // or first.equals(second)
        assertTrue(actual)
    }

    @Test
    fun hashCodeOverride() {
        val value: Any = "contact@kotools.org"
        val first: EmailAddress = EmailAddress.fromString(value)
        val second: EmailAddress = EmailAddress.fromString(value)
        val actual: Boolean = first.hashCode() == second.hashCode()
        assertTrue(actual)
    }

    @Test
    fun toStringOverride() {
        val value: Any = "contact@kotools.org"
        val address: EmailAddress = EmailAddress.fromString(value)
        val actual = "$address" // or address.toString()
        assertEquals(expected = value, actual)
    }
}
