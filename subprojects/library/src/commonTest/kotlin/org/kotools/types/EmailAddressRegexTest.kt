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
class EmailAddressRegexTest {
    // ----------------------------- equals(Any?) ------------------------------

    @Test
    fun equalsFailsWithNull() {
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        val other: Any? = null
        val actual: Boolean = regex.equals(other)
        assertFalse(actual)
    }

    @Test
    fun equalsFailsWithAnotherTypeThanEmailAddressRegex() {
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        val other = "oops"
        val actual: Boolean = regex.equals(other)
        assertFalse(actual)
    }

    @Test
    fun equalsFailsWithEmailAddressRegexHavingAnotherPattern() {
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        val other: EmailAddressRegex = EmailAddressRegex.alphabetic()
        val actual: Boolean = regex.equals(other)
        assertFalse(actual)
    }

    // ------------------------------ hashCode() -------------------------------

    @Test
    fun hashCodeUsesStringRepresentation() {
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        val actual: Int = regex.hashCode()
        val expected: Int = hashCodeOf("$regex")
        assertEquals(expected, actual)
    }

    // ------------------------- matches(CharSequence) -------------------------

    @Test
    fun matchesFailsWithTextNotMatchingRegularExpression() {
        val text = "invalid-contact@kotools.org"
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        assertFalse(regex matches text)
    }

    // ------------------------- Companion.of(String) --------------------------

    @Test
    fun ofPassesWithPatternMatchingDefaultRegex() {
        assertNotNull(EmailAddressRegex of """^[a-z]+@[a-z]+\.[a-z]+$""")
    }

    @Test
    fun ofFailsWithPatternNotMatchingDefaultRegex(): Unit =
        assertNull(EmailAddressRegex of """^[a-z]+\.[a-z]+$""")

    // ----------------------- Companion.orThrow(String) -----------------------

    @Test
    fun orThrowFailsWithPatternNotMatchingDefaultOne() {
        val pattern = """^[a-z]+\.[a-z]+$"""
        val exception: IllegalArgumentException = assertFailsWith {
            EmailAddressRegex.orThrow(pattern)
        }
        val actual: ExceptionMessage = ExceptionMessage.from(exception)
        val expected: ExceptionMessage =
            ExceptionMessage.invalidEmailAddressPattern(pattern)
        assertEquals(expected, actual)
    }
}
