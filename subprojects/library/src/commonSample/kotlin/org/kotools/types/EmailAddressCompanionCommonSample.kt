package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressCompanionCommonSample {
    @Test
    fun patternSample() {
        val pattern: String = EmailAddress.PATTERN
        check(pattern == "^\\S+@\\S+\\.\\S+\$")
    }

    @Test
    fun fromStringAny() {
        val value: Any = "contact@kotools.org"
        val isSuccess: Boolean = try {
            EmailAddress.fromString(value)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        check(isSuccess)
    }

    @Test
    fun fromStringAnyAny() {
        val value: Any = "contact@kotools.org"
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val isSuccess: Boolean = try {
            EmailAddress.fromString(value, pattern)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        check(isSuccess)
    }

    @Test
    fun fromStringOrNullAny() {
        val value: Any = "contact@kotools.org"
        val address: EmailAddress? = EmailAddress.fromStringOrNull(value)
        checkNotNull(address)
    }

    @Test
    fun fromStringOrNullAnyAny() {
        val value: Any = "contact@kotools.org"
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val address: EmailAddress? =
            EmailAddress.fromStringOrNull(value, pattern)
        checkNotNull(address)
    }

    @Test
    fun orNullAny() {
        val value: Any = "contact@kotools.org"
        val address: EmailAddress? = EmailAddress.orNull(value)
        checkNotNull(address)
    }
}
