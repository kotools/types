package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object EmailAddressKotlinSample {
    @Suppress("FunctionName")
    fun equals_override() {
        val value: Any = "contact@kotools.org"
        val first: EmailAddress = EmailAddress.fromString(value)
        val second: EmailAddress = EmailAddress.fromString(value)
        val result: Boolean = first == second // or first.equals(second)
        println(result) // true
    } // END

    @Suppress("FunctionName")
    fun hashCode_override() {
        val value: Any = "contact@kotools.org"
        val first: EmailAddress = EmailAddress.fromString(value)
        val second: EmailAddress = EmailAddress.fromString(value)
        val result: Boolean = first.hashCode() == second.hashCode()
        println(result) // true
    } // END

    @Suppress("FunctionName")
    fun toString_override() {
        val value: Any = "contact@kotools.org"
        val address: EmailAddress = EmailAddress.fromString(value)
        val addressAsString: String = address.toString()
        println(addressAsString == value) // true
    } // END
}
