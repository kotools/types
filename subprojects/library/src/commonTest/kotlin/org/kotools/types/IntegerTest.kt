package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerTest {
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

    // ----------------------- Class-level declarations ------------------------

    @Test
    fun parseWithPlusSignedDecimalString() {
        // Given
        val plusSign = "+"
        val text = "${plusSign}1234"
        // When
        val integer: Integer = Integer.parse(text)
        // Then
        val result: String = integer.toString()
        val expected: String = text.removePrefix(plusSign)
        assertEquals(expected, result)
    }

    @Test
    fun parseWithMinusSignedDecimalString() {
        // Given
        val text = "-1234"
        // When
        val integer: Integer = Integer.parse(text)
        // Then
        val result: String = integer.toString()
        assertEquals(expected = text, result)
    }

    @Test
    fun parseWithBlankString() {
        // Given
        val text = "   "
        // When
        val exception: IllegalArgumentException = assertFailsWith {
            Integer.parse(text)
        }
        // Then
        val result: String? = exception.message
        val expected = "Integer should not be blank"
        assertEquals(expected, result)
    }

    @Test
    fun parseWithNonDecimalString() {
        // Given
        val text = "oops"
        // When
        val exception: IllegalArgumentException = assertFailsWith {
            Integer.parse(text)
        }
        // Then
        val result: String? = exception.message
        val expected = "Integer can only contain an optional + or - sign, " +
                "followed by a sequence of digits, was: $text"
        assertEquals(expected, result)
    }

    @Test
    fun parseOrNullWithPlusSignedDecimalString() {
        // Given
        val text = "+1234"
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        assertNotNull(result)
    }

    @Test
    fun parseOrNullWithMinusSignedDecimalString() {
        // Given
        val text = "-1234"
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        assertNotNull(result)
    }

    @Test
    fun parseOrNullWithBlankString() {
        // Given
        val text = "  "
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        assertNull(result)
    }

    @Test
    fun parseOrNullWithNonDecimalString() {
        // Given
        val text = "oops"
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        assertNull(result)
    }
}
