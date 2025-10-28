package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonZeroIntegerTest {
    @Test
    fun constructorWithZeroInt() {
        // Given
        val number = 0
        // When
        val result: IllegalArgumentException = assertFailsWith {
            NonZeroInteger(number)
        }
        // Then
        val expected = "NonZeroInteger shouldn't be zero"
        assertEquals(expected, result.message)
    }

    @Test
    fun constructorWithZeroLong() {
        // Given
        val number = 0L
        // When
        val result: IllegalArgumentException = assertFailsWith {
            NonZeroInteger(number)
        }
        // Then
        val expected = "NonZeroInteger shouldn't be zero"
        assertEquals(expected, result.message)
    }

    @Test
    fun constructorWithPlusSignedNonZeroDecimalString() {
        // Given
        val text = "+18446744073709551614"
        // When
        val result = NonZeroInteger(text)
        // Then
        val resultAsString: String = result.toString()
        val expected: String = text.removePrefix("+")
        assertEquals(expected, resultAsString)
    }

    @Test
    fun constructorWithMinusSignedNonZeroDecimalString() {
        // Given
        val text = "-18446744073709551614"
        // When
        val result = NonZeroInteger(text)
        // Then
        val resultAsString: String = result.toString()
        assertEquals(expected = text, resultAsString)
    }

    @Test
    fun constructorWithBlankString() {
        // Given
        val text = "  "
        // When
        val result: IllegalArgumentException = assertFailsWith {
            NonZeroInteger(text)
        }
        // Then
        val expected = "Integer should not be blank"
        assertEquals(expected, result.message)
    }

    @Test
    fun constructorWithNonDecimalString() {
        // Given
        val text = "oops"
        // When
        val result: IllegalArgumentException = assertFailsWith {
            NonZeroInteger(text)
        }
        // Then
        val expected = "Integer can only contain an optional + or - sign, " +
                "followed by a sequence of digits, was: $text"
        assertEquals(expected, result.message)
    }

    @Test
    fun constructorWithDecimalStringHavingSingleZero() {
        // Given
        val text = "0"
        // When
        val result: IllegalArgumentException = assertFailsWith {
            NonZeroInteger(text)
        }
        // Then
        val expected = "NonZeroInteger shouldn't be zero"
        assertEquals(expected, result.message)
    }

    @Test
    fun constructorWithDecimalStringHavingMultipleZeros() {
        // Given
        val text = "000"
        // When
        val result: IllegalArgumentException = assertFailsWith {
            NonZeroInteger(text)
        }
        // Then
        val expected = "NonZeroInteger shouldn't be zero"
        assertEquals(expected, result.message)
    }

    @Test
    fun constructorWithPlusSignedZeroDecimalString() {
        // Given
        val text = "+000"
        // When
        val result: IllegalArgumentException = assertFailsWith {
            NonZeroInteger(text)
        }
        // Then
        val expected = "NonZeroInteger shouldn't be zero"
        assertEquals(expected, result.message)
    }

    @Test
    fun constructorWithMinusSignedZeroDecimalString() {
        // Given
        val text = "-000"
        // When
        val result: IllegalArgumentException = assertFailsWith {
            NonZeroInteger(text)
        }
        // Then
        val expected = "NonZeroInteger shouldn't be zero"
        assertEquals(expected, result.message)
    }
}
