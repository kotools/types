package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(InternalKotoolsTypesApi::class)
class InvalidEmailAddressTest {
    @Test
    fun constructor_should_pass_with_valid_arguments() {
        val value = " @kotools.org"
        val pattern = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        InvalidEmailAddress(value, pattern)
    }

    @Test
    fun constructor_should_fail_with_a_blank_value() {
        val value = "  "
        val pattern = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val exception: IllegalArgumentException = assertFailsWith {
            InvalidEmailAddress(value, pattern)
        }
        val actual: String? = exception.message
        val expected: String = InvalidEmailAddress.BLANK_VALUE
        assertEquals(expected, actual)
    }

    @Test
    fun constructor_should_fail_with_a_blank_pattern() {
        val value = " @kotools.org"
        val pattern = "   "
        val exception: IllegalArgumentException = assertFailsWith {
            InvalidEmailAddress(value, pattern)
        }
        val actual: String? = exception.message
        val expected: String = InvalidEmailAddress.BLANK_PATTERN
        assertEquals(expected, actual)
    }

    @Test
    fun toString_should_pass() {
        val value = " @kotools.org"
        val pattern = "^[a-z]+@[a-z]+\\.[a-z]+\$"
        val error = InvalidEmailAddress(value, pattern)
        val actual: String = error.toString()
        val expected = "'$value' is an invalid email address. It should " +
                "match the following pattern: '$pattern'."
        assertEquals(expected, actual)
    }
}
