package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerTest {
    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsPassesWithIntegerHavingSameValue() {
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
    fun equalsFailsWithAnotherTypeThanInteger() {
        // Given
        val number: Long = Long.MAX_VALUE
        val integer: Integer = Integer.from(number)
        // When
        val result: Boolean = integer.equals(other = number)
        // Then
        assertFalse(result)
    }

    @Test
    fun equalsFailsWithIntegerHavingAnotherValue() {
        // Given
        val integer: Integer = Integer.from(Long.MAX_VALUE)
        val other: Integer = Integer.from(Long.MIN_VALUE)
        // When
        val result: Boolean = integer == other
        // Then
        assertFalse(result)
    }

    @Test
    fun hashCodeReturnsSameValueForIntegersThatAreEqual() {
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

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun compareToDelegatesToStringsComparison() {
        // Given
        val x: Integer = Integer.fromDecimal("123")
        val y: Integer = Integer.fromDecimal("-123456")
        // When
        val result: Int = x.compareTo(y)
        // Then
        val expected: Int = "$x".compareTo("$y")
        assertEquals(expected, result)
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinusPassesOnZeroInteger() {
        // Given
        val x: Integer = Integer.Zero
        // When
        val result: Integer = -x
        // Then
        assertEquals(expected = x, result)
    }

    @Test
    fun unaryMinusPassesOnPositiveInteger() {
        // Given
        val number = 123456L
        val x: Integer = Integer.from(number)
        // When
        val result: Integer = -x
        // Then
        val expected: Integer = Integer.from(-number)
        assertEquals(expected, result)
    }

    @Test
    fun unaryMinusPassesOnNegativeInteger() {
        // Given
        val number = 123456L
        val x: Integer = Integer.from(-number)
        // When
        val result: Integer = -x
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun plusPassesWithNonZeroIntegers() {
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
    fun plusPassesWithZeroAndNonZeroIntegers() {
        // Given
        val x: Integer = Integer.Zero
        val y: Integer = Integer.from(123)
        // When
        val result: Integer = x + y
        // Then
        assertEquals(expected = y, result)
    }

    @Test
    fun plusPassesWithNonZeroAndZeroIntegers() {
        // Given
        val x: Integer = Integer.from(123)
        val y: Integer = Integer.Zero
        // When
        val result: Integer = x + y
        // Then
        assertEquals(expected = x, result)
    }

    @Test
    fun minusPassesWithNonZeroIntegers() {
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
    fun minusPassesWithZeroAndNonZeroIntegers() {
        // Given
        val x: Integer = Integer.Zero
        val y: Integer = Integer.from(123)
        // When
        val result: Integer = x - y
        // Then
        val expected: Integer = -y
        assertEquals(expected, result)
    }

    @Test
    fun minusPassesWithNonZeroAndZeroIntegers() {
        // Given
        val x: Integer = Integer.from(123)
        val y: Integer = Integer.Zero
        // When
        val result: Integer = x - y
        // Then
        assertEquals(expected = x, result)
    }

    @Test
    fun timesPassesWithNonZeroIntegers() {
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
    fun timesPassesWithZeroAndNonZeroIntegers() {
        // Given
        val x: Integer = Integer.Zero
        val y: Integer = Integer.from(123)
        // When
        val result: Integer = x * y
        // Then
        assertEquals(expected = x, result)
    }

    @Test
    fun timesPassesWithNonZeroAndZeroIntegers() {
        // Given
        val x: Integer = Integer.from(123)
        val y: Integer = Integer.Zero
        // When
        val result: Integer = x * y
        // Then
        assertEquals(expected = y, result)
    }

    @Test
    fun timesPassesWithOneAndNonZeroIntegers() {
        // Given
        val x: Integer = Integer.One
        val y: Integer = Integer.from(123)
        // When
        val result: Integer = x * y
        // Then
        assertEquals(expected = y, result)
    }

    @Test
    fun timesPassesWithNonZeroAndOneIntegers() {
        // Given
        val x: Integer = Integer.from(123)
        val y: Integer = Integer.One
        // When
        val result: Integer = x * y
        // Then
        assertEquals(expected = x, result)
    }

    @Test
    fun divPassesWithNonZeroIntegerProducingInteger() {
        // Given
        val x: Integer = Integer.from(12)
        val y: Integer = Integer.from(3)
        // When
        val result: Integer = x / y
        // Then
        val expected: Integer = Integer.from(4)
        assertEquals(expected, result)
    }

    @Test
    fun divPassesWithNonZeroIntegerProducingFloatingPointNumber() {
        // Given
        val x: Integer = Integer.from(12)
        val y: Integer = Integer.from(5)
        // When
        val result: Integer = x / y
        // Then
        val expected: Integer = Integer.from(2)
        assertEquals(expected, result)
    }

    @Test
    fun divPassesWithOneInteger() {
        // Given
        val x: Integer = Integer.from(12)
        val y: Integer = Integer.One
        // When
        val result: Integer = x / y
        // Then
        assertEquals(expected = x, result)
    }

    @Test
    fun divPassesOnZeroIntegerAndWithNonZeroInteger() {
        // Given
        val x: Integer = Integer.Zero
        val y: Integer = Integer.from(3)
        // When
        val result: Integer = x / y
        // Then
        assertEquals(expected = x, result)
    }

    @Test
    fun divFailsWithZeroInteger() {
        // Given
        val x: Integer = Integer.from(12)
        val y: Integer = Integer.Zero
        // When
        val result: IllegalArgumentException = assertFailsWith { x / y }
        // Then
        val expected = "Integer can't be divided by zero."
        assertEquals(expected, result.message)
    }

    @Test
    fun remPassesWithNonZeroInteger() {
        // Given
        val x: Integer = Integer.from(21)
        val y: Integer = Integer.from(5)
        // When
        val result: Integer = x % y
        // Then
        assertEquals(expected = Integer.One, result)
    }

    @Test
    fun remPassesWithOneInteger() {
        // Given
        val x: Integer = Integer.from(21)
        val y: Integer = Integer.One
        // When
        val result: Integer = x % y
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun remPassesOnZeroIntegerAndWithNonZeroInteger() {
        // Given
        val x: Integer = Integer.Zero
        val y: Integer = Integer.from(2)
        // When
        val result: Integer = x % y
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun remFailsWithZeroInteger() {
        // Given
        val x: Integer = Integer.from(21)
        val y: Integer = Integer.Zero
        // When
        val result: IllegalArgumentException = assertFailsWith { x % y }
        // Then
        val expected = "Integer can't be divided by zero."
        assertEquals(expected, result.message)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringPasses() {
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
    fun fromPassesWithLong() {
        // Given
        val number: Long = Long.MAX_VALUE
        // When
        val integer: Integer = Integer.from(number)
        // Then
        assertEquals(expected = "$number", actual = "$integer")
    }

    @Test
    fun fromDecimalPassesWithNonZeroDecimalString() {
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
    fun fromDecimalPassesWithPlusSignedNonZeroDecimalString() {
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
    fun fromDecimalPassesWithMinusSignedNonZeroDecimalString() {
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
    fun fromDecimalPassesWithSingleZeroDecimalString() {
        // Given
        val text = "0"
        // When
        val result: Integer = Integer.fromDecimal(text)
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun fromDecimalPassesWithMultipleZerosDecimalString() {
        // Given
        val text = "000"
        // When
        val result: Integer = Integer.fromDecimal(text)
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun fromDecimalPassesWithPlusSignedZeroDecimalString() {
        // Given
        val text = "+0"
        // When
        val result: Integer = Integer.fromDecimal(text)
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun fromDecimalPassesWithMinusSignedZeroDecimalString() {
        // Given
        val text = "-0"
        // When
        val result: Integer = Integer.fromDecimal(text)
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun fromDecimalPassesWithLeadingZerosInPositiveDecimalString() {
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
    fun fromDecimalPassesWithLeadingZerosInNegativeDecimalString() {
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
    fun fromDecimalFailsWithBlankString() {
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
    fun fromDecimalFailsWithPlusSignString() {
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
    fun fromDecimalFailsWithMinusSignString() {
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
    fun fromDecimalFailsWithNonDecimalString() {
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
    fun fromDecimalOrNullPassesWithNonZeroDecimalString() {
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
    fun fromDecimalOrNullPassesWithPlusSignedNonZeroDecimalString() {
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
    fun fromDecimalOrNullPassesWithMinusSignedNonZeroDecimalString() {
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
    fun fromDecimalOrNullPassesWithSingleZeroDecimalString() {
        // Given
        val text = "0"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun fromDecimalOrNullPassesWithMultipleZerosDecimalString() {
        // Given
        val text = "000"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun fromDecimalOrNullPassesWithPlusSignedZeroDecimalString() {
        // Given
        val text = "+0"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun fromDecimalOrNullPassesWithMinusSignedZeroDecimalString() {
        // Given
        val text = "-0"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun fromDecimalOrNullPassesWithLeadingZerosInPositiveDecimalString() {
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
    fun fromDecimalOrNullPassesWithLeadingZerosInNegativeDecimalString() {
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
    fun fromDecimalOrNullFailsWithBlankString() {
        // Given
        val text = "  "
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        assertNull(result)
    }

    @Test
    fun fromDecimalOrNullFailsWithPlusSignString() {
        // Given
        val text = "+"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        assertNull(result)
    }

    @Test
    fun fromDecimalOrNullFailsWithMinusSignString() {
        // Given
        val text = "-"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        assertNull(result)
    }

    @Test
    fun fromDecimalOrNullFailsWithNonDecimalString() {
        // Given
        val text = "oops"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        assertNull(result)
    }
}
