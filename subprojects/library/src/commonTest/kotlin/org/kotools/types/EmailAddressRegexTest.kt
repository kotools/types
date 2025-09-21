package org.kotools.types

import kotools.types.internal.hashCodeOf
import kotools.types.internal.simpleNameOf
import org.kotools.types.internal.ExceptionMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressRegexTest {
    @Test
    fun equalsShouldPassWithAnotherEmailAddressRegexHavingSamePattern() {
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        val other: EmailAddressRegex = EmailAddressRegex.default()
        val actual: Boolean = regex.equals(other)
        val message = "Regular expressions with the same pattern are equal."
        assertTrue(actual, message)
    }

    @Test
    fun equalsShouldFailWithNull() {
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        val other: Any? = null
        val actual: Boolean = regex.equals(other)
        val message = "Regular expression is never equal to 'null'."
        assertFalse(actual, message)
    }

    @Test
    fun equalsShouldFailWithAnotherTypeThanEmailAddressRegex() {
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        val other = "oops"
        val actual: Boolean = regex.equals(other)
        val message: String = simpleNameOf<EmailAddressRegex>()
            .let { "Other object is not of type '$it'." }
        assertFalse(actual, message)
    }

    @Test
    fun equalsShouldFailWithAnotherEmailAddressRegexHavingAnotherPattern() {
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        val other: EmailAddressRegex = EmailAddressRegex.alphabetic()
        val actual: Boolean = regex.equals(other)
        val message =
            "Regular expressions with different pattern are not equal."
        assertFalse(actual, message)
    }

    @Test
    fun hashCodeShouldPass() {
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        val actual: Int = regex.hashCode()
        val expected: Int = hashCodeOf("$regex")
        val message = simpleNameOf(regex::class)
            .let { "Hash code of '$it' is calculated from its pattern." }
        assertEquals(expected, actual, message)
    }

    @Test
    fun matchesShouldPassWithValidText() {
        val text = "contact@kotools.org"
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        val actual: Boolean = regex.matches(text)
        val message =
            "'$text' matches the following regular expression: '$regex'."
        assertTrue(actual, message)
    }

    @Test
    fun matchesShouldFailWithInvalidText() {
        val text = "invalid-contact@kotools.org"
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        val actual: Boolean = regex.matches(text)
        val message =
            "'$text' doesn't match the following regular expression: '$regex'."
        assertFalse(actual, message)
    }

    // ----------------------- Companion.orNull(String) ------------------------

    @Test
    fun orNullPassesWithPatternMatchingDefaultOne() {
        val pattern = """^[a-z]+@[a-z]+\.[a-z]+$"""
        val regex: EmailAddressRegex? = EmailAddressRegex.orNull(pattern)
        assertEquals(expected = pattern, actual = "$regex")
    }

    @Test
    fun orNullFailsWithPatternNotMatchingDefaultOne() {
        val pattern = """^[a-z]+\.[a-z]+$"""
        val actual: EmailAddressRegex? = EmailAddressRegex.orNull(pattern)
        assertNull(actual)
    }

    // ----------------------- Companion.orThrow(String) -----------------------

    @Test
    fun orThrowPassesWithPatternMatchingDefaultOne() {
        val pattern = """^[a-z]+@[a-z]+\.[a-z]+$"""
        val regex: EmailAddressRegex = EmailAddressRegex.orThrow(pattern)
        assertEquals(expected = pattern, actual = "$regex")
    }

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
