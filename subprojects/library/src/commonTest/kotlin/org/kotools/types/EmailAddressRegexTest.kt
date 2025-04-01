package org.kotools.types

import org.kotools.types.internal.ExceptionMessage
import kotlin.test.Test
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class EmailAddressRegexCompanionTest {
    @Test
    fun orNullShouldFailWithInvalidPattern() {
        val pattern = """^[a-z]+\.[a-z]+$"""
        val actual: EmailAddressRegex? = EmailAddressRegex.orNull(pattern)
        val message: String = ExceptionMessage
            .invalidEmailAddressPattern(pattern)
            .toString()
        assertNull(actual, message)
    }
}
