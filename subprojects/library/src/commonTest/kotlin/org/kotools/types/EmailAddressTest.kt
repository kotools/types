package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.InvalidEmailAddress
import org.kotools.types.internal.InvalidEmailAddressPattern
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
    @Test
    fun equalsShouldPassWithEmailAddressHavingSameStringRepresentation() {
        val value: Any = Values.VALID
        val first: EmailAddress = EmailAddress.fromString(value)
        val second: Any = EmailAddress.fromString(value)
        val actual: Boolean = first.equals(second)
        assertTrue(actual)
    }

    @Test
    fun equalsShouldFailWithAnotherTypeThanEmailAddress() {
        val value: Any = Values.VALID
        val first: EmailAddress = EmailAddress.fromString(value)
        val second: Any = value
        val actual: Boolean = first.equals(second)
        assertFalse(actual)
    }

    @Test
    fun equalsShouldFailWithEmailAddressHavingAnotherStringRepresentation() {
        val first: EmailAddress = EmailAddress.fromString(Values.VALID)
        val second: Any = EmailAddress.fromString("second@kotools.org")
        val actual: Boolean = first.equals(second)
        assertFalse(actual)
    }

    @Test
    fun hashCodeShouldPass() {
        val value = "contact@kotools.org"
        val actual: Int = EmailAddress.fromString(value)
            .hashCode()
        val expected: Int = hashCodeOf(value)
        assertEquals(expected, actual)
    }

    @Test
    fun toStringShouldPass() {
        val value: Any = Values.VALID
        val address: EmailAddress = EmailAddress.fromString(value)
        val actual: String = address.toString()
        assertEquals(expected = value, actual)
    }
}

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressCompanionTest {
    @Test
    fun patternShouldPass() {
        val actual: String = EmailAddress.PATTERN
        val expected = """^\S+@\S+\.\S+$"""
        assertEquals(expected, actual)
    }

    @Test
    fun fromStringAnyShouldPassWithValidValue() {
        val value: Any = Values.VALID
        val actual: Result<EmailAddress> = kotlin.runCatching {
            EmailAddress.fromString(value)
        }
        assertTrue(actual.isSuccess)
    }

    @Test
    fun fromStringAnyShouldFailWithMissingAtSign(): Unit =
        this.fromStringShouldFailWith(Values.MISSING_AT_SIGN)

    @Test
    fun fromStringAnyShouldFailWithMissingDotInDomain(): Unit =
        this.fromStringShouldFailWith(Values.MISSING_DOMAIN_DOT)

    @Test
    fun fromStringAnyShouldFailWithWhitespacesInLocalPart(): Unit =
        this.fromStringShouldFailWith(Values.WHITESPACES_IN_LOCAL_PART)

    @Test
    fun fromStringAnyShouldFailWithWhitespacesInDomainFirstLabel(): Unit =
        this.fromStringShouldFailWith(Values.WHITESPACES_IN_DOMAIN_FIRST_LABEL)

    @Test
    fun fromStringAnyShouldFailWithWhitespacesInDomainSecondLabel(): Unit =
        this.fromStringShouldFailWith(Values.WHITESPACES_IN_DOMAIN_SECOND_LABEL)

    private fun fromStringShouldFailWith(value: Any) {
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value)
        }
        val actual: String? = exception.message
        val valueAsString: String = value.toString()
        val expected: String = InvalidEmailAddress(
            value = valueAsString,
            pattern = EmailAddress.PATTERN
        ).toString()
        assertEquals(expected, actual)
    }

    @Test
    fun fromStringAnyAnyShouldPassWithValidValueAndPattern() {
        val value: Any = Values.VALID
        val pattern: Any = """^[a-z]+@[a-z]+\.[a-z]+$"""
        val result: Result<EmailAddress> = kotlin.runCatching {
            EmailAddress.fromString(value, pattern)
        }
        assertTrue(result.isSuccess)
    }

    @Test
    fun fromStringAnyAnyShouldFailWithInvalidValue() {
        val value = "first-contact@kotools.org"
        val pattern = """^[a-z]+@[a-z]+\.[a-z]+$"""
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value, pattern)
        }
        val actual: String? = exception.message
        val expected: String = InvalidEmailAddress(value, pattern)
            .toString()
        assertEquals(expected, actual)
    }

    @Test
    fun fromStringAnyAnyShouldFailWithInvalidPattern() {
        val value: Any = Values.VALID
        val pattern: Any = """^[a-z]+\.[a-z]+$"""
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddress.fromString(value, pattern)
        }
        val actual: String? = exception.message
        val expected: String = InvalidEmailAddressPattern(
            "$pattern",
            validationPattern = EmailAddress.PATTERN
        ).toString()
        assertEquals(expected, actual)
    }

    @Test
    fun fromStringOrNullAnyShouldPassWithValidValue() {
        val value: Any = Values.VALID
        val actual: EmailAddress? = EmailAddress.fromStringOrNull(value)
        assertNotNull(actual)
    }

    @Test
    fun fromStringOrNullAnyShouldFailWithMissingAtSign() {
        val value: Any = Values.MISSING_AT_SIGN
        this.fromStringOrNullShouldFailWith(value)
    }

    @Test
    fun fromStringOrNullAnyShouldFailWithMissingDotInDomain() {
        val value: Any = Values.MISSING_DOMAIN_DOT
        this.fromStringOrNullShouldFailWith(value)
    }

    @Test
    fun fromStringOrNullAnyShouldFailWithWhitespacesInLocalPart() {
        val value: Any = Values.WHITESPACES_IN_LOCAL_PART
        this.fromStringOrNullShouldFailWith(value)
    }

    @Test
    fun fromStringOrNullAnyShouldFailWithWhitespacesInDomainFirstLabel() {
        val value: Any = Values.WHITESPACES_IN_DOMAIN_FIRST_LABEL
        this.fromStringOrNullShouldFailWith(value)
    }

    @Test
    fun fromStringOrNullAnyShouldFailWithWhitespacesInDomainSecondLabel() {
        val value: Any = Values.WHITESPACES_IN_DOMAIN_SECOND_LABEL
        this.fromStringOrNullShouldFailWith(value)
    }

    private fun fromStringOrNullShouldFailWith(value: Any) {
        val actual: EmailAddress? = EmailAddress.fromStringOrNull(value)
        assertNull(actual)
    }

    @Test
    fun fromStringOrNullAnyAnyShouldPassWithValidValueAndPattern() {
        val value: Any = Values.VALID
        val pattern: Any = """^[a-z]+@[a-z]+\.[a-z]+$"""
        val actual: EmailAddress? =
            EmailAddress.fromStringOrNull(value, pattern)
        assertNotNull(actual)
    }

    @Test
    fun fromStringOrNullAnyAnyShouldFailWithInvalidValue() {
        val value: Any = "first-contact@kotools.org"
        val pattern: Any = """^[a-z]+@[a-z]+\.[a-z]+$"""
        val actual: EmailAddress? =
            EmailAddress.fromStringOrNull(value, pattern)
        assertNull(actual)
    }

    @Test
    fun fromStringOrNullAnyAnyShouldFailWithInvalidPattern() {
        val value: Any = Values.VALID
        val pattern: Any = """^[a-z]+\.[a-z]+$"""
        val actual: EmailAddress? =
            EmailAddress.fromStringOrNull(value, pattern)
        assertNull(actual)
    }

    @Test
    fun orNullAnyShouldPassWithValidValue() {
        val value: Any = Values.VALID
        val actual: EmailAddress? = EmailAddress.orNull(value)
        assertNotNull(actual)
    }

    @Test
    fun orNullAnyShouldFailWithMissingAtSign(): Unit =
        this.orNullShouldFailWith(Values.MISSING_AT_SIGN)

    @Test
    fun orNullAnyShouldFailWithMissingDotInDomain(): Unit =
        this.orNullShouldFailWith(Values.MISSING_DOMAIN_DOT)

    @Test
    fun orNullAnyShouldFailWithWhitespacesInLocalPart(): Unit =
        this.orNullShouldFailWith(Values.WHITESPACES_IN_LOCAL_PART)

    @Test
    fun orNullAnyShouldFailWithWhitespacesInDomainFirstLabel(): Unit =
        this.orNullShouldFailWith(Values.WHITESPACES_IN_DOMAIN_FIRST_LABEL)

    @Test
    fun orNullAnyShouldFailWithWhitespacesInDomainSecondLabel(): Unit =
        this.orNullShouldFailWith(Values.WHITESPACES_IN_DOMAIN_SECOND_LABEL)

    private fun orNullShouldFailWith(value: Any) {
        val actual: EmailAddress? = EmailAddress.orNull(value)
        assertNull(actual)
    }

    @Test
    fun orNullAnyAnyShouldPassWithValidValueAndPattern() {
        val value: Any = Values.VALID
        val pattern: Any = """^[a-z]+@[a-z]+\.[a-z]+$"""
        val actual: EmailAddress? = EmailAddress.orNull(value, pattern)
        assertNotNull(actual)
    }

    @Test
    fun orNullAnyAnyShouldFailWithInvalidValue() {
        val value: Any = "first-contact@kotools.org"
        val pattern: Any = """^[a-z]+@[a-z]+\.[a-z]+$"""
        this.orNullShouldFailWith(value, pattern)
    }

    @Test
    fun orNullAnyAnyShouldFailWithInvalidPattern() {
        val value: Any = Values.VALID
        val pattern: Any = """^[a-z]+\.[a-z]+$"""
        this.orNullShouldFailWith(value, pattern)
    }

    private fun orNullShouldFailWith(value: Any, pattern: Any) {
        val actual: EmailAddress? = EmailAddress.orNull(value, pattern)
        assertNull(actual)
    }
}
