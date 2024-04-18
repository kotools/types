package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(InternalKotoolsTypesApi::class)
class InvalidEmailAddressPatternTest {
    @Test
    fun constructor_should_pass_with_valid_arguments() {
        val pattern = "^[a-z]+\$"
        val validationPattern = "^\\S+@\\S+\\.\\S+\$"
        InvalidEmailAddressPattern(pattern, validationPattern)
    }

    @Test
    fun constructor_should_fail_with_a_blank_pattern() {
        val pattern = "  "
        val validationPattern = "^\\S+@\\S+\\.\\S+\$"
        val exception: IllegalArgumentException = assertFailsWith {
            InvalidEmailAddressPattern(pattern, validationPattern)
        }
        val actual: String? = exception.message
        val expected: String = InvalidEmailAddress.BLANK_PATTERN
        assertEquals(expected, actual)
    }

    @Test
    fun constructor_should_fail_with_a_blank_validation_pattern() {
        val pattern = "^[a-z]+\$"
        val validationPattern = "  "
        val exception: IllegalArgumentException = assertFailsWith {
            InvalidEmailAddressPattern(pattern, validationPattern)
        }
        val actual: String? = exception.message
        val expected: String =
            InvalidEmailAddressPattern.BLANK_VALIDATION_PATTERN
        assertEquals(expected, actual)
    }

    @Test
    fun toString_should_pass() {
        val pattern = "^[a-z]+\$"
        val validationPattern = "^\\S+@\\S+\\.\\S+\$"
        val error = InvalidEmailAddressPattern(pattern, validationPattern)
        val actual: String = error.toString()
        val expected = "'$pattern' is invalid for validating email addresses." +
                " It should match the following pattern: '$validationPattern'."
        assertEquals(expected, actual)
    }
}
