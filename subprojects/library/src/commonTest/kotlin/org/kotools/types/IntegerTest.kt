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
        val expected: Integer = Integer.parse("18446744073709551614")
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
        val expected: Integer = Integer.parse("-18446744073709551614")
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
        val expected: Integer = Integer.parse("9223372036854775807000")
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
    fun parsePassesWithNonZeroDecimalString() {
        // Given
        val number = 123456L
        val text = "$number"
        // When
        val result: Integer = Integer.parse(text)
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun parsePassesWithPlusSignedNonZeroDecimalString() {
        // Given
        val number = 123456L
        val text = "+$number"
        // When
        val result: Integer = Integer.parse(text)
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun parsePassesWithMinusSignedNonZeroDecimalString() {
        // Given
        val number: Long = -123456L
        val text = "$number"
        // When
        val result: Integer = Integer.parse(text)
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun parsePassesWithSingleZeroDecimalString() {
        // Given
        val text = "0"
        // When
        val result: Integer = Integer.parse(text)
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun parsePassesWithMultipleZerosDecimalString() {
        // Given
        val text = "000"
        // When
        val result: Integer = Integer.parse(text)
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun parsePassesWithPlusSignedZeroDecimalString() {
        // Given
        val text = "+0"
        // When
        val result: Integer = Integer.parse(text)
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun parsePassesWithMinusSignedZeroDecimalString() {
        // Given
        val text = "-0"
        // When
        val result: Integer = Integer.parse(text)
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun parsePassesWithLeadingZerosInPositiveDecimalString() {
        // Given
        val number = 123L
        val text = "000$number"
        // When
        val result: Integer = Integer.parse(text)
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun parsePassesWithLeadingZerosInNegativeDecimalString() {
        // Given
        val number = 123L
        val text = "-000$number"
        // When
        val result: Integer = Integer.parse(text)
        // Then
        val expected: Integer = Integer.from(-number)
        assertEquals(expected, result)
    }

    @Test
    fun parseFailsWithBlankString() {
        // Given
        val text = " "
        // When
        val result: IllegalArgumentException = assertFailsWith {
            Integer.parse(text)
        }
        // Then
        val expected = "Integer should not be blank"
        assertEquals(expected, result.message)
    }

    @Test
    fun parseFailsWithPlusSignString() {
        // Given
        val text = "+"
        // When
        val result: IllegalArgumentException = assertFailsWith {
            Integer.parse(text)
        }
        // Then
        val expected: String = Integer.syntaxErrorIn(text)
        assertEquals(expected, result.message)
    }

    @Test
    fun parseFailsWithMinusSignString() {
        // Given
        val text = "-"
        // When
        val result: IllegalArgumentException = assertFailsWith {
            Integer.parse(text)
        }
        // Then
        val expected: String = Integer.syntaxErrorIn(text)
        assertEquals(expected, result.message)
    }

    @Test
    fun parseFailsWithNonDecimalString() {
        // Given
        val text = "oops"
        // When
        val result: IllegalArgumentException = assertFailsWith {
            Integer.parse(text)
        }
        // Then
        val expected: String = Integer.syntaxErrorIn(text)
        assertEquals(expected, result.message)
    }

    @Test
    fun parseOrNullPassesWithNonZeroDecimalString() {
        // Given
        val number = 123456L
        val text = "$number"
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun parseOrNullPassesWithPlusSignedNonZeroDecimalString() {
        // Given
        val number = 123456L
        val text = "+$number"
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun parseOrNullPassesWithMinusSignedNonZeroDecimalString() {
        // Given
        val number: Long = -123456L
        val text = "$number"
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun parseOrNullPassesWithSingleZeroDecimalString() {
        // Given
        val text = "0"
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun parseOrNullPassesWithMultipleZerosDecimalString() {
        // Given
        val text = "000"
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun parseOrNullPassesWithPlusSignedZeroDecimalString() {
        // Given
        val text = "+0"
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun parseOrNullPassesWithMinusSignedZeroDecimalString() {
        // Given
        val text = "-0"
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        assertEquals(expected = Integer.Zero, result)
    }

    @Test
    fun parseOrNullPassesWithLeadingZerosInPositiveDecimalString() {
        // Given
        val number = 123L
        val text = "000$number"
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        val expected: Integer = Integer.from(number)
        assertEquals(expected, result)
    }

    @Test
    fun parseOrNullPassesWithLeadingZerosInNegativeDecimalString() {
        // Given
        val number = 123L
        val text = "-000$number"
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        val expected: Integer = Integer.from(-number)
        assertEquals(expected, result)
    }

    @Test
    fun parseOrNullFailsWithBlankString() {
        // Given
        val text = "  "
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        assertNull(result)
    }

    @Test
    fun parseOrNullFailsWithPlusSignString() {
        // Given
        val text = "+"
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        assertNull(result)
    }

    @Test
    fun parseOrNullFailsWithMinusSignString() {
        // Given
        val text = "-"
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        assertNull(result)
    }

    @Test
    fun parseOrNullFailsWithNonDecimalString() {
        // Given
        val text = "oops"
        // When
        val result: Integer? = Integer.parseOrNull(text)
        // Then
        assertNull(result)
    }
}
