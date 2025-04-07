package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressRegexCompanionCommonSample {
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
}
