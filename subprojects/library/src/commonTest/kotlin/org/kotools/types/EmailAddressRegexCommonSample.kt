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
    fun toStringOverride() {
        val pattern: String = EmailAddressRegex.default()
            .toString()
        val expected = """^\S+@\S+\.\S+$"""
        assertEquals(expected, pattern)
    }
}
