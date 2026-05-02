package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.random
import org.kotools.types.randomExcept
import org.kotools.types.randomMalformedDecimalString
import org.kotools.types.randomNegativeDecimalString
import org.kotools.types.randomNonZeroDecimalString
import org.kotools.types.randomPositiveDecimalString
import org.kotools.types.randomZeroDecimalString
import org.kotools.types.repeatTest
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
    fun parseRepresentsZeroUniquely(): Unit = repeatTest {
        // Given
        val value: String = randomZeroDecimalString()
        // When
        val actual: Decimal = Decimal.parse(value)
        // Then
        val expected: Decimal = Decimal.of(0)
        assertEquals(expected, actual, message = "Input: $value")
    }

    @Test
    fun parseRemovesPlusSignFromPositiveDecimalNumbers(): Unit = repeatTest {
        // Given
        val value: String = randomPositiveDecimalString()
        // When
        val actual: Decimal = Decimal.parse(value)
        // Then
        assertFalse(message = "Input: $value") { "$actual".startsWith('+') }
    }

    @Test
    fun parseKeepsMinusSignFromNegativeDecimalNumbers(): Unit = repeatTest {
        // Given
        val value: String = randomNegativeDecimalString()
        // When
        val actual: Decimal = Decimal.parse(value)
        // Then
        assertTrue(message = "Input: $value") { "$actual".startsWith('-') }
    }

    @Test
    fun parseRemovesLeadingZerosFromNonZeroDecimalNumbers(): Unit = repeatTest {
        // Given
        val value: String = randomNonZeroDecimalString()
        // When
        val actual: Decimal = Decimal.parse(value)
        // Then
        assertTrue(message = "Input: $value") {
            val s: String = actual.toString()
            s.startsWith("0.") || !s.startsWith('0')
        }
    }

    @Test
    fun parseRemovesTrailingZerosFromDecimalNumbers(): Unit = repeatTest {
        // Given
        val value: String = randomNonZeroDecimalString()
        // When
        val actual: Decimal = Decimal.parse(value)
        // Then
        assertTrue(message = "Input: $value") {
            val s: String = actual.toString()
            '.' !in s || !s.endsWith('0')
        }
    }

    @Test
    fun parseIsStableWithStringRepresentation(): Unit = repeatTest {
        // Given
        val value: String = randomNonZeroDecimalString()
        // When
        val actual: Decimal = Decimal.parse(value)
        // Then
        val expected: Decimal = Decimal.parse("$actual")
        assertEquals(expected, actual, message = "Input: $value")
    }

    @Test
    fun parseThrowsExceptionWithMalformedDecimalStrings(): Unit = repeatTest {
        // Given
        val value: String = randomMalformedDecimalString()
        // When & Then
        val exception: IllegalArgumentException = assertFailsWith {
            Decimal.parse(value)
        }
        val actual: String? = exception.message
        val expected = "\"$value\" is not a valid decimal number."
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
        val y: Decimal = Decimal.parse("$x")
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
        val y: Decimal = Decimal.parse("$x")
        assertTrue(message = "Inputs: x = $x, y = $y") { x.compareTo(y) == 0 }
    }

    @Test
    fun compareToReturnsNegativeNumberWithGreaterDecimal(): Unit = repeatTest {
        val x: Decimal = Decimal.parse(randomNegativeDecimalString())
        val y: Decimal = Decimal.parse(randomPositiveDecimalString())
        assertTrue(message = "Inputs: x = $x, y = $y") { x < y }
    }

    @Test
    fun compareToReturnsPositiveNumberWithLessDecimal(): Unit = repeatTest {
        val x: Decimal = Decimal.parse(randomPositiveDecimalString())
        val y: Decimal = Decimal.parse(randomNegativeDecimalString())
        assertTrue(message = "Inputs: x = $x, y = $y") { x > y }
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinusReturnsSameInstanceOnZero(): Unit = repeatTest {
        val decimalString: String = randomZeroDecimalString()
        val zero: Decimal = Decimal.parse(decimalString)

        val actual: Decimal = -zero

        assertSame(expected = zero, actual)
    }

    @Test
    fun unaryMinusReturnsNegativeNumberOnPositiveDecimalNumber(): Unit =
        repeatTest {
            val decimalString: String = randomPositiveDecimalString()
            val number: Decimal = Decimal.parse(decimalString)

            val actual: Decimal = -number

            val expected: Decimal = Decimal.parse("-$number")
            assertEquals(expected, actual, message = "Input: $number")
            assertTrue(message = "$actual must be negative (< 0).") {
                actual < Decimal.of(0)
            }
        }

    @Test
    fun unaryMinusReturnsPositiveNumberOnNegativeDecimalNumber(): Unit =
        repeatTest {
            val decimalString: String = randomNegativeDecimalString()
            val number: Decimal = Decimal.parse(decimalString)

            val actual: Decimal = -number

            val magnitude: String = number.toString()
                .removePrefix("-")
            val expected: Decimal = Decimal.parse(magnitude)
            assertEquals(expected, actual, message = "Input: $number")
            assertTrue(message = "$actual must be positive (> 0).") {
                actual > Decimal.of(0)
            }
        }

    @Test
    fun plusIsNeutralWithZero(): Unit = repeatTest {
        val x: Decimal = Decimal.random()
        val zero: Decimal = Decimal.parse(randomZeroDecimalString())

        // Neutrality: x + 0 = 0 + x = x
        assertSame(expected = x, actual = x + zero)
        assertSame(expected = x, actual = zero + x)
    }

    @Test
    fun plusDoesNotOverflowWithPositiveDecimalNumbers(): Unit = repeatTest {
        val x: Decimal = Decimal.parse(randomPositiveDecimalString())
        val y: Decimal = Decimal.parse(randomPositiveDecimalString())

        val actual: Decimal = x + y

        assertTrue("$actual must be greater than ${x}.") { actual > x }
        assertTrue("$actual must be greater than ${y}.") { actual > y }
    }

    @Test
    fun plusDoesNotOverflowWithNegativeDecimalNumbers(): Unit = repeatTest {
        val x: Decimal = Decimal.parse(randomNegativeDecimalString())
        val y: Decimal = Decimal.parse(randomNegativeDecimalString())

        val actual: Decimal = x + y

        assertTrue("$actual must be less than ${x}.") { actual < x }
        assertTrue("$actual must be less than ${y}.") { actual < y }
    }

    @Test
    fun plusIsCommutative(): Unit = repeatTest {
        val x: Decimal = Decimal.random()
        val y: Decimal = Decimal.random()

        // Commutativity: x + y = y + x
        assertEquals(x + y, y + x, "Inputs: x = $x, y = $y")
    }

    @Test
    fun plusIsAssociative(): Unit = repeatTest {
        val x: Decimal = Decimal.random()
        val y: Decimal = Decimal.random()
        val z: Decimal = Decimal.random()

        // Associativity: (x + y) + z = x + (y + z)
        assertEquals((x + y) + z, x + (y + z), "Inputs: x = $x, y = $y, z = $z")
    }

    @Test
    fun plusHasAdditiveInverse(): Unit = repeatTest {
        val x: Decimal = Decimal.random()

        // Additive inverse: x + (-x) = 0
        assertEquals(Decimal.of(0), x + (-x), "Input: $x")
    }

    @Test
    fun plusPreservesOrdering(): Unit = repeatTest {
        val x: Decimal = Decimal.parse(randomPositiveDecimalString())
        val y: Decimal = Decimal.parse(randomNegativeDecimalString())
        val z: Decimal = Decimal.random()

        // Ordering preservation: if x > y, then (x + z) > (y + z)
        assertTrue("$x must be greater than ${y}.") { x > y }
        assertTrue("($x + $z) must be greater than ($y + $z).") {
            x + z > y + z
        }
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringReturnsParseableDecimalString(): Unit = repeatTest {
        val x: Decimal = Decimal.random()
        val y: Decimal = Decimal.parse("$x")
        assertEquals(x, y, message = "Input: $x")
    }
}
