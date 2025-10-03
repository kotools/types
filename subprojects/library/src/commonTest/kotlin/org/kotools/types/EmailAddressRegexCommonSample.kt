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
        assertTrue(result)
    }

    @Test
    fun hashCodeOverride() {
        val hashCode: Int = EmailAddressRegex.default()
            .hashCode()
        val other: Int = EmailAddressRegex.default()
            .hashCode()
        assertEquals(hashCode, other)
    }

    @Test
    fun matches() {
        val text = "contact@kotools.org"
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        assertTrue(regex matches text)
    }

    @Test
    fun toStringOverride() {
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        val result = "$regex" // or regex.toString()
        assertEquals(expected = """^\S+@\S+\.\S+$""", result)
    }

    // ------------------------------- Companion -------------------------------

    @Test
    fun default() {
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        assertEquals(expected = """^\S+@\S+\.\S+$""", "$regex")
    }

    @Test
    fun orNull() {
        val pattern = """^[a-z]+@[a-z]+\.[a-z]+$"""
        val regex: EmailAddressRegex? = EmailAddressRegex.orNull(pattern)
        assertEquals(expected = pattern, "$regex")
    }

    @Test
    fun orThrow() {
        val pattern = """^[a-z]+@[a-z]+\.[a-z]+$"""
        val regex: EmailAddressRegex = EmailAddressRegex.orThrow(pattern)
        assertEquals(expected = pattern, "$regex")
    }

    @Test
    fun alphabetic() {
        val regex: EmailAddressRegex = EmailAddressRegex.alphabetic()
        assertEquals(expected = """^[a-z]+@[a-z]+\.[a-z]+$""", "$regex")
    }

    @Test
    fun alphanumeric() {
        val regex: EmailAddressRegex = EmailAddressRegex.alphanumeric()
        val expected = """^[0-9a-z]+@[0-9a-z]+\.[0-9a-z]+$"""
        assertEquals(expected, "$regex")
    }
}
