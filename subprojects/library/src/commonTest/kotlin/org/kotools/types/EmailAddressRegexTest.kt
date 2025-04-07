package org.kotools.types

import org.kotools.types.internal.ExceptionMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
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

    @Test
    fun orThrowShouldFailWithInvalidPattern() {
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
