package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressRegexCommonSample {
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
}
