package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressRegexCompanionCommonSample {
    @Test
    fun default() {
        val regex: EmailAddressRegex = EmailAddressRegex.default()
        val expected = """^\S+@\S+\.\S+$"""
        assertEquals(expected, "$regex")
    }
}
