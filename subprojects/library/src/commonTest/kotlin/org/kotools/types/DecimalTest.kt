package org.kotools.types

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
    fun fromIntegerUsesStringRepresentationOfSpecifiedNumber(): Unit =
        repeatTest {
            val number: Long = Random.nextLong()
            val actual: String = Decimal.fromInteger(number)
                .toString()
            val expected: String = number.toString()
            assertEquals(expected, actual, message = "Input: $number")
        }

    @Test
    fun fromDecimalRepresentsZeroUniquely(): Unit = repeatTest {
        val text: String = randomZeroString()
        val actual: Decimal = Decimal.fromDecimal(text)
        val expected: Decimal = Decimal.fromInteger(0)
        assertEquals(expected, actual, message = "Input: $text")
    }

    @Test
    fun fromDecimalRemovesPlusSignFromPositiveDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomPositiveDecimalString()
            val actual: Boolean = Decimal.fromDecimal(text)
                .toString()
                .startsWith('+')
            assertFalse(actual, message = "Input: $text")
        }

    @Test
    fun fromDecimalKeepsMinusSignFromNegativeDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomNegativeDecimalString()
            val actual: Boolean = Decimal.fromDecimal(text)
                .toString()
                .startsWith('-')
            assertTrue(actual, message = "Input: $text")
        }

    @Test
    fun fromDecimalRemovesLeadingZerosFromNonZeroDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomNonZeroDecimalString()
            val actual: Boolean = Decimal.fromDecimal(text)
                .toString()
                .let { it.startsWith("0.") || !it.startsWith('0') }
            assertTrue(actual, message = "Input: $text")
        }

    @Test
    fun fromDecimalRemovesTrailingZerosFromDecimalNumbers(): Unit = repeatTest {
        val text: String = randomNonZeroDecimalString()
        val actual: Boolean = Decimal.fromDecimal(text)
            .toString()
            .let { '.' !in it || !it.endsWith('0') }
        assertTrue(actual, message = "Input: $text")
    }

    @Test
    fun fromDecimalIsStableWithStringRepresentation(): Unit = repeatTest {
        val text: String = randomNonZeroDecimalString()
        val x: Decimal = Decimal.fromDecimal(text)
        val y: Decimal = Decimal.fromDecimal("$x")
        assertEquals(x, y, message = "Input: $text")
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

    @Test
    fun fromDecimalOrNullRepresentsZeroUniquely(): Unit = repeatTest {
        val text: String = randomZeroString()
        val actual: Decimal? = Decimal.fromDecimalOrNull(text)
        val expected: Decimal = Decimal.fromInteger(0)
        assertEquals(expected, actual, message = "Input: $text")
    }

    @Test
    fun fromDecimalOrNullRemovesPlusSignFromPositiveDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomPositiveDecimalString()
            val decimal: Decimal? = Decimal.fromDecimalOrNull(text)
            val message = "Input: $text"
            assertNotNull(decimal, message)
            assertFalse(message) { "$decimal".startsWith('+') }
        }

    @Test
    fun fromDecimalOrNullKeepsMinusSignFromNegativeDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomNegativeDecimalString()
            val decimal: Decimal? = Decimal.fromDecimalOrNull(text)
            val message = "Input: $text"
            assertNotNull(decimal, message)
            assertTrue(message) { "$decimal".startsWith('-') }
        }

    @Test
    fun fromDecimalOrNullRemovesLeadingZerosFromNonZeroDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomNonZeroDecimalString()
            val decimal: Decimal? = Decimal.fromDecimalOrNull(text)
            val message = "Input: $text"
            assertNotNull(decimal, message)
            assertTrue(message) {
                "$decimal".let { it.startsWith("0.") || !it.startsWith('0') }
            }
        }

    @Test
    fun fromDecimalOrNullRemovesTrailingZerosFromDecimalNumbers(): Unit =
        repeatTest {
            val text: String = randomNonZeroDecimalString()
            val decimal: Decimal? = Decimal.fromDecimalOrNull(text)
            val message = "Input: $text"
            assertNotNull(decimal, message)
            assertTrue(message) {
                "$decimal".let { '.' !in it || !it.endsWith('0') }
            }
        }

    @Test
    fun fromDecimalOrNullIsStableWithStringRepresentation(): Unit = repeatTest {
        val text: String = randomNonZeroDecimalString()
        val x: Decimal? = Decimal.fromDecimalOrNull(text)
        val y: Decimal? = Decimal.fromDecimalOrNull("$x")
        assertEquals(x, y, message = "Input: $text")
    }

    @Test
    fun fromDecimalOrNullReturnsNullWithMalformedDecimalStrings(): Unit =
        repeatTest {
            val text: String = randomMalformedDecimalString()
            val actual: Decimal? = Decimal.fromDecimalOrNull(text)
            assertNull(actual, message = "Input: $text")
        }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun equalsAndHashCodeWithSameDecimalNumbers(): Unit = repeatTest {
        val x: Decimal = Decimal.random()
        val y: Decimal = Decimal.fromDecimal("$x")
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

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringReturnsParseableDecimalString(): Unit = repeatTest {
        val x: Decimal = Decimal.random()
        val y: Decimal = Decimal.fromDecimal("$x")
        assertEquals(x, y, message = "Input: $x")
    }
}
