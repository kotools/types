package org.kotools.types

import kotools.types.ExperimentalKotoolsTypesApi
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class DecimalTest {
    // ------------------------------- Creations -------------------------------

    @Test
    fun ofLongUsesStringRepresentationOfSpecifiedValue(): Unit =
        repeatTest {
            val number: Long = Random.nextLong()
            val actual: String = Decimal.of(number)
                .toString()
            val expected: String = number.toString()
            assertEquals(expected, actual, message = "Input: $number")
        }

    @Test
    fun fromStringRepresentsZeroUniquely(): Unit = repeatTest {
        val text: String = randomZeroDecimalString()
        val actual: Decimal = Decimal.fromString(text)
        val expected: Decimal = Decimal.of(0)
        assertEquals(expected, actual, message = "Input: $text")
    }

    @Test
    fun fromStringRemovesPlusSignFromPositiveDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomPositiveDecimalString()
            val actual: Boolean = Decimal.fromString(text)
                .toString()
                .startsWith('+')
            assertFalse(actual, message = "Input: $text")
        }

    @Test
    fun fromStringKeepsMinusSignFromNegativeDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomNegativeDecimalString()
            val actual: Boolean = Decimal.fromString(text)
                .toString()
                .startsWith('-')
            assertTrue(actual, message = "Input: $text")
        }

    @Test
    fun fromStringRemovesLeadingZerosFromNonZeroDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomNonZeroDecimalString()
            val actual: Boolean = Decimal.fromString(text)
                .toString()
                .let { it.startsWith("0.") || !it.startsWith('0') }
            assertTrue(actual, message = "Input: $text")
        }

    @Test
    fun fromStringRemovesTrailingZerosFromDecimalNumbers(): Unit = repeatTest {
        val text: String = randomNonZeroDecimalString()
        val actual: Boolean = Decimal.fromString(text)
            .toString()
            .let { '.' !in it || !it.endsWith('0') }
        assertTrue(actual, message = "Input: $text")
    }

    @Test
    fun fromStringIsStableWithStringRepresentation(): Unit = repeatTest {
        val text: String = randomNonZeroDecimalString()
        val x: Decimal = Decimal.fromString(text)
        val y: Decimal = Decimal.fromString("$x")
        assertEquals(x, y, message = "Input: $text")
    }

    @Test
    fun fromStringThrowsExceptionWithMalformedDecimalStrings(): Unit =
        repeatTest {
            val text: String = randomMalformedDecimalString()
            val exception: IllegalArgumentException = assertFailsWith {
                Decimal.fromString(text)
            }
            val actual: String? = exception.message
            val expected = "\"$text\" is not a valid decimal number."
            assertEquals(expected, actual)
        }

    @Test
    fun fromStringOrNullRepresentsZeroUniquely(): Unit = repeatTest {
        val text: String = randomZeroDecimalString()
        val actual: Decimal? = Decimal.fromStringOrNull(text)
        val expected: Decimal = Decimal.of(0)
        assertEquals(expected, actual, message = "Input: $text")
    }

    @Test
    fun fromStringOrNullRemovesPlusSignFromPositiveDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomPositiveDecimalString()
            val decimal: Decimal? = Decimal.fromStringOrNull(text)
            val message = "Input: $text"
            assertNotNull(decimal, message)
            assertFalse(message) { "$decimal".startsWith('+') }
        }

    @Test
    fun fromStringOrNullKeepsMinusSignFromNegativeDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomNegativeDecimalString()
            val decimal: Decimal? = Decimal.fromStringOrNull(text)
            val message = "Input: $text"
            assertNotNull(decimal, message)
            assertTrue(message) { "$decimal".startsWith('-') }
        }

    @Test
    fun fromStringOrNullRemovesLeadingZerosFromNonZeroDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomNonZeroDecimalString()
            val decimal: Decimal? = Decimal.fromStringOrNull(text)
            val message = "Input: $text"
            assertNotNull(decimal, message)
            assertTrue(message) {
                "$decimal".let { it.startsWith("0.") || !it.startsWith('0') }
            }
        }

    @Test
    fun fromStringOrNullRemovesTrailingZerosFromDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomNonZeroDecimalString()
            val decimal: Decimal? = Decimal.fromStringOrNull(text)
            val message = "Input: $text"
            assertNotNull(decimal, message)
            assertTrue(message) {
                "$decimal".let { '.' !in it || !it.endsWith('0') }
            }
        }

    @Test
    fun fromStringOrNullIsStableWithStringRepresentation(): Unit = repeatTest {
        val text: String = randomNonZeroDecimalString()
        val x: Decimal? = Decimal.fromStringOrNull(text)
        val y: Decimal? = Decimal.fromStringOrNull("$x")
        assertEquals(x, y, message = "Input: $text")
    }

    @Test
    fun fromStringOrNullReturnsNullWithMalformedDecimalStrings(): Unit =
        repeatTest {
            val text: String = randomMalformedDecimalString()
            val actual: Decimal? = Decimal.fromStringOrNull(text)
            assertNull(actual, message = "Input: $text")
        }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun equalsAndHashCodeWithSameDecimalNumbers(): Unit = repeatTest {
        val x: Decimal = Decimal.random()
        val y: Decimal = Decimal.fromString("$x")
        val message = "Inputs: x = $x, y = $y"
        assertEquals(x, y, message)
        assertEquals(x.hashCode(), y.hashCode(), message)
    }

    @Test
    fun equalsAndHashCodeWithDifferentDecimalNumbers(): Unit = repeatTest {
        val x: Decimal = Decimal.random()
        val y: Decimal = Decimal.randomExcept(x)
        val message = "Inputs: x = $x, y = $y"
        assertNotEquals(x, y, message)
        assertNotEquals(x.hashCode(), y.hashCode(), message)
    }

    @Test
    fun equalsAndHashCodeWithAnotherTypeThanDecimal(): Unit = repeatTest {
        val x: Decimal = Decimal.random()
        val y = "$x"
        val message = "Inputs: x = $x, y = $y"
        assertNotEquals(x, y as Any, message)
        assertNotEquals(x.hashCode(), y.hashCode(), message)
    }

    @Test
    fun compareToReturnsZeroWithSameDecimal(): Unit = repeatTest {
        val x: Decimal = Decimal.random()
        val y: Decimal = Decimal.fromString("$x")
        assertTrue(message = "Inputs: x = $x, y = $y") { x.compareTo(y) == 0 }
    }

    @Test
    fun compareToReturnsNegativeNumberWithGreaterDecimal(): Unit = repeatTest {
        val x: Decimal = Decimal.fromString(randomNegativeDecimalString())
        val y: Decimal = Decimal.fromString(randomPositiveDecimalString())
        assertTrue(message = "Inputs: x = $x, y = $y") { x < y }
    }

    @Test
    fun compareToReturnsPositiveNumberWithLessDecimal(): Unit = repeatTest {
        val x: Decimal = Decimal.fromString(randomPositiveDecimalString())
        val y: Decimal = Decimal.fromString(randomNegativeDecimalString())
        assertTrue(message = "Inputs: x = $x, y = $y") { x > y }
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringReturnsParseableDecimalString(): Unit = repeatTest {
        val x: Decimal = Decimal.random()
        val y: Decimal = Decimal.fromString("$x")
        assertEquals(x, y, message = "Input: $x")
    }
}
