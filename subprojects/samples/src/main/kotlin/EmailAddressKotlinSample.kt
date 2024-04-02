package org.kotools.types

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.experimental.ExperimentalKotoolsTypesApi

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object EmailAddressKotlinSample {
    fun serialization() {
        val address: EmailAddress =
            EmailAddress.fromString("contact@kotools.org") // TABS: 1
        val encoded: String = Json.encodeToString(address)
        println(encoded) // "contact@kotools.org"
        val decoded: EmailAddress = Json.decodeFromString(encoded)
        println(decoded == address) // true
    } // END

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
