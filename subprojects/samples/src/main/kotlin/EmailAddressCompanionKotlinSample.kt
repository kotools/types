package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object EmailAddressCompanionKotlinSample {
    fun patternSample() {
        val pattern: String = EmailAddress.PATTERN
        println(pattern) // ^\S+@\S+\.\S+$
    } // END

    @Suppress("FunctionName")
    fun fromString_Any() {
        val value: Any = "contact@kotools.org"
        val result: Result<EmailAddress> = kotlin.runCatching {
            EmailAddress.fromString(value) // TABS: 1
        }
        println(result.isSuccess) // true
    } // END

    @Suppress("FunctionName")
    fun fromString_Any_Any() {
        val value: Any = "contact@kotools.org"
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val result: Result<EmailAddress> = kotlin.runCatching {
            EmailAddress.fromString(value, pattern) // TABS: 1
        }
        println(result.isSuccess) // true
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

    @Suppress("FunctionName")
    fun orNull_String() {
        val address: EmailAddress? = EmailAddress.orNull("contact@kotools.org")
        println(address != null) // true
    } // END
}
