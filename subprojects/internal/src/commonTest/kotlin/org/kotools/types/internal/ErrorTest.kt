package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(InternalKotoolsTypesApi::class)
class ErrorTest {
    @Test
    fun constructor_should_pass_with_valid_arguments() {
        val integer: Int = Random.nextInt(Int.MIN_VALUE..0)
        val message = "'$integer' is invalid."
        val reason = "It should be greater than zero."
        Error(message, reason)
    }

    @Test
    fun constructor_should_fail_with_a_blank_message() {
        val message = "  "
        val reason = "It should be greater than zero."
        val exception: IllegalArgumentException = assertFailsWith {
            Error(message, reason)
        }
        val actual: String? = exception.message
        val expected: String = Error.BLANK_MESSAGE
        assertEquals(expected, actual)
    }

    @Test
    fun constructor_should_fail_with_a_blank_reason() {
        val integer: Int = Random.nextInt(Int.MIN_VALUE..0)
        val message = "'$integer' is invalid."
        val reason = "  "
        val exception: IllegalArgumentException = assertFailsWith {
            Error(message, reason)
        }
        val actual: String? = exception.message
        val expected: String = Error.BLANK_REASON
        assertEquals(expected, actual)
    }

    @Test
    fun toString_should_pass() {
        val integer: Int = Random.nextInt(Int.MIN_VALUE..0)
        val message = "'$integer' is invalid."
        val reason = "It should be greater than zero."
        val error = Error(message, reason)
        val actual: String = error.toString()
        val expected = "$message $reason"
        assertEquals(expected, actual)
    }
}
