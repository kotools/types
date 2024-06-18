package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.Warning

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object EmailAddressCompanionKotlinSample {
    fun patternSample() {
        val pattern: String = EmailAddress.PATTERN
        println(pattern) // ^\S+@\S+\.\S+$
    } // END

    @Suppress(Warning.FUNCTION_NAME)
    fun fromString_Any() {
        val value: Any = "contact@kotools.org"
        val isSuccess: Boolean = try {
            EmailAddress.fromString(value)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        println(isSuccess) // true
    } // END

    @Suppress(Warning.FUNCTION_NAME)
    fun fromString_Any_Any() {
        val value: Any = "contact@kotools.org"
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val isSuccess: Boolean = try {
            EmailAddress.fromString(value, pattern)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        println(isSuccess) // true
    } // END

    @Suppress(Warning.FUNCTION_NAME)
    fun fromStringOrNull_Any() {
        val value: Any = "contact@kotools.org"
        val address: EmailAddress? = EmailAddress.fromStringOrNull(value)
        println(address != null) // true
    } // END

    @Suppress(Warning.FUNCTION_NAME)
    fun fromStringOrNull_Any_Any() {
        val value: Any = "contact@kotools.org"
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val address: EmailAddress? =
            EmailAddress.fromStringOrNull(value, pattern)
        println(address != null) // true
    } // END

    @Suppress(Warning.FUNCTION_NAME)
    fun orNull_Any() {
        val value: Any = "contact@kotools.org"
        val address: EmailAddress? = EmailAddress.orNull(value)
        println(address != null) // true
    } // END
}
