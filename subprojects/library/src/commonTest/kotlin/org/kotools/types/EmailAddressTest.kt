package org.kotools.types

import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.ExceptionMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.fail

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressTest {
    // ----------------------------- equals(Any?) ------------------------------

    @Test
    fun equalsFailsWithAnotherTypeThanEmailAddress() {
        val text = "contact@kotools.org"
        val first: EmailAddress = EmailAddress.of(text) ?: fail()
        val second: Any = text
        val actual: Boolean = first.equals(second)
        assertFalse(actual)
    }

    @Test
    fun equalsFailsWithEmailAddressHavingAnotherStringRepresentation() {
        val first: EmailAddress = EmailAddress.of("contact@kotools.org")
            ?: fail()
        val second: Any = EmailAddress.of("second@kotools.org") ?: fail()
        val actual: Boolean = first.equals(second)
        assertFalse(actual)
    }

    // ------------------------------ hashCode() -------------------------------

    @Test
    fun hashCodeUsesOriginalText() {
        val text = "contact@kotools.org"
        val actual: Int = EmailAddress.of(text)
            ?.hashCode()
            ?: fail()
        val expected: Int = hashCodeOf(text)
        assertEquals(expected, actual)
    }

    // ------------------------- Companion.of(String) --------------------------

    @Test
    fun ofPassesWithTextMatchingDefaultRegex() {
        assertNotNull(EmailAddress of "contact@kotools.org")
    }

    @Test
    fun ofFailsWithTextNotHavingAtSign(): Unit =
        assertNull(EmailAddress of "contactKotools.org")

    @Test
    fun ofFailsWithTextNotHavingDotInDomain(): Unit =
        assertNull(EmailAddress of "contact@kotoolsOrg")

    @Test
    fun ofFailsWithTextHavingWhitespacesInLocalPart(): Unit =
        assertNull(EmailAddress of " cont  act @kotools.org")

    @Test
    fun ofFailsWithTextHavingWhitespacesInFirstLabelOfDomain(): Unit =
        assertNull(EmailAddress of "contact@ ko tools .org")

    @Test
    fun ofFailsWithTextHavingWhitespacesInSecondLabelOfDomain(): Unit =
        assertNull(EmailAddress of "contact@kotools. or  g ")

    // ---------------- Companion.of(String, EmailAddressRegex) ----------------

    @Test
    fun ofPassesWithTextMatchingRegex() {
        val text = "contact@kotools.org"
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        val actual: EmailAddress? = EmailAddress.of(text, regex)
        assertNotNull(actual)
    }

    @Test
    fun ofFailsWithTextNotMatchingRegex() {
        val text = "contact-2@kotools.org"
        val regex: EmailAddressRegex = EmailAddressRegex.alphanumeric()
        val actual: EmailAddress? = EmailAddress.of(text, regex)
        assertNull(actual)
    }

    // ------------- Companion.orThrow(String, EmailAddressRegex) --------------

    @Test
    fun orThrowStringEmailAddressRegexFailsWithTextNotMatchingRegex() {
        val text = "invalid-contact@kotools.org"
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.orThrow(text, regex)
        }
        val actual: ExceptionMessage = ExceptionMessage.from(exception)
        val expected: ExceptionMessage =
            ExceptionMessage.invalidEmailAddress(text)
        assertEquals(expected, actual)
    }
}
