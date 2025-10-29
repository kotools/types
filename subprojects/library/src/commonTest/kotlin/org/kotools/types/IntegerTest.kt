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
        val text = "+9223372036854775807"
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
        val text = "-92233720368547758070"
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
        val number = 9_223_372_036_854_775_807
        val integer = Integer(number)
        // When
        val result: Boolean = integer.equals(other = number)
        // Then
        assertFalse(result)
    }

    @Test
    fun equalsWithIntegerHavingAnotherValue() {
        // Given
        val integer = Integer(9_223_372_036_854_775_807)
        val other = Integer(-9_223_372_036_854_775_807)
        // When
        val result: Boolean = integer == other
        // Then
        assertFalse(result)
    }
}
