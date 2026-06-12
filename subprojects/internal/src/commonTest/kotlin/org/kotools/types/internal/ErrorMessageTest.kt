package org.kotools.types.internal

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(InternalKotoolsTypesApi::class)
class ErrorMessageTest {
    @Test
    fun errorMessageCapitalizesReason() {
        val actual: String = errorMessage("reason")
        assertEquals(expected = "Reason", actual)
    }

    @Test
    fun errorMessageHasNoTrailingPoint() {
        val result: String = errorMessage("Reason")
        assertFalse("Illegal trailing point: '$result'") {
            result.endsWith('.')
        }
    }

    @Test
    fun errorMessageSeparatesReasonAndValueWithColon() {
        val reason = "Reason"
        val result: String = errorMessage(reason, "value")
        assertTrue("Missing colon delimiter: '$result'") {
            result.substringAfter(reason)
                .startsWith(':')
        }
    }

    @Test
    fun errorMessageWrapsStringValueInSingleQuotes() {
        val reason = "Reason"
        val value = "value"
        val result: String = errorMessage(reason, value)
        val quotedValue: String = result.substringAfter("$reason: ")
        assertEquals(expected = "'$value'", actual = quotedValue)
    }

    @Test
    fun errorMessageEscapesSingleQuotesInStringValue() {
        val reason = "Reason"
        val value = "O'Brien"
        val result: String = errorMessage(reason, value)
        val expected = "Reason: 'O\\'Brien'"
        assertEquals(expected, result)
    }

    @Test
    fun errorMessageLeavesNonStringValueUnquoted() {
        val reason = "Non-negative integer"
        val value = 123
        val result: String = errorMessage(reason, value)
        val unquotedValue: String = result.substringAfter("$reason: ")
        assertEquals(expected = "$value", actual = unquotedValue)
    }

    @Test
    fun errorMessageThrowsAnExceptionWithBlankReason() {
        val exception: IllegalArgumentException = assertFailsWith {
            errorMessage(reason = "  ")
        }
        assertEquals(expected = "Blank reason", actual = exception.message)
    }
}
