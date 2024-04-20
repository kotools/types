package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object EmailAddressCompanionKotlinSample {
    fun patternSample() {
        val pattern: String = EmailAddress.PATTERN
        println(pattern) // ^\S+@\S+\.\S+$
    } // END

    fun qualifiedName() {
        val qualifiedName: String = EmailAddress.qualifiedName
        println(qualifiedName) // org.kotools.types.EmailAddress
    } // END

    @Suppress("FunctionName")
    fun fromString_Any() {
        val value: Any = "contact@kotools.org"
        val isSuccess: Boolean = try {
            EmailAddress.fromString(value) // TABS: 1
            true // TABS: 1
        } catch (exception: IllegalArgumentException) {
            false // TABS: 1
        }
        println(isSuccess) // true
    } // END

    @Suppress("FunctionName")
    fun fromString_Any_Any() {
        val value: Any = "contact@kotools.org"
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val isSuccess: Boolean = try {
            EmailAddress.fromString(value, pattern) // TABS: 1
            true // TABS: 1
        } catch (exception: IllegalArgumentException) {
            false // TABS: 1
        }
        println(isSuccess) // true
    } // END

    @Suppress("FunctionName")
    fun fromStringOrNull_Any() {
        val value: Any = "contact@kotools.org"
        val address: EmailAddress? = EmailAddress.fromStringOrNull(value)
        println(address != null) // true
    } // END

    @Suppress("FunctionName")
    fun fromStringOrNull_Any_Any() {
        val value: Any = "contact@kotools.org"
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val address: EmailAddress? =
            EmailAddress.fromStringOrNull(value, pattern) // TABS: 1
        println(address != null) // true
    } // END
}
