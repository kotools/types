package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.negativeIntegerString
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
    fun parseNormalizesZero(): Unit = repeatTest {
        val value: String = Random.zeroString()

        val actual: Integer = Integer.parse(value)

        val expected: Integer = Integer.of(0)
        assertEquals(expected, actual, message = "Input: \"$value\"")
    }

    @Test
    fun parseIgnoresPlusSignFromPositiveValues(): Unit = repeatTest {
        val value: String = Random.positiveIntegerString()

        val integer: Integer = Integer.parse(value)

        val actual: String = integer.toString()
        assertFalse(
            "\"$actual\" must not start with plus sign (input: \"$value\")."
        ) { actual.startsWith('+') }
    }

    @Test
    fun parsePreservesRepresentationOfNegativeValues(): Unit = repeatTest {
        val value: String = Random.negativeIntegerString()

        val integer: Integer = Integer.parse(value)

        val actual: String = integer.toString()
        assertEquals(expected = value, actual, message = "Input: \"$value\"")
    }

    @Test
    fun parseRemovesLeadingZerosFromNonZeroValues(): Unit = repeatTest {
        val value: String = Random.nonZeroIntegerStringWithLeadingZeros()

        val integer: Integer = Integer.parse(value)

        val actual: String = integer.toString()
        assertTrue(
            "\"$actual\" must not have leading zeros (input: \"$value\")."
        ) { actual.first(Char::isDigit) != '0' }
    }

    @Test
    fun parseThrowsExceptionWithNonIntegerValue(): Unit = repeatTest {
        val value: String = Random.nonIntegerString()

        val message = "Input: \"$value\""
        val exception: NumberFormatException =
            assertFailsWith(message) { Integer.parse(value) }

        val expected = "\"$value\" is not a valid integer."
        assertEquals(expected, actual = exception.message, message)
    }

    @Test
    fun fromDecimalOrNullWithNonZeroDecimalString() {
        // Given
        val number = 123456L
        val text = "$number"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        val expected: Integer = Integer.of(number)
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
        val expected: Integer = Integer.of(number)
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
        val expected: Integer = Integer.of(number)
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalOrNullWithSingleZeroDecimalString() {
        // Given
        val text = "0"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        val expected: Integer = Integer.zero()
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalOrNullWithMultipleZerosDecimalString() {
        // Given
        val text = "000"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        val expected: Integer = Integer.zero()
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalOrNullWithPlusSignedZeroDecimalString() {
        // Given
        val text = "+0"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        val expected: Integer = Integer.zero()
        assertEquals(expected, result)
    }

    @Test
    fun fromDecimalOrNullWithMinusSignedZeroDecimalString() {
        // Given
        val text = "-0"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        val expected: Integer = Integer.zero()
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
        val expected: Integer = Integer.of(number)
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
        val expected: Integer = Integer.of(-number)
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
        val x: Integer = Integer.zero()
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
        val y: Integer = Integer.zero()
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
        val y: Integer = Integer.zero()
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
        val y: Integer = Integer.zero()
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
        val integer: Integer = Integer.of(number)
        // When
        val result = "$integer"
        // Then
        assertEquals(expected = "$number", result)
    }
}
