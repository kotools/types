package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressCompanionTest {
    @Test
    fun pattern_should_pass() {
        val actual: String = EmailAddress.PATTERN
        val expected = "^\\S+@\\S+\\.\\S+\$"
        assertEquals(expected, actual)
    }
}
