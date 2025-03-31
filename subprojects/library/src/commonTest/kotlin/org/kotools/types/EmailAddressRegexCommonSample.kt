package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressRegexCommonSample {
    @Test
    fun toStringOverride() {
        val pattern: String = EmailAddressRegex.default()
            .toString()
        val expected = """^\S+@\S+\.\S+$"""
        assertEquals(expected, pattern)
    }
}
