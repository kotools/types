package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.nonIntegerString
import org.kotools.types.nonZeroIntegerStringWithLeadingZeros
import org.kotools.types.positiveIntegerString
import org.kotools.types.repeatTest
import org.kotools.types.zeroString
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerTest {
    // ------------------------------- Creations -------------------------------

    @Test
    fun ofPreservesLongRepresentation(): Unit = repeatTest {
        val value: Long = Random.nextLong()

        val integer: Integer = Integer.of(value)

        val actual: String = integer.toString()
        val expected: String = value.toString()
        assertEquals(expected, actual, message = "Input: $value")
    }

    @Test
    fun parsingNormalizesZero(): Unit = repeatTest {
        val value: String = Random.zeroString()

        val integer: Integer = Integer.parse(value)
        val safeInteger: Integer? = Integer.parseOrNull(value)

        val expected: Integer = Integer.of(0)
        val message = "Input: \"$value\""
        assertEquals(expected, actual = integer, message)
        assertEquals(expected, actual = safeInteger, message)
    }

    @Test
    fun parsingRemovesPlusSign(): Unit = repeatTest {
        val value: String = Random.positiveIntegerString()

        val integer: Integer = Integer.parse(value)
        val safeInteger: Integer? = Integer.parseOrNull(value)

        val actual: String = integer.toString()
        assertFalse(
            "\"$actual\" must not start with plus sign (input: \"$value\")."
        ) { actual.startsWith('+') }
        assertEquals(integer, safeInteger, message = "Input: \"$value\"")
    }

    @Test
    fun parsingRemovesLeadingZerosFromNonZeroInteger(): Unit = repeatTest {
        val value: String = Random.nonZeroIntegerStringWithLeadingZeros()

        val integer: Integer = Integer.parse(value)
        val safeInteger: Integer? = Integer.parseOrNull(value)

        val actual: String = integer.toString()
        assertTrue(
            "\"$actual\" must not have leading zeros (input: \"$value\")."
        ) { actual.first(Char::isDigit) != '0' }
        assertEquals(integer, safeInteger, message = "Input: \"$value\"")
    }

    @Test
    fun parsingPreservesCanonicalRepresentation(): Unit = repeatTest {
        val value: String = Random.nextLong()
            .toString()

        val integer: Integer = Integer.parse(value)
        val safeInteger: Integer? = Integer.parseOrNull(value)

        val actual: String = integer.toString()
        val message = "Input: \"$value\""
        assertEquals(expected = value, actual, message)
        assertEquals(integer, safeInteger, message)
    }

    @Test
    fun parsingFailsWithNonintegerString(): Unit = repeatTest {
        val value: String = Random.nonIntegerString()

        val message = "Input: \"$value\""
        val exception: NumberFormatException =
            assertFailsWith(message) { Integer.parse(value) }
        val expected = "\"$value\" is not a valid integer."
        assertEquals(expected, actual = exception.message, message)

        val safeInteger: Integer? = Integer.parseOrNull(value)
        assertNull(safeInteger, message)
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun equalsWithIntegerHavingSameValue() {
        // Given
        val number: Long = Long.MAX_VALUE
        val integer: Integer = Integer.of(number)
        val other: Integer = Integer.of(number)
        // When
        val result: Boolean = integer == other
        // Then
        assertTrue(result)
    }

    @Test
    fun equalsWithAnotherTypeThanInteger() {
        // Given
        val number: Long = Long.MAX_VALUE
        val integer: Integer = Integer.of(number)
        // When
        val result: Boolean = integer.equals(other = number)
        // Then
        assertFalse(result)
    }

    @Test
    fun equalsWithIntegerHavingAnotherValue() {
        // Given
        val integer: Integer = Integer.of(Long.MAX_VALUE)
        val other: Integer = Integer.of(Long.MIN_VALUE)
        // When
        val result: Boolean = integer == other
        // Then
        assertFalse(result)
    }

    @Test
    fun hashCodeReturnsSameValueForEqualIntegers() {
        // Given
        val number: Long = Long.MAX_VALUE
        val integer: Integer = Integer.of(number)
        // When
        val result: Int = integer.hashCode()
        // Then
        val expected: Int = Integer.of(number)
            .hashCode()
        assertEquals(expected, result)
    }

    @Test
    fun compareToWithSameInteger() {
        // Given
        val number: Long = Long.MAX_VALUE
        val x: Integer = Integer.of(number)
        val y: Integer = Integer.of(number)
        // When
        val result: Int = x.compareTo(y)
        // Then
        assertEquals(expected = 0, result)
    }

    @Test
    fun compareToWithGreaterInteger() {
        // Given
        val x: Integer = Integer.of(Long.MIN_VALUE)
        val y: Integer = Integer.of(Long.MAX_VALUE)
        // When
        val result: Int = x.compareTo(y)
        // Then
        assertTrue(result < 0)
    }

    @Test
    fun compareToWithLesserInteger() {
        // Given
        val x: Integer = Integer.of(Long.MAX_VALUE)
        val y: Integer = Integer.of(Long.MIN_VALUE)
        // When
        val result: Int = x.compareTo(y)
        // Then
        assertTrue(result > 0)
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinusOnZero() {
        // Given
        val x: Integer = Integer.of(0)
        // When
        val result: Integer = -x
        // Then
        assertEquals(expected = x, result)
    }

    @Test
    fun unaryMinusOnPositiveInteger() {
        // Given
        val x: Integer = Integer.of(123456789)
        // When
        val result: Integer = -x
        // Then
        val expected: Integer = Integer.of(-123456789)
        assertEquals(expected, result)
    }

    @Test
    fun unaryMinusOnNegativeInteger() {
        // Given
        val x: Integer = Integer.of(-123456789)
        // When
        val result: Integer = -x
        // Then
        val expected: Integer = Integer.of(123456789)
        assertEquals(expected, result)
    }

    @Test
    fun plus() {
        // Given
        val number = 9223372036854775807
        val x: Integer = Integer.of(number)
        val y: Integer = Integer.of(number)
        // When
        val result: Integer = x + y
        // Then
        val expected: Integer = Integer.parse("18446744073709551614")
        assertEquals(expected, result)
    }

    @Test
    fun minus() {
        // Given
        val x: Integer = Integer.of(-9223372036854775807)
        val y: Integer = Integer.of(9223372036854775807)
        // When
        val result: Integer = x - y
        // Then
        val expected: Integer = Integer.parse("-18446744073709551614")
        assertEquals(expected, result)
    }

    @Test
    fun times() {
        // Given
        val x: Integer = Integer.of(9223372036854775807)
        val y: Integer = Integer.of(1_000)
        // When
        val result: Integer = x * y
        // Then
        val expected: Integer = Integer.parse("9223372036854775807000")
        assertEquals(expected, result)
    }

    @Test
    fun divWithNonZeroInteger() {
        // Given
        val x: Integer = Integer.of(42)
        val y: Integer = Integer.of(5)
        // When
        val result: Integer = x / y
        // Then
        val expected: Integer = Integer.of(8)
        assertEquals(expected, result)
    }

    @Test
    fun divWithZero() {
        // Given
        val x: Integer = Integer.of(42)
        val y: Integer = Integer.of(0)
        // When
        val result: ArithmeticException = assertFailsWith { x / y }
        // Then
        val expected = "Integer can't be divided by zero."
        assertEquals(expected, result.message)
    }

    @Test
    fun divOrNullWithNonZeroInteger() {
        // Given
        val x: Integer = Integer.of(42)
        val y: Integer = Integer.of(5)
        // When
        val result: Integer? = x.divOrNull(y)
        // Then
        val expected: Integer = Integer.of(8)
        assertEquals(expected, result)
    }

    @Test
    fun divOrNullWithZero() {
        // Given
        val x: Integer = Integer.of(42)
        val y: Integer = Integer.of(0)
        // When
        val result: Integer? = x.divOrNull(y)
        // Then
        assertNull(result)
    }

    @Test
    fun remWithNonZeroInteger() {
        // Given
        val x: Integer = Integer.of(42)
        val y: Integer = Integer.of(5)
        // When
        val result: Integer = x % y
        // Then
        val expected: Integer = Integer.of(2)
        assertEquals(expected, result)
    }

    @Test
    fun remWithZero() {
        // Given
        val x: Integer = Integer.of(42)
        val y: Integer = Integer.of(0)
        // When
        val result: ArithmeticException = assertFailsWith { x % y }
        // Then
        val expected = "Integer can't be divided by zero."
        assertEquals(expected, result.message)
    }

    @Test
    fun remOrNullWithNonZeroInteger() {
        // Given
        val x: Integer = Integer.of(42)
        val y: Integer = Integer.of(5)
        // When
        val result: Integer? = x.remOrNull(y)
        // Then
        val expected: Integer = Integer.of(2)
        assertEquals(expected, result)
    }

    @Test
    fun remOrNullWithZero() {
        // Given
        val x: Integer = Integer.of(42)
        val y: Integer = Integer.of(0)
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
        val integer: Integer = Integer.of(number)
        // When
        val result = "$integer"
        // Then
        assertEquals(expected = "$number", result)
    }
}
