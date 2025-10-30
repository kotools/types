package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerTest {
    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsPassesWithIntegerHavingSameValue() {
        val number: Long = Long.MAX_VALUE
        val integer: Integer = Integer.from(number)
        val other: Integer = Integer.from(number)
        val result: Boolean = integer == other
        assertTrue(result)
    }

    @Test
    fun equalsFailsWithAnotherTypeThanInteger() {
        val number: Long = Long.MAX_VALUE
        val integer: Integer = Integer.from(number)
        val result: Boolean = integer.equals(other = number)
        assertFalse(result)
    }

    @Test
    fun equalsFailsWithIntegerHavingAnotherValue() {
        val integer: Integer = Integer.from(Long.MAX_VALUE)
        val other: Integer = Integer.from(Long.MIN_VALUE)
        val result: Boolean = integer == other
        assertFalse(result)
    }

    @Test
    fun hashCodeReturnsSameValueForIntegersThatAreEqual() {
        val number: Long = Long.MAX_VALUE
        val x: Integer = Integer.from(number)
        val y: Integer = Integer.from(number)
        assertEquals(x.hashCode(), y.hashCode())
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun plusPasses() {
        val x: Integer = Integer.from(Long.MAX_VALUE)
        val y: Integer = Integer.from(Long.MAX_VALUE)
        val actual: Integer = x + y
        val expected: Integer = Integer.parse("18446744073709551614")
        assertEquals(expected, actual)
    }

    @Test
    fun minusPasses() {
        val x: Integer = Integer.from(Long.MIN_VALUE)
        val y: Integer = Integer.from(Long.MAX_VALUE)
        val actual: Integer = x - y
        val expected: Integer = Integer.parse("-18446744073709551615")
        assertEquals(expected, actual)
    }

    // ----------------------- Class-level declarations ------------------------

    @Test
    fun fromPassesWithLong() {
        // Given
        val number: Long = Long.MAX_VALUE
        // When
        val integer: Integer = Integer.from(number)
        // Then
        val result: String = integer.toString()
        val expected: String = number.toString()
        assertEquals(expected, result)
    }

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
        val plusSign = "+"
        val text = "${plusSign}1234"
        // When
        val integer: Integer? = Integer.parseOrNull(text)
        // Then
        assertNotNull(integer)
        val result: String = integer.toString()
        val expected: String = text.removePrefix(plusSign)
        assertEquals(expected, result)
    }

    @Test
    fun parseOrNullWithMinusSignedDecimalString() {
        // Given
        val text = "-1234"
        // When
        val integer: Integer? = Integer.parseOrNull(text)
        // Then
        assertNotNull(integer)
        val result: String = integer.toString()
        assertEquals(expected = text, result)
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
