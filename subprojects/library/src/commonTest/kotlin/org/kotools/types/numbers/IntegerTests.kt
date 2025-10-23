package org.kotools.types.numbers

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerInstanceCreationTest {
    @Test
    fun withMaximumLong() {
        // Given
        val number: Long = Long.MAX_VALUE
        // When
        val actual = Integer(number)
        // Then
        assertEquals(expected = "$number", actual = "$actual")
    }

    @Test
    fun withMinimumLong() {
        // Given
        val number: Long = Long.MIN_VALUE
        // When
        val actual = Integer(number)
        // Then
        assertEquals(expected = "$number", actual = "$actual")
    }

    @Test
    fun withDecimalText() {
        // Given
        val text: String = Long.MAX_VALUE.toString()
        // When
        val actual = Integer(text)
        // Then
        assertEquals(expected = text, actual = "$actual")
    }

    @Test
    fun withPlusSignedDecimalText() {
        // Given
        val number: Long = Long.MAX_VALUE
        val text = "+$number"
        // When
        val actual = Integer(text)
        // Then
        assertEquals(expected = "$number", actual = "$actual")
    }

    @Test
    fun withMinusSignedDecimalText() {
        // Given
        val text: String = Long.MIN_VALUE.toString()
        // When
        val actual = Integer(text)
        // Then
        assertEquals(expected = text, actual = "$actual")
    }

    @Test
    fun withBlankText() {
        // Given
        val text = ""
        // When
        val actual: IllegalArgumentException = assertFailsWith {
            Integer(text)
        }
        // Then
        val expected = "Integer should not be blank"
        assertEquals(expected, actual.message)
    }

    @Test
    fun withNonDecimalText() {
        // Given
        val text = "hello"
        // When
        val actual: IllegalArgumentException = assertFailsWith {
            Integer(text)
        }
        // Then
        val expected = "Integer can only contain an optional + or - sign, " +
                "followed by a sequence of digits, was: $text"
        assertEquals(expected, actual.message)
    }
}

@ExperimentalKotoolsTypesApi
class IntegerArithmeticOperationTest {
    @Test
    fun plus() {
        // Given
        val x = Integer(Long.MAX_VALUE)
        val y = Integer(1)
        // When
        val actual: Integer = x + y
        // Then
        val expected = Integer("9223372036854775808")
        assertEquals(expected, actual)
    }

    @Test
    fun minus() {
        // Given
        val x = Integer(-9223372036854775807)
        val y = Integer(9223372036854775807)
        // When
        val actual: Integer = x - y
        // Then
        val expected = Integer("-18446744073709551614")
        assertEquals(expected, actual)
    }

    @Test
    fun times() {
        // Given
        val x = Integer(9223372036854775807)
        val y = Integer(10)
        // When
        val product: Integer = x * y
        // Then
        val expected = Integer("92233720368547758070")
        assertEquals(expected, product)
    }
}
