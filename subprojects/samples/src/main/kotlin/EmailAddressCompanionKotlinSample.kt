package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object EmailAddressCompanionKotlinSample {
    fun patternSample() {
        val pattern: String = EmailAddress.PATTERN
        println(pattern) // ^\S+@\S+\.\S+$
    } // END

    fun fromStringSample() {
        val value: Any = "contact@kotools.org"
        val result: Result<EmailAddress> = kotlin.runCatching {
            EmailAddress.fromString(value) // TABS: 1
        }
        println(result.isSuccess) // true
    } // END

    fun fromStringOrNullSample() {
        val value: Any = "contact@kotools.org"
        val address: EmailAddress? = EmailAddress.fromStringOrNull(value)
        println(address != null) // true
    } // END

    @Suppress("FunctionName")
    fun fromStringOrNull_Any_Any_Sample() {
        val value: Any = "contact@kotools.org"
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val address: EmailAddress? =
            EmailAddress.fromStringOrNull(value, pattern) // TABS: 1
        println(address != null) // true
    } // END
}
