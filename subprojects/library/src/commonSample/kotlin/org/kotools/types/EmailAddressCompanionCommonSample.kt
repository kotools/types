package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressCompanionCommonSample {
    @Test
    fun patternSample() {
        val actual: String = EmailAddress.PATTERN
        val expected = "^\\S+@\\S+\\.\\S+\$"
        assertEquals(expected, actual)
    }

    @Test
    fun fromStringAny() {
        val text: Any = "contact@kotools.org"
        val isSuccess: Boolean = try {
            EmailAddress.fromString(text)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @Test
    fun fromStringAnyAny() {
        val text: Any = "contact@kotools.org"
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val isSuccess: Boolean = try {
            EmailAddress.fromString(text, pattern)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @Test
    fun fromStringOrNullAny() {
        val text: Any = "contact@kotools.org"
        val actual: EmailAddress? = EmailAddress.fromStringOrNull(text)
        assertNotNull(actual)
    }

    @Test
    fun fromStringOrNullAnyAny() {
        val value: Any = "contact@kotools.org"
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val actual: EmailAddress? =
            EmailAddress.fromStringOrNull(value, pattern)
        assertNotNull(actual)
    }
}
