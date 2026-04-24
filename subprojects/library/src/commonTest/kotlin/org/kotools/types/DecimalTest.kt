package org.kotools.types

import kotlin.random.Random
import kotlin.random.nextLong
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
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
    fun equalsAndHashCodeWithSameDecimalNumbers(): Unit = repeatTest {
        val text: String = randomNonZeroDecimalString()
        val x: Decimal = Decimal.fromDecimal(text)
        val y: Decimal = Decimal.fromDecimal(text)
        val message = "Inputs: x = $x, y = $y"
        assertEquals(x, y, message)
        assertEquals(x.hashCode(), y.hashCode(), message)
    }

    @Test
    fun equalsAndHashCodeWithDifferentDecimalNumbers(): Unit = repeatTest {
        val x: Decimal = randomPositiveDecimalString()
            .let(Decimal.Companion::fromDecimal)
        val y: Decimal = randomNegativeDecimalString()
            .let(Decimal.Companion::fromDecimal)
        val message = "Inputs: x = $x, y = $y"
        assertNotEquals(x, y, message)
        assertNotEquals(x.hashCode(), y.hashCode(), message)
    }

    @Test
    fun equalsAndHashCodeWithAnotherTypeThanDecimal(): Unit = repeatTest {
        val y: String = randomNonZeroDecimalString()
        val x: Decimal = Decimal.fromDecimal(y)
        val message = "Inputs: x = $x, y = $y"
        assertNotEquals(x, y as Any, message)
        assertNotEquals(x.hashCode(), y.hashCode(), message)
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
