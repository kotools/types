package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerJsJvmTest {
    // ----------------------- Long.toPositiveInteger() ------------------------

    @Test
    fun toPositiveIntegerPassesOnLongGreaterThanZero() {
        val number: Long = Long.MAX_VALUE
        val actual: String = number.toPositiveInteger()
            .toString()
        val expected: String = number.toString()
        assertEquals(expected, actual)
    }

    @Test
    fun toPositiveIntegerFailsOnLongEqualToZero() {
        val number = 0L
        val exception: IllegalArgumentException =
            assertFailsWith { number.toPositiveInteger() }
        val actual: String? = exception.message
        val expected = "$number must be greater than zero."
        assertEquals(expected, actual)
    }

    @Test
    fun toPositiveIntegerFailsOnLongLessThanZero() {
        val number = Long.MIN_VALUE
        val exception: IllegalArgumentException =
            assertFailsWith { number.toPositiveInteger() }
        val actual: String? = exception.message
        val expected = "$number must be greater than zero."
        assertEquals(expected, actual)
    }
}
