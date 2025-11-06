package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerTest {
    // ------------------------------ Comparisons ------------------------------

    @Test
    fun equalsWithIntegerHavingSameValue() {
        // Given
        val number: Long = Long.MAX_VALUE
        val integer: Integer = Integer.from(number)
        val other: Integer = Integer.from(number)
        // When
        val result: Boolean = integer == other
        // Then
        assertTrue(result)
    }

    @Test
    fun equalsWithAnotherTypeThanInteger() {
        // Given
        val number: Long = Long.MAX_VALUE
        val integer: Integer = Integer.from(number)
        // When
        val result: Boolean = integer.equals(other = number)
        // Then
        assertFalse(result)
    }

    @Test
    fun equalsWithIntegerHavingAnotherValue() {
        // Given
        val integer: Integer = Integer.from(Long.MAX_VALUE)
        val other: Integer = Integer.from(Long.MIN_VALUE)
        // When
        val result: Boolean = integer == other
        // Then
        assertFalse(result)
    }

    @Test
    fun hashCodeReturnsSameValueForEqualIntegers() {
        // Given
        val number: Long = Long.MAX_VALUE
        val integer: Integer = Integer.from(number)
        // When
        val result: Int = integer.hashCode()
        // Then
        val expected: Int = Integer.from(number)
            .hashCode()
        assertEquals(expected, result)
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun plusWithNonZeroIntegers() {
        // Given
        val number = 9223372036854775807
        val x: Integer = Integer.from(number)
        val y: Integer = Integer.from(number)
        // When
        val result: Integer = x + y
        // Then
        val expected: Integer = Integer.fromDecimal("18446744073709551614")
        assertEquals(expected, result)
    }

    @Test
    fun plusWithZeroAndNonZeroIntegers() {
        // Given
        val x: Integer = Integer.from(0)
        val y: Integer = Integer.from(123)
        // When
        val result: Integer = x + y
        // Then
        assertEquals(expected = y, result)
    }

    @Test
    fun plusWithNonZeroAndZeroIntegers() {
        // Given
        val x: Integer = Integer.from(123)
        val y: Integer = Integer.from(0)
        // When
        val result: Integer = x + y
        // Then
        assertEquals(expected = x, result)
    }

    @Test
    fun minusWithNonZeroIntegers() {
        // Given
        val x: Integer = Integer.from(-9223372036854775807)
        val y: Integer = Integer.from(9223372036854775807)
        // When
        val result: Integer = x - y
        // Then
        val expected: Integer = Integer.fromDecimal("-18446744073709551614")
        assertEquals(expected, result)
    }

    @Test
    fun minusWithZeroAndNonZeroIntegers() {
        // Given
        val x: Integer = Integer.from(0)
        val y: Integer = Integer.from(123)
        // When
        val result: Integer = x - y
        // Then
        val expected: Integer = Integer.from(-123)
        assertEquals(expected, result)
    }

    @Test
    fun minusWithNonZeroAndZeroIntegers() {
        // Given
        val x: Integer = Integer.from(123)
        val y: Integer = Integer.from(0)
        // When
        val result: Integer = x - y
        // Then
        assertEquals(expected = x, result)
    }

    @Test
    fun timesWithNonZeroIntegers() {
        // Given
        val x: Integer = Integer.from(9223372036854775807)
        val y: Integer = Integer.from(1000)
        // When
        val result: Integer = x * y
        // Then
        val expected: Integer = Integer.fromDecimal("9223372036854775807000")
        assertEquals(expected, result)
    }

    @Test
    fun timesWithZeroAndNonZeroIntegers() {
        // Given
        val x: Integer = Integer.from(0)
        val y: Integer = Integer.from(123)
        // When
        val result: Integer = x * y
        // Then
        assertEquals(expected = x, result)
    }

    @Test
    fun timesWithNonZeroAndZeroIntegers() {
        // Given
        val x: Integer = Integer.from(123)
        val y: Integer = Integer.from(0)
        // When
        val result: Integer = x * y
        // Then
        assertEquals(expected = y, result)
    }

    @Test
    fun timesWithOneAndNonZeroIntegers() {
        // Given
        val x: Integer = Integer.from(1)
        val y: Integer = Integer.from(123)
        // When
        val result: Integer = x * y
        // Then
        assertEquals(expected = y, result)
    }

    @Test
    fun timesWithNonZeroAndOneIntegers() {
        // Given
        val x: Integer = Integer.from(123)
        val y: Integer = Integer.from(1)
        // When
        val result: Integer = x * y
        // Then
        assertEquals(expected = x, result)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringReturnsDecimalString() {
        // Given
        val number: Long = Long.MAX_VALUE
        val integer: Integer = Integer.from(number)
        // When
        val result = "$integer"
        // Then
        assertEquals(expected = "$number", result)
    }

    // ----------------------- Class-level declarations ------------------------

    @Test
    fun from() {
        // Given
        val number: Long = Long.MAX_VALUE
        // When
        val integer: Integer = Integer.from(number)
        // Then
        assertEquals(expected = "$number", actual = "$integer")
    }

    @Test
    fun fromDecimalWithNonZeroDecimalString() {
        // Given
        val number = 123456L
        val text = "$number"
        // When
        val result: Integer = Integer.fromDecimal(text)
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalWithPlusSignedNonZeroDecimalString() {
        // Given
        val number = 123456L
        val text = "+$number"
        // When
        val result: Integer = Integer.fromDecimal(text)
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalWithMinusSignedNonZeroDecimalString() {
        // Given
        val number: Long = -123456L
        val text = "$number"
        // When
        val result: Integer = Integer.fromDecimal(text)
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalWithSingleZeroDecimalString() {
        // Given
        val text = "0"
        // When
        val result: Integer = Integer.fromDecimal(text)
        // Then
        val expected: Integer = Integer.from(0)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalWithMultipleZerosDecimalString() {
        // Given
        val text = "000"
        // When
        val result: Integer = Integer.fromDecimal(text)
        // Then
        val expected: Integer = Integer.from(0)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalWithPlusSignedZeroDecimalString() {
        // Given
        val text = "+0"
        // When
        val result: Integer = Integer.fromDecimal(text)
        // Then
        val expected: Integer = Integer.from(0)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalWithMinusSignedZeroDecimalString() {
        // Given
        val text = "-0"
        // When
        val result: Integer = Integer.fromDecimal(text)
        // Then
        val expected: Integer = Integer.from(0)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalWithLeadingZerosInPositiveDecimalString() {
        // Given
        val number = 123L
        val text = "000$number"
        // When
        val result: Integer = Integer.fromDecimal(text)
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalWithLeadingZerosInNegativeDecimalString() {
        // Given
        val number = 123L
        val text = "-000$number"
        // When
        val result: Integer = Integer.fromDecimal(text)
        // Then
        val expected: Integer = Integer.from(-number)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalWithBlankString() {
        // Given
        val text = " "
        // When
        val result: IllegalArgumentException = assertFailsWith {
            Integer.fromDecimal(text)
        }
        // Then
        val expected = "Integer can't be blank."
        assertEquals(expected, result.message)
    }

    @Test
    fun fromDecimalWithPlusSignString() {
        // Given
        val text = "+"
        // When
        val result: IllegalArgumentException = assertFailsWith {
            Integer.fromDecimal(text)
        }
        // Then
        val expected: String = "Integer can only contain an optional + or - " +
                "sign, followed by a sequence of digits (was: $text)."
        assertEquals(expected, result.message)
    }

    @Test
    fun fromDecimalWithMinusSignString() {
        // Given
        val text = "-"
        // When
        val result: IllegalArgumentException = assertFailsWith {
            Integer.fromDecimal(text)
        }
        // Then
        val expected: String = "Integer can only contain an optional + or - " +
                "sign, followed by a sequence of digits (was: $text)."
        assertEquals(expected, result.message)
    }

    @Test
    fun fromDecimalWithNonDecimalString() {
        // Given
        val text = "oops"
        // When
        val result: IllegalArgumentException = assertFailsWith {
            Integer.fromDecimal(text)
        }
        // Then
        val expected: String = "Integer can only contain an optional + or - " +
                "sign, followed by a sequence of digits (was: $text)."
        assertEquals(expected, result.message)
    }

    @Test
    fun fromDecimalOrNullWithNonZeroDecimalString() {
        // Given
        val number = 123456L
        val text = "$number"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalOrNullWithPlusSignedNonZeroDecimalString() {
        // Given
        val number = 123456L
        val text = "+$number"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalOrNullWithMinusSignedNonZeroDecimalString() {
        // Given
        val number: Long = -123456L
        val text = "$number"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalOrNullWithSingleZeroDecimalString() {
        // Given
        val text = "0"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        val expected: Integer = Integer.from(0)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalOrNullWithMultipleZerosDecimalString() {
        // Given
        val text = "000"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        val expected: Integer = Integer.from(0)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalOrNullWithPlusSignedZeroDecimalString() {
        // Given
        val text = "+0"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        val expected: Integer = Integer.from(0)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalOrNullWithMinusSignedZeroDecimalString() {
        // Given
        val text = "-0"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        val expected: Integer = Integer.from(0)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalOrNullWithLeadingZerosInPositiveDecimalString() {
        // Given
        val number = 123L
        val text = "000$number"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalOrNullWithLeadingZerosInNegativeDecimalString() {
        // Given
        val number = 123L
        val text = "-000$number"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        val expected: Integer = Integer.from(-number)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalOrNullWithBlankString() {
        // Given
        val text = "  "
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        assertNull(result)
    }

    @Test
    fun fromDecimalOrNullWithPlusSignString() {
        // Given
        val text = "+"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        assertNull(result)
    }

    @Test
    fun fromDecimalOrNullWithMinusSignString() {
        // Given
        val text = "-"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        assertNull(result)
    }

    @Test
    fun fromDecimalOrNullWithNonDecimalString() {
        // Given
        val text = "oops"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        assertNull(result)
    }
}
