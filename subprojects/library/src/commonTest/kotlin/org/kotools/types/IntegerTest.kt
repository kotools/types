package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerTest {
    // ----------------------------- Constructors ------------------------------

    @Test
    fun constructorWithPlusSignedDecimalText() {
        // Given
        val text = "+18446744073709551614"
        // When
        val integer = Integer(text)
        // Then
        val result: String = integer.toString()
        val expected: String = text.removePrefix("+")
        assertEquals(expected, result)
    }

    @Test
    fun constructorWithMinusSignedDecimalText() {
        // Given
        val text = "-18446744073709551614"
        // When
        val integer = Integer(text)
        // Then
        val result: String = integer.toString()
        assertEquals(expected = text, result)
    }

    @Test
    fun constructorWithBlankText() {
        // Given
        val text = ""
        // When
        val exception: IllegalArgumentException = assertFailsWith {
            Integer(text)
        }
        // Then
        val result: String? = exception.message
        val expected = "Integer should not be blank"
        assertEquals(expected, result)
    }

    @Test
    fun constructorWithNonDecimalText() {
        // Given
        val text = "hello"
        // When
        val exception: IllegalArgumentException = assertFailsWith {
            Integer(text)
        }
        // Then
        val result: String? = exception.message
        val expected = "Integer can only contain an optional + or - sign, " +
                "followed by a sequence of digits, was: $text"
        assertEquals(expected, result)
    }

    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsWithAnotherTypeThanInteger() {
        // Given
        val number: Long = Long.MAX_VALUE
        val integer = Integer(number)
        // When
        val result: Boolean = integer.equals(other = number)
        // Then
        assertFalse(result)
    }

    @Test
    fun equalsWithIntegerHavingAnotherValue() {
        // Given
        val integer = Integer(Long.MAX_VALUE)
        val other = Integer(Long.MIN_VALUE)
        // When
        val result: Boolean = integer == other
        // Then
        assertFalse(result)
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
