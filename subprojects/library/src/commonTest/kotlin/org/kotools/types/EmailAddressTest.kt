package org.kotools.types

import kotools.types.internal.hashCodeOf
import kotools.types.internal.simpleNameOf
import org.kotools.types.internal.ExceptionMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
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
}

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressCompanionTest {
    @Test
    fun orNullStringShouldPassWithValidText() {
        val text: String = Values.VALID
        val actual: EmailAddress? = EmailAddress.orNull(text)
        val message: String = simpleNameOf<EmailAddress>()
            .let { "Creating an instance of $it with '$text' should pass." }
        assertNotNull(actual, message)
    }

    @Test
    fun orNullStringShouldFailWithMissingAtSign(): Unit =
        this.orNullShouldFailWith(Values.MISSING_AT_SIGN)

    @Test
    fun orNullStringShouldFailWithMissingDotInDomain(): Unit =
        this.orNullShouldFailWith(Values.MISSING_DOMAIN_DOT)

    @Test
    fun orNullStringShouldFailWithWhitespacesInLocalPart(): Unit =
        this.orNullShouldFailWith(Values.WHITESPACES_IN_LOCAL_PART)

    @Test
    fun orNullStringShouldFailWithWhitespacesInDomainFirstLabel(): Unit =
        this.orNullShouldFailWith(Values.WHITESPACES_IN_DOMAIN_FIRST_LABEL)

    @Test
    fun orNullStringShouldFailWithWhitespacesInDomainSecondLabel(): Unit =
        this.orNullShouldFailWith(Values.WHITESPACES_IN_DOMAIN_SECOND_LABEL)

    private fun orNullShouldFailWith(text: String) {
        val actual: EmailAddress? = EmailAddress.orNull(text)
        val message: String = simpleNameOf<EmailAddress>()
            .let { "Creating an instance of $it with '$text' should fail." }
        assertNull(actual, message)
    }

    @Test
    fun orNullStringEmailAddressRegexShouldPassWithValidText() {
        val text: String = Values.VALID
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        val actual: EmailAddress? = EmailAddress.orNull(text, regex)
        val message =
            "'$text' matches the following regular expression: '$regex'."
        assertNotNull(actual, message)
    }

    @Test
    fun orNullStringEmailAddressRegexShouldFailWithInvalidText() {
        val text = "first-contact@kotools.org"
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        val actual: EmailAddress? = EmailAddress.orNull(text, regex)
        val message =
            "'$text' doesn't match the following regular expression: '$regex'."
        assertNull(actual, message)
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
        val throwable: IllegalArgumentException =
            assertFailsWith { EmailAddress.orThrow(text) }
        val actual: ExceptionMessage = ExceptionMessage.from(throwable)
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
        val throwable: IllegalArgumentException = assertFailsWith {
            EmailAddress.orThrow(text, regex)
        }
        val actual: ExceptionMessage = ExceptionMessage.from(throwable)
        val expected: ExceptionMessage =
            ExceptionMessage.invalidEmailAddress(text)
        assertEquals(expected, actual)
    }
}
