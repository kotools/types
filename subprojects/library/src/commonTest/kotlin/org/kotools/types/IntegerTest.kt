package org.kotools.types

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerTest {
    // ------------------------------- Creations -------------------------------

    @Test
    fun fromUsesStringRepresentationOfSpecifiedNumber(): Unit = repeatTest {
        val number: Long = Random.nextLong()
        val actual: String = Integer.from(number)
            .toString()
        val expected: String = number.toString()
        assertEquals(expected, actual, message = "Input: $number")
    }

    @Test
    fun fromDecimalRepresentsZeroUniquely(): Unit = repeatTest {
        val text: String = randomZeroIntegerString()
        val actual: Integer = Integer.fromDecimal(text)
        val expected: Integer = Integer.from(0)
        assertEquals(expected, actual, message = "Input: $text")
    }

    @Test
    fun fromDecimalRemovesPlusSignFromPositiveInteger(): Unit = repeatTest {
        val text: String = randomPositiveIntegerString()
        val actual: Boolean = Integer.fromDecimal(text)
            .toString()
            .startsWith('+')
        assertFalse(actual, message = "Input: $text")
    }

    @Test
    fun fromDecimalKeepsMinusSignFromNegativeInteger(): Unit = repeatTest {
        val text: String = randomNegativeIntegerString()
        val actual: Boolean = Integer.fromDecimal(text)
            .toString()
            .startsWith('-')
        assertTrue(actual, message = "Input: $text")
    }

    @Test
    fun fromDecimalRemovesLeadingZerosFromNonZeroInteger(): Unit = repeatTest {
        val text: String = randomNonZeroIntegerString()
        val actual: Boolean = Integer.fromDecimal(text)
            .toString()
            .startsWith('0')
        assertFalse(actual, message = "Input: $text")
    }

    @Test
    fun fromDecimalIsStableWithStringRepresentation(): Unit = repeatTest {
        val text: String = randomNonZeroIntegerString()
        val x: Integer = Integer.fromDecimal(text)
        val y: Integer = Integer.fromDecimal("$x")
        assertEquals(x, y, message = "Input: $text")
    }

    @Test
    fun fromDecimalThrowsExceptionWithMalformedIntegerString(): Unit =
        repeatTest {
            val text: String = randomMalformedIntegerString()
            val exception: IllegalArgumentException = assertFailsWith {
                Integer.fromDecimal(text)
            }
            val actual: String? = exception.message
            val expected = "\"$text\" is not a valid integer."
            assertEquals(expected, actual, message = "Input: $text")
        }

    @Test
    fun fromDecimalOrNullRepresentsZeroUniquely(): Unit = repeatTest {
        val text: String = randomZeroIntegerString()
        val actual: Integer? = Integer.fromDecimalOrNull(text)
        val expected: Integer = Integer.from(0)
        assertEquals(expected, actual, message = "Input: $text")
    }

    @Test
    fun fromDecimalOrNullRemovesPlusSignFromPositiveInteger(): Unit =
        repeatTest {
            val text: String = randomPositiveIntegerString()
            val integer: Integer? = Integer.fromDecimalOrNull(text)
            val message = "Input: $text"
            assertNotNull(integer, message)
            assertTrue(message) { !"$integer".startsWith('+') }
        }

    @Test
    fun fromDecimalOrNullKeepsMinusSignFromNegativeInteger(): Unit =
        repeatTest {
            val text: String = randomNegativeIntegerString()
            val integer: Integer? = Integer.fromDecimalOrNull(text)
            val message = "Input: $text"
            assertNotNull(integer, message)
            assertTrue(message) { "$integer".startsWith('-') }
        }

    @Test
    fun fromDecimalOrNullRemovesLeadingZerosFromNonZeroInteger(): Unit =
        repeatTest {
            val text: String = randomNonZeroIntegerString()
            val integer: Integer? = Integer.fromDecimalOrNull(text)
            val message = "Input: $text"
            assertNotNull(integer, message)
            assertTrue(message) { !"$integer".startsWith('0') }
        }

    @Test
    fun fromDecimalOrNullIsStableWithStringRepresentation(): Unit = repeatTest {
        val text: String = randomNonZeroIntegerString()
        val x: Integer? = Integer.fromDecimalOrNull(text)
        val y: Integer? = Integer.fromDecimalOrNull("$x")
        assertEquals(x, y, message = "Input: $text")
    }

    @Test
    fun fromDecimalOrNullReturnsNullWithMalformedIntegerString(): Unit =
        repeatTest {
            val text: String = randomMalformedIntegerString()
            val actual: Integer? = Integer.fromDecimalOrNull(text)
            assertNull(actual, message = "Input: $text")
        }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun equalityReturnsTrueWithSameNumber(): Unit = repeatTest {
        val x: Integer = Integer.random()
        val y: Integer = Integer.fromDecimal("$x")
        val message = "Inputs: x = $x, y = $y"
        assertEquals(x, y, message)
        assertEquals(x.hashCode(), y.hashCode(), message)
        assertTrue(message) { x.compareTo(y) == 0 }
    }

    @Test
    fun equalityReturnsFalseWithDifferentNumbers(): Unit = repeatTest {
        val x: Integer = Integer.random()
        val y: Integer = Integer.randomExcept(x)
        val message = "Inputs: x = $x, y = $y"
        assertNotEquals(x, y, message)
        assertNotEquals(x.hashCode(), y.hashCode(), message)
    }

    @Test
    fun equalityReturnsFalseWithAnotherTypeThanInteger(): Unit = repeatTest {
        val x: Integer = Integer.random()
        val y = "$x"
        val message = "Inputs: x = $x, y = $y"
        assertNotEquals(x, y as Any, message)
        assertNotEquals(x.hashCode(), y.hashCode(), message)
    }

    @Test
    fun compareToReturnsPositiveNumberWithLesserInteger(): Unit = repeatTest {
        val x: Integer = Integer.fromDecimal(randomPositiveIntegerString())
        val y: Integer = Integer.fromDecimal(randomNegativeIntegerString())
        assertTrue(message = "Inputs: x = $x, y = $y") { x > y }
    }

    @Test
    fun compareToReturnsNegativeNumberWithGreaterInteger(): Unit = repeatTest {
        val x: Integer = Integer.fromDecimal(randomNegativeIntegerString())
        val y: Integer = Integer.fromDecimal(randomPositiveIntegerString())
        assertTrue(message = "Inputs: x = $x, y = $y") { x < y }
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinusOnZero() {
        // Given
        val x: Integer = Integer.zero()
        // When
        val result: Integer = -x
        // Then
        assertSame(expected = x, result)
    }

    @Test
    fun unaryMinusOnPositiveInteger() {
        // Given
        val x: Integer = Integer.from(123456789)
        // When
        val result: Integer = -x
        // Then
        val expected: Integer = Integer.from(-123456789)
        assertEquals(expected, result)
    }

    @Test
    fun unaryMinusOnNegativeInteger() {
        // Given
        val x: Integer = Integer.from(-123456789)
        // When
        val result: Integer = -x
        // Then
        val expected: Integer = Integer.from(123456789)
        assertEquals(expected, result)
    }

    @Test
    fun plus() {
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
    fun plusOnZero() {
        // Given
        val x: Integer = Integer.zero()
        val y: Integer = Integer.from(Long.MAX_VALUE)
        // When
        val result: Integer = x + y
        // Then
        assertSame(expected = y, result)
    }

    @Test
    fun plusWithZero() {
        // Given
        val x: Integer = Integer.from(Long.MAX_VALUE)
        val y: Integer = Integer.zero()
        // When
        val result: Integer = x + y
        // Then
        assertSame(expected = x, result)
    }

    @Test
    fun minus() {
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
    fun minusOnZero() {
        // Given
        val x: Integer = Integer.zero()
        val y: Integer = Integer.from(123)
        // When
        val result: Integer = x - y
        // Then
        val expected: Integer = -y
        assertEquals(expected, result)
    }

    @Test
    fun minusWithZero() {
        // Given
        val x: Integer = Integer.from(123)
        val y: Integer = Integer.zero()
        // When
        val result: Integer = x - y
        // Then
        assertSame(expected = x, result)
    }

    @Test
    fun times() {
        // Given
        val x: Integer = Integer.from(9223372036854775807)
        val y: Integer = Integer.from(1_000)
        // When
        val result: Integer = x * y
        // Then
        val expected: Integer = Integer.fromDecimal("9223372036854775807000")
        assertEquals(expected, result)
    }

    @Test
    fun timesOnZero() {
        // Given
        val x: Integer = Integer.zero()
        val y: Integer = Integer.from(Long.MAX_VALUE)
        // When
        val result: Integer = x * y
        // Then
        assertSame(expected = x, result)
    }

    @Test
    fun timesOnOne() {
        // Given
        val x: Integer = Integer.one()
        val y: Integer = Integer.from(Long.MAX_VALUE)
        // When
        val result: Integer = x * y
        // Then
        assertSame(expected = y, result)
    }

    @Test
    fun timesWithZero() {
        // Given
        val x: Integer = Integer.from(Long.MAX_VALUE)
        val y: Integer = Integer.zero()
        // When
        val result: Integer = x * y
        // Then
        assertSame(expected = y, result)
    }

    @Test
    fun timesWithOne() {
        // Given
        val x: Integer = Integer.from(Long.MAX_VALUE)
        val y: Integer = Integer.one()
        // When
        val result: Integer = x * y
        // Then
        assertSame(expected = x, result)
    }

    @Test
    fun divOnZeroWithNonZeroInteger() {
        // Given
        val x: Integer = Integer.zero()
        val y: Integer = Integer.from(42)
        // When
        val result: Integer = x / y
        // Then
        assertSame(expected = x, result)
    }

    @Test
    fun divWithOne() {
        // Given
        val x: Integer = Integer.from(42)
        val y: Integer = Integer.one()
        // When
        val result: Integer = x / y
        // Then
        assertSame(expected = x, result)
    }

    @Test
    fun divWithNonZeroInteger() {
        // Given
        val x: Integer = Integer.from(42)
        val y: Integer = Integer.from(5)
        // When
        val result: Integer = x / y
        // Then
        val expected: Integer = Integer.from(8)
        assertEquals(expected, result)
    }

    @Test
    fun divWithZero() {
        // Given
        val x: Integer = Integer.from(42)
        val y: Integer = Integer.zero()
        // When
        val result: ArithmeticException = assertFailsWith { x / y }
        // Then
        val expected = "Integer can't be divided by zero."
        assertEquals(expected, result.message)
    }

    @Test
    fun divOrNullOnZeroWithNonZeroInteger() {
        // Given
        val x: Integer = Integer.zero()
        val y: Integer = Integer.from(2)
        // When
        val result: Integer? = x.divOrNull(y)
        // Then
        assertSame(expected = x, result)
    }

    @Test
    fun divOrNullWithOne() {
        // Given
        val x: Integer = Integer.from(42)
        val y: Integer = Integer.one()
        // When
        val result: Integer? = x.divOrNull(y)
        // Then
        assertSame(expected = x, result)
    }

    @Test
    fun divOrNullWithNonZeroInteger() {
        // Given
        val x: Integer = Integer.from(42)
        val y: Integer = Integer.from(5)
        // When
        val result: Integer? = x.divOrNull(y)
        // Then
        val expected: Integer = Integer.from(8)
        assertEquals(expected, result)
    }

    @Test
    fun divOrNullWithZero() {
        // Given
        val x: Integer = Integer.from(42)
        val y: Integer = Integer.zero()
        // When
        val result: Integer? = x.divOrNull(y)
        // Then
        assertNull(result)
    }

    @Test
    fun remOnZeroWithNonZeroInteger() {
        // Given
        val x: Integer = Integer.zero()
        val y: Integer = Integer.from(2)
        // When
        val result: Integer = x % y
        // Then
        assertSame(expected = x, result)
    }

    @Test
    fun remWithOne() {
        // Given
        val x: Integer = Integer.from(42)
        val y: Integer = Integer.one()
        // When
        val result: Integer = x % y
        // Then
        val expected: Integer = Integer.zero()
        assertEquals(expected, result)
    }

    @Test
    fun remWithNonZeroInteger() {
        // Given
        val x: Integer = Integer.from(42)
        val y: Integer = Integer.from(5)
        // When
        val result: Integer = x % y
        // Then
        val expected: Integer = Integer.from(2)
        assertEquals(expected, result)
    }

    @Test
    fun remWithZero() {
        // Given
        val x: Integer = Integer.from(42)
        val y: Integer = Integer.zero()
        // When
        val result: ArithmeticException = assertFailsWith { x % y }
        // Then
        val expected = "Integer can't be divided by zero."
        assertEquals(expected, result.message)
    }

    @Test
    fun remOrNullOnZeroWithNonZeroInteger() {
        // Given
        val x: Integer = Integer.zero()
        val y: Integer = Integer.from(2)
        // When
        val result: Integer? = x.remOrNull(y)
        // Then
        assertSame(expected = x, result)
    }

    @Test
    fun remOrNullWithOne() {
        // Given
        val x: Integer = Integer.from(42)
        val y: Integer = Integer.one()
        // When
        val result: Integer? = x.remOrNull(y)
        // Then
        val expected: Integer = Integer.zero()
        assertEquals(expected, result)
    }

    @Test
    fun remOrNullWithNonZeroInteger() {
        // Given
        val x: Integer = Integer.from(42)
        val y: Integer = Integer.from(5)
        // When
        val result: Integer? = x.remOrNull(y)
        // Then
        val expected: Integer = Integer.from(2)
        assertEquals(expected, result)
    }

    @Test
    fun remOrNullWithZero() {
        // Given
        val x: Integer = Integer.from(42)
        val y: Integer = Integer.zero()
        // When
        val result: Integer? = x.remOrNull(y)
        // Then
        assertNull(result)
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

    @Test
    fun toSignedStringOnZero() {
        // Given
        val zero: Integer = Integer.zero()
        // When
        val result: String = zero.toSignedString()
        // Then
        assertEquals(expected = "0", result)
    }

    @Test
    fun toSignedStringOnPositiveInteger() {
        // Given
        val number = 123456789L
        val integer: Integer = Integer.from(number)
        // When
        val result: String = integer.toSignedString()
        // Then
        assertEquals(expected = "+$number", result)
    }

    @Test
    fun toSignedStringOnNegativeInteger() {
        // Given
        val number = -123456789L
        val integer: Integer = Integer.from(number)
        // When
        val result: String = integer.toSignedString()
        // Then
        assertEquals(expected = "$number", result)
    }
}
