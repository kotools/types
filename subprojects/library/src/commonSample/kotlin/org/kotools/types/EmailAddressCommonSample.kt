package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressCommonSample {
    @Test
    fun equalsOverride() {
        val value: Any = "contact@kotools.org"
        val first: EmailAddress = EmailAddress.fromString(value)
        val second: EmailAddress = EmailAddress.fromString(value)
        val result: Boolean = first == second // or first.equals(second)
        check(result)
    }

    @Test
    fun hashCodeOverride() {
        val value: Any = "contact@kotools.org"
        val first: EmailAddress = EmailAddress.fromString(value)
        val second: EmailAddress = EmailAddress.fromString(value)
        val result: Boolean = first.hashCode() == second.hashCode()
        check(result)
    }

    @Test
    fun toStringOverride() {
        val value: Any = "contact@kotools.org"
        val address: EmailAddress = EmailAddress.fromString(value)
        val addressAsString: String = address.toString()
        check(addressAsString == value)
    }
}
