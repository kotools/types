package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerTest {
    @Test
    fun constructorWithMaximumLong() {
        // Given
        val number: Long = Long.MAX_VALUE
        // When
        val actual = Integer(number)
        // Then
        assertEquals(expected = "$number", actual = "$actual")
    }

    @Test
    fun constructorWithMinimumLong() {
        // Given
        val number: Long = Long.MIN_VALUE
        // When
        val actual = Integer(number)
        // Then
        assertEquals(expected = "$number", actual = "$actual")
    }

    @Test
    fun constructorWithDecimalText() {
        // Given
        val text: String = Long.MAX_VALUE.toString()
        // When
        val actual = Integer(text)
        // Then
        assertEquals(expected = text, actual = "$actual")
    }

    @Test
    fun constructorWithPlusSignedDecimalText() {
        // Given
        val number: Long = Long.MAX_VALUE
        val text = "+$number"
        // When
        val actual = Integer(text)
        // Then
        assertEquals(expected = "$number", actual = "$actual")
    }

    @Test
    fun constructorWithMinusSignedDecimalText() {
        // Given
        val text: String = Long.MIN_VALUE.toString()
        // When
        val actual = Integer(text)
        // Then
        assertEquals(expected = text, actual = "$actual")
    }

    @Test
    fun constructorWithBlankText() {
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
    fun constructorWithNonDecimalText() {
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

    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsWithIntegerHavingSameValue() {
        // Given
        val x = Integer(Long.MAX_VALUE)
        val y = Integer(Long.MAX_VALUE)
        // When
        val actual: Boolean = x == y
        // Then
        assertTrue(actual)
    }

    @Test
    fun equalsWithAnotherTypeThanInteger() {
        // Given
        val x = Integer(Long.MAX_VALUE)
        val y: Long = Long.MAX_VALUE
        // When
        val actual: Boolean = x.equals(y)
        // Then
        assertFalse(actual)
    }

    @Test
    fun equalsWithIntegerHavingAnotherValue() {
        // Given
        val x = Integer(Long.MAX_VALUE)
        val y = Integer(Long.MIN_VALUE)
        // When
        val actual: Boolean = x == y
        // Then
        assertFalse(actual)
    }

    @Test
    fun hashCodeReturnsHashCodeOfPlatformInteger() {
        // Given
        val integer = Integer(Long.MAX_VALUE)
        // When
        val actual: Int = integer.hashCode()
        // Then
        val expected: Int = Integer(Long.MAX_VALUE)
            .hashCode()
        assertEquals(expected, actual)
    }

    // ------------------------- Arithmetic operations -------------------------

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

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringPasses() {
        // Given
        val integer = Integer(Long.MAX_VALUE)
        // When
        val actual: String = integer.toString()
        // Then
        assertEquals(expected = "${Long.MAX_VALUE}", actual)
    }
}
