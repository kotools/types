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
    fun fromStringOrNullAnyAny() {
        val text: Any = "contact@kotools.org"
        val pattern: Any = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val actual: EmailAddress? = EmailAddress.fromStringOrNull(text, pattern)
        assertNotNull(actual)
    }

    @Test
    fun orNullString() {
        val text = "contact@kotools.org"
        val emailAddress: EmailAddress? = EmailAddress.orNull(text)
        assertNotNull(emailAddress)
    }

    @Test
    fun orNullStringString() {
        val text = "contact@kotools.org"
        val pattern = """^[a-z]+@[a-z]+\.[a-z]+$"""
        val emailAddress: EmailAddress? = EmailAddress.orNull(text, pattern)
        assertNotNull(emailAddress)
    }

    @Test
    fun orThrowString() {
        val text = "contact@kotools.org"
        val isSuccess: Boolean = try {
            EmailAddress.orThrow(text)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @Test
    fun orThrowStringString() {
        val text = "contact@kotools.org"
        val pattern = """^[a-z]+@[a-z]+\.[a-z]+$"""
        val isSuccess: Boolean = try {
            EmailAddress.orThrow(text, pattern)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }
}
