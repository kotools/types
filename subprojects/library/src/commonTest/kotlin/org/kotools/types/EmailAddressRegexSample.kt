package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressRegexSample {
    @Test
    fun equalsOverride() {
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        val other: EmailAddressRegex = EmailAddressRegex.default()
        val result: Boolean = regex == other // or regex.equals(other)
        val message = "Regular expressions with the same pattern are equal."
        assertTrue(result, message)
    }

    @Test
    fun hashCodeOverride() {
        val hashCode: Int = EmailAddressRegex.default()
            .hashCode()
        val other: Int = EmailAddressRegex.default()
            .hashCode()
        val result: Boolean = hashCode == other
        val message = "Regular expressions with the same pattern have the " +
                "same hash code value."
        assertTrue(result, message)
    }

    @Test
    fun matches() {
        val text = "contact@kotools.org"
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        val result: Boolean = regex.matches(text)
        val message =
            "'$text' matches the following regular expression: '$regex'."
        assertTrue(result, message)
    }

    @Test
    fun toStringOverride() {
        val pattern: String = EmailAddressRegex.default()
            .toString()
        val expected = """^\S+@\S+\.\S+$"""
        assertEquals(expected, pattern)
    }

    // ------------------------------- Companion -------------------------------

    @Test
    fun default() {
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        val expected = """^\S+@\S+\.\S+$"""
        assertEquals(expected, "$regex")
    }

    @Test
    fun orNull() {
        val pattern = """^[a-z]+@[a-z]+\.[a-z]+$"""
        val regex: EmailAddressRegex? = EmailAddressRegex.orNull(pattern)
        val message = "'$pattern' is valid for validating email addresses."
        assertNotNull(regex, message)
    }

    @Test
    fun orThrow() {
        EmailAddressRegex.orThrow("""^[a-z]+@[a-z]+\.[a-z]+$""")
    }

    @Test
    fun alphabetic() {
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        val expected = """^[a-z]+@[a-z]+\.[a-z]+$"""
        assertEquals(expected, "$regex")
    }

    @Test
    fun alphanumeric() {
        val regex: EmailAddressRegex = EmailAddressRegex.alphanumeric()
        val expected = """^[0-9a-z]+@[0-9a-z]+\.[0-9a-z]+$"""
        assertEquals(expected, "$regex")
    }
}
