package org.kotools.types

import kotlin.random.Random
import kotlin.random.nextLong
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class DecimalTest {
    // ------------------------------- Creations -------------------------------

    @Test
    fun fromIntegerRepresentsZeroUniquely(): Unit =
        listOf<Long>(0, +0, -0).forEach {
            val actual: String = Decimal.fromInteger(it)
                .toString()
            assertEquals(expected = "0", actual)
        }

    @Test
    fun fromIntegerDoesNotAddPlusSignForPositiveNumbers(): Unit = repeatTest {
        val number: Long = Random.nextLong(1..Long.MAX_VALUE)
        val actual: Boolean = Decimal.fromInteger(number)
            .toString()
            .startsWith('+')
        assertFalse(actual, message = "input: $number")
    }

    @Test
    fun fromIntegerKeepsSignForNegativeIntegers(): Unit = repeatTest {
        val number: Long = Random.nextLong(Long.MIN_VALUE..-1)
        val actual: Boolean = Decimal.fromInteger(number)
            .toString()
            .startsWith('-')
        assertTrue(actual, message = "input: $number")
    }

    @Test
    fun fromIntegerDoesNotAddFractionalPart(): Unit = repeatTest {
        val number: Long = Random.nextLong()
        val actual: Boolean = Decimal.fromInteger(number)
            .toString()
            .contains('.')
        assertFalse(actual, message = "input: $number")
    }

    @Test
    fun fromDecimalRepresentsZeroUniquely(): Unit = repeatTest {
        val text: String = randomZeroString()
        val actual: Decimal = Decimal.fromDecimal(text)
        val expected: Decimal = Decimal.fromInteger(0)
        assertEquals(expected, actual)
    }

    @Test
    fun fromDecimalRemovesPlusSignFromPositiveDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomPositiveDecimalString()
            val actual: Boolean = Decimal.fromDecimal(text)
                .toString()
                .startsWith('+')
            assertFalse(actual, message = "input: $text")
        }

    @Test
    fun fromDecimalKeepsMinusSignFromNegativeDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomNegativeDecimalString()
            val actual: Boolean = Decimal.fromDecimal(text)
                .toString()
                .startsWith('-')
            assertTrue(actual, message = "input: $text")
        }

    @Test
    fun fromDecimalRemovesLeadingZerosFromNonZeroDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomNonZeroDecimalString()
            val actual: Boolean = Decimal.fromDecimal(text)
                .toString()
                .startsWith('0')
            assertFalse(actual, message = "input: $text")
        }

    @Test
    fun fromDecimalRemovesTrailingZerosFromDecimalNumbers(): Unit = repeatTest {
        val text: String = randomNonZeroDecimalString()
        val actual: Boolean = Decimal.fromDecimal(text)
            .toString()
            .let { '.' !in it || !it.endsWith('0') }
        assertTrue(actual, message = "input: $text")
    }

    @Test
    fun fromDecimalIsStableWithStringRepresentation(): Unit = repeatTest {
        val text: String = randomNonZeroDecimalString()
        val x: Decimal = Decimal.fromDecimal(text)
        val y: Decimal = Decimal.fromDecimal("$x")
        assertEquals(x, y, message = "input: $text")
    }

    @Test
    fun fromDecimalThrowsExceptionWithMalformedDecimalStrings(): Unit =
        repeatTest {
            val text: String = randomMalformedDecimalString()
            val exception: IllegalArgumentException = assertFailsWith {
                Decimal.fromDecimal(text)
            }
            val actual: String? = exception.message
            val expected = "\"$text\" is not a valid decimal number."
            assertEquals(expected, actual)
        }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun equalsAndHashCodeWithSameDecimalNumbers() {
        // Given
        val x: Decimal = Decimal.fromInteger(1)
        val y: Decimal = Decimal.fromDecimal("+0001.00")
        // When
        val equality: Boolean = x == y
        val hashConformity: Boolean = x.hashCode() == y.hashCode()
        // Then
        assertTrue(equality, "$x and $y must be equal.")
        assertTrue(hashConformity, "Hash code of $x and $y must be equal.")
    }

    @Test
    fun equalsAndHashCodeWithDifferentDecimalNumbers() {
        // Given
        val x: Decimal = Decimal.fromDecimal("0.0001")
        val y: Decimal = Decimal.fromDecimal("0.0000001")
        // When
        val equality: Boolean = x == y
        val hashConformity: Boolean = x.hashCode() == y.hashCode()
        // Then
        assertFalse(equality, "$x and $y must be different.")
        assertFalse(hashConformity, "Hash code of $x and $y must be different.")
    }

    @Test
    fun equalsAndHashCodeWithAnotherTypeThanDecimal() {
        // Given
        val x: Decimal = Decimal.fromDecimal("0.0001")
        val y: String = x.toString()
        // When
        val equality: Boolean = x.equals(y)
        val hashConformity: Boolean = x.hashCode() == y.hashCode()
        // Then
        assertFalse(equality, "$x (Decimal) and $y (String) must be different.")
        assertFalse(hashConformity, "Hash code of $x and $y must be different.")
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOnZero() {
        // Given
        val zero: Decimal = Decimal.fromDecimal("-00.00000000")
        // When
        val actual: String = zero.toString()
        // Then
        assertEquals(expected = "0", actual)
    }

    @Test
    fun toStringOnPositiveDecimalNumber() {
        // Given
        val number: Decimal = Decimal.fromDecimal("+000123456.789000")
        // When
        val actual: String = number.toString()
        // Then
        assertEquals(expected = "123456.789", actual)
    }

    @Test
    fun toStringOnNegativeDecimalNumber() {
        // Given
        val number: Decimal = Decimal.fromDecimal("-0.0001230")
        // When
        val actual: String = number.toString()
        // Then
        assertEquals(expected = "-0.000123", actual)
    }
}
