package org.kotools.types

import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.ExceptionMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

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

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressTest {
    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsShouldPassWithEmailAddressHavingSameStringRepresentation() {
        val text: String = Values.VALID
        val first: EmailAddress = EmailAddress.orThrow(text)
        val second: Any = EmailAddress.orThrow(text)
        val actual: Boolean = first.equals(second)
        assertTrue(actual)
    }

    @Test
    fun equalsShouldFailWithAnotherTypeThanEmailAddress() {
        val text: String = Values.VALID
        val first: EmailAddress = EmailAddress.orThrow(text)
        val second: Any = text
        val actual: Boolean = first.equals(second)
        assertFalse(actual)
    }

    @Test
    fun equalsShouldFailWithEmailAddressHavingAnotherStringRepresentation() {
        val first: EmailAddress = EmailAddress.orThrow(Values.VALID)
        val second: Any = EmailAddress.orThrow("second@kotools.org")
        val actual: Boolean = first.equals(second)
        assertFalse(actual)
    }

    @Test
    fun hashCodeShouldPass() {
        val text = "contact@kotools.org"
        val actual: Int = EmailAddress.orThrow(text)
            .hashCode()
        val expected: Int = hashCodeOf(text)
        assertEquals(expected, actual)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringShouldPass() {
        val text: String = Values.VALID
        val address: EmailAddress = EmailAddress.orThrow(text)
        val actual: String = address.toString()
        assertEquals(expected = text, actual)
    }

    // ------------------------------- Companion -------------------------------

    @Test
    fun orNullStringFailsWithMissingAtSign() {
        val actual: EmailAddress? = EmailAddress.orNull(Values.MISSING_AT_SIGN)
        assertNull(actual)
    }

    @Test
    fun orNullStringFailsWithMissingDotInDomain() {
        val actual: EmailAddress? =
            EmailAddress.orNull(Values.MISSING_DOMAIN_DOT)
        assertNull(actual)
    }

    @Test
    fun orNullStringFailsWithWhitespacesInLocalPart() {
        val actual: EmailAddress? =
            EmailAddress.orNull(Values.WHITESPACES_IN_LOCAL_PART)
        assertNull(actual)
    }

    @Test
    fun orNullStringFailsWithWhitespacesInDomainFirstLabel() {
        val actual: EmailAddress? =
            EmailAddress.orNull(Values.WHITESPACES_IN_DOMAIN_FIRST_LABEL)
        assertNull(actual)
    }

    @Test
    fun orNullStringFailsWithWhitespacesInDomainSecondLabel() {
        val actual: EmailAddress? =
            EmailAddress.orNull(Values.WHITESPACES_IN_DOMAIN_SECOND_LABEL)
        assertNull(actual)
    }

    @Test
    fun orNullStringEmailAddressRegexFailsWithTextNotMatchingRegex() {
        val text = "first-contact@kotools.org"
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        val actual: EmailAddress? = EmailAddress.orNull(text, regex)
        assertNull(actual)
    }

    @Test
    fun orThrowStringShouldPassWithValidText() {
        EmailAddress.orThrow(text = Values.VALID)
    }

    @Test
    fun orThrowStringShouldFailWithMissingAtSign(): Unit =
        this.orThrowStringShouldFailWith(Values.MISSING_AT_SIGN)

    @Test
    fun orThrowStringShouldFailWithMissingDotInDomain(): Unit =
        this.orThrowStringShouldFailWith(Values.MISSING_DOMAIN_DOT)

    @Test
    fun orThrowStringShouldFailWithWhitespacesInLocalPart(): Unit =
        this.orThrowStringShouldFailWith(Values.WHITESPACES_IN_LOCAL_PART)

    @Test
    fun orThrowStringShouldFailWithWhitespacesInDomainFirstLabel(): Unit = this
        .orThrowStringShouldFailWith(Values.WHITESPACES_IN_DOMAIN_FIRST_LABEL)

    @Test
    fun orThrowStringShouldFailWithWhitespacesInDomainSecondLabel(): Unit = this
        .orThrowStringShouldFailWith(Values.WHITESPACES_IN_DOMAIN_SECOND_LABEL)

    private fun orThrowStringShouldFailWith(text: String) {
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.orThrow(text)
        }
        val actual: ExceptionMessage = ExceptionMessage.from(exception)
        val expected: ExceptionMessage =
            ExceptionMessage.invalidEmailAddress(text)
        assertEquals(expected, actual)
    }

    @Test
    fun orThrowStringEmailAddressRegexShouldPassWithValidText() {
        val text = "contact@kotools.org"
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        EmailAddress.orThrow(text, regex)
    }

    @Test
    fun orThrowStringEmailAddressRegexShouldFailWithInvalidText() {
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
