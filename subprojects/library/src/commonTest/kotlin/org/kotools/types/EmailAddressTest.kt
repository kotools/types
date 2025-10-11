package org.kotools.types

import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.ExceptionMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressTest {
    // ----------------------------- equals(Any?) ------------------------------

    @Test
    fun equalsFailsWithAnotherTypeThanEmailAddress() {
        val text: String = Values.VALID
        val first: EmailAddress = EmailAddress.orThrow(text)
        val second: Any = text
        val actual: Boolean = first.equals(second)
        assertFalse(actual)
    }

    @Test
    fun equalsFailsWithEmailAddressHavingAnotherStringRepresentation() {
        val first: EmailAddress = EmailAddress.orThrow(Values.VALID)
        val second: Any = EmailAddress.orThrow("second@kotools.org")
        val actual: Boolean = first.equals(second)
        assertFalse(actual)
    }

    // ------------------------------ hashCode() -------------------------------

    @Test
    fun hashCodeUsesOriginalText() {
        val text = "contact@kotools.org"
        val actual: Int = EmailAddress.orThrow(text)
            .hashCode()
        val expected: Int = hashCodeOf(text)
        assertEquals(expected, actual)
    }

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

    // ----------------------- Companion.orNull(String) ------------------------

    @Test
    fun orNullStringFailsWithMissingAtSign(): Unit =
        this.orNullStringFailsWith(Values.MISSING_AT_SIGN)

    @Test
    fun orNullStringFailsWithMissingDotInDomain(): Unit =
        this.orNullStringFailsWith(Values.MISSING_DOMAIN_DOT)

    @Test
    fun orNullStringFailsWithWhitespacesInLocalPart(): Unit =
        this.orNullStringFailsWith(Values.WHITESPACES_IN_LOCAL_PART)

    @Test
    fun orNullStringFailsWithWhitespacesInDomainFirstLabel(): Unit =
        this.orNullStringFailsWith(Values.WHITESPACES_IN_DOMAIN_FIRST_LABEL)

    @Test
    fun orNullStringFailsWithWhitespacesInDomainSecondLabel(): Unit =
        this.orNullStringFailsWith(Values.WHITESPACES_IN_DOMAIN_SECOND_LABEL)

    private fun orNullStringFailsWith(text: String) {
        val actual: EmailAddress? = EmailAddress.orNull(text)
        assertNull(actual)
    }

    // -------------- Companion.orNull(String, EmailAddressRegex) --------------

    @Test
    fun orNullStringEmailAddressRegexFailsWithTextNotMatchingRegex() {
        val text = "first-contact@kotools.org"
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        val actual: EmailAddress? = EmailAddress.orNull(text, regex)
        assertNull(actual)
    }

    // ----------------------- Companion.orThrow(String) -----------------------

    @Test
    fun orThrowStringFailsWithMissingAtSign(): Unit =
        this.orThrowStringFailsWith(Values.MISSING_AT_SIGN)

    @Test
    fun orThrowStringFailsWithMissingDotInDomain(): Unit =
        this.orThrowStringFailsWith(Values.MISSING_DOMAIN_DOT)

    @Test
    fun orThrowStringFailsWithWhitespacesInLocalPart(): Unit =
        this.orThrowStringFailsWith(Values.WHITESPACES_IN_LOCAL_PART)

    @Test
    fun orThrowStringFailsWithWhitespacesInDomainFirstLabel(): Unit =
        this.orThrowStringFailsWith(Values.WHITESPACES_IN_DOMAIN_FIRST_LABEL)

    @Test
    fun orThrowStringFailsWithWhitespacesInDomainSecondLabel(): Unit =
        this.orThrowStringFailsWith(Values.WHITESPACES_IN_DOMAIN_SECOND_LABEL)

    private fun orThrowStringFailsWith(text: String) {
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.orThrow(text)
        }
        val actual: ExceptionMessage = ExceptionMessage.from(exception)
        val expected: ExceptionMessage =
            ExceptionMessage.invalidEmailAddress(text)
        assertEquals(expected, actual)
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

private object Values {
    const val VALID: String = "contact@kotools.org"
    const val MISSING_AT_SIGN: String = "contactKotools.org"
    const val MISSING_DOMAIN_DOT: String = "contact@kotoolsOrg"
    const val WHITESPACES_IN_LOCAL_PART: String = " cont  act @kotools.org"
    const val WHITESPACES_IN_DOMAIN_FIRST_LABEL: String =
        "contact@ ko tools .org"
    const val WHITESPACES_IN_DOMAIN_SECOND_LABEL: String =
        "contact@kotools. or  g "
}
