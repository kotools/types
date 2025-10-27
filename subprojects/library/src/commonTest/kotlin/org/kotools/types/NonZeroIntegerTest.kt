package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonZeroIntegerTest {
    @Test
    fun constructorWithNonZeroInt() {
        // Given
        val number: Int = Int.MAX_VALUE
        // When
        val integer = NonZeroInteger(number)
        // Then
        val actualAsString: String = integer.toString()
        val expected: String = number.toString()
        assertEquals(expected, actualAsString)
    }

    @Test
    fun constructorWithZeroInt() {
        // Given
        val number = 0
        // When
        val exception: IllegalArgumentException = assertFailsWith {
            NonZeroInteger(number)
        }
        // Then
        val actual: String? = exception.message
        val expected = "NonZeroInteger shouldn't be zero"
        assertEquals(expected, actual)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        // Given
        val number: Int = Int.MAX_VALUE
        val integer = NonZeroInteger(number)
        // When
        val actual: String = integer.toString()
        // Then
        val expected: String = number.toString()
        assertEquals(expected, actual)
    }
}
