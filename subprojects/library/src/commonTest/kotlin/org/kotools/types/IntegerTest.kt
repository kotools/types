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
        val number: Long = Long.MAX_VALUE
        val integer: Integer = Integer.from(number)
        val other: Integer = Integer.from(number)
        val result: Boolean = integer == other
        assertTrue(result)
    }

    @Test
    fun equalsFailsWithAnotherTypeThanInteger() {
        val number: Long = Long.MAX_VALUE
        val integer: Integer = Integer.from(number)
        val result: Boolean = integer.equals(other = number)
        assertFalse(result)
    }

    @Test
    fun equalsFailsWithIntegerHavingAnotherValue() {
        val integer: Integer = Integer.from(Long.MAX_VALUE)
        val other: Integer = Integer.from(Long.MIN_VALUE)
        val result: Boolean = integer == other
        assertFalse(result)
    }

    @Test
    fun hashCodeReturnsSameValueForIntegersThatAreEqual() {
        val number: Long = Long.MAX_VALUE
        val x: Integer = Integer.from(number)
        val y: Integer = Integer.from(number)
        assertEquals(x.hashCode(), y.hashCode())
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun plusPasses() {
        val number = 9223372036854775807
        val x: Integer = Integer.from(number)
        val y: Integer = Integer.from(number)
        val actual: Integer = x + y
        val expected: Integer = Integer.parse("18446744073709551614")
        assertEquals(expected, actual)
    }

    @Test
    fun minusPasses() {
        val number = 9223372036854775807
        val x: Integer = Integer.from(-number)
        val y: Integer = Integer.from(number)
        val actual: Integer = x - y
        val expected: Integer = Integer.parse("-18446744073709551614")
        assertEquals(expected, actual)
    }

    @Test
    fun timesPasses() {
        val x: Integer = Integer.from(9223372036854775807)
        val y: Integer = Integer.from(1000)
        val actual: Integer = x * y
        val expected: Integer = Integer.parse("9223372036854775807000")
        assertEquals(expected, actual)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringPasses() {
        val number: Long = Long.MAX_VALUE
        val actual: String = Integer.from(number)
            .toString()
        assertEquals(expected = "$number", actual)
    }

    // ----------------------- Class-level declarations ------------------------

    @Test
    fun fromPassesWithLong() {
        val number: Long = Long.MAX_VALUE
        val integer: Integer = Integer.from(number)
        assertEquals(expected = "$number", actual = "$integer")
    }

    @Test
    fun parsePassesWithNonZeroDecimalString() {
        val number = 123456L
        val actual: Integer = Integer.parse("$number")
        val expected: Integer = Integer.from(number)
        assertEquals(expected, actual)
    }

    @Test
    fun parsePassesWithPlusSignedNonZeroDecimalString() {
        val number = 123456L
        val actual: Integer = Integer.parse("+$number")
        val expected: Integer = Integer.from(number)
        assertEquals(expected, actual)
    }

    @Test
    fun parsePassesWithMinusSignedNonZeroDecimalString() {
        val number: Long = -123456L
        val actual: Integer = Integer.parse("$number")
        val expected: Integer = Integer.from(number)
        assertEquals(expected, actual)
    }

    @Test
    fun parsePassesWithSingleZeroDecimalString() {
        val actual: Integer = Integer.parse("0")
        assertEquals(expected = Integer.Zero, actual)
    }

    @Test
    fun parsePassesWithMultipleZerosDecimalString() {
        val actual: Integer = Integer.parse("000")
        assertEquals(expected = Integer.Zero, actual)
    }

    @Test
    fun parsePassesWithPlusSignedZeroDecimalString() {
        val actual: Integer = Integer.parse("+0")
        assertEquals(expected = Integer.Zero, actual)
    }

    @Test
    fun parsePassesWithMinusSignedZeroDecimalString() {
        val actual: Integer = Integer.parse("-0")
        assertEquals(expected = Integer.Zero, actual)
    }

    @Test
    fun parsePassesWithLeadingZerosInPositiveDecimalString() {
        val number = 123L
        val actual: Integer = Integer.parse("000$number")
        val expected: Integer = Integer.from(number)
        assertEquals(expected, actual)
    }

    @Test
    fun parsePassesWithLeadingZerosInNegativeDecimalString() {
        val number = 123L
        val actual: Integer = Integer.parse("-000$number")
        val expected: Integer = Integer.from(-number)
        assertEquals(expected, actual)
    }

    @Test
    fun parseFailsWithBlankString() {
        val exception: IllegalArgumentException = assertFailsWith {
            Integer.parse(" ")
        }
        val expected = "Integer should not be blank"
        assertEquals(expected, actual = exception.message)
    }

    @Test
    fun parseFailsWithPlusSignString() {
        val text = "+"
        val exception: IllegalArgumentException = assertFailsWith {
            Integer.parse(text)
        }
        val expected: String = Integer.syntaxErrorIn(text)
        assertEquals(expected, actual = exception.message)
    }

    @Test
    fun parseFailsWithMinusSignString() {
        val text = "-"
        val exception: IllegalArgumentException = assertFailsWith {
            Integer.parse(text)
        }
        val expected: String = Integer.syntaxErrorIn(text)
        assertEquals(expected, actual = exception.message)
    }

    @Test
    fun parseFailsWithNonDecimalString() {
        val text = "oops"
        val exception: IllegalArgumentException = assertFailsWith {
            Integer.parse(text)
        }
        val expected: String = Integer.syntaxErrorIn(text)
        assertEquals(expected, actual = exception.message)
    }

    @Test
    fun parseOrNullPassesWithNonZeroDecimalString() {
        val number = 123456L
        val actual: Integer? = Integer.parseOrNull("$number")
        val expected: Integer = Integer.from(number)
        assertEquals(expected, actual)
    }

    @Test
    fun parseOrNullPassesWithPlusSignedNonZeroDecimalString() {
        val number = 123456L
        val actual: Integer? = Integer.parseOrNull("+$number")
        val expected: Integer = Integer.from(number)
        assertEquals(expected, actual)
    }

    @Test
    fun parseOrNullPassesWithMinusSignedNonZeroDecimalString() {
        val number: Long = -123456L
        val actual: Integer? = Integer.parseOrNull("$number")
        val expected: Integer = Integer.from(number)
        assertEquals(expected, actual)
    }

    @Test
    fun parseOrNullPassesWithSingleZeroDecimalString() {
        val actual: Integer? = Integer.parseOrNull("0")
        assertEquals(expected = Integer.Zero, actual)
    }

    @Test
    fun parseOrNullPassesWithMultipleZerosDecimalString() {
        val actual: Integer? = Integer.parseOrNull("000")
        assertEquals(expected = Integer.Zero, actual)
    }

    @Test
    fun parseOrNullPassesWithPlusSignedZeroDecimalString() {
        val actual: Integer? = Integer.parseOrNull("+0")
        assertEquals(expected = Integer.Zero, actual)
    }

    @Test
    fun parseOrNullPassesWithMinusSignedZeroDecimalString() {
        val actual: Integer? = Integer.parseOrNull("-0")
        assertEquals(expected = Integer.Zero, actual)
    }

    @Test
    fun parseOrNullPassesWithLeadingZerosInPositiveDecimalString() {
        val number = 123L
        val actual: Integer? = Integer.parseOrNull("000$number")
        val expected: Integer = Integer.from(number)
        assertEquals(expected, actual)
    }

    @Test
    fun parseOrNullPassesWithLeadingZerosInNegativeDecimalString() {
        val number = 123L
        val actual: Integer = Integer.parse("-000$number")
        val expected: Integer = Integer.from(-number)
        assertEquals(expected, actual)
    }

    @Test
    fun parseOrNullFailsWithBlankString() {
        val actual: Integer? = Integer.parseOrNull("  ")
        assertNull(actual)
    }

    @Test
    fun parseOrNullFailsWithPlusSignString() {
        val actual: Integer? = Integer.parseOrNull("+")
        assertNull(actual)
    }

    @Test
    fun parseOrNullFailsWithMinusSignString() {
        val actual: Integer? = Integer.parseOrNull("-")
        assertNull(actual)
    }

    @Test
    fun parseOrNullFailsWithNonDecimalString() {
        val actual: Integer? = Integer.parseOrNull("oops")
        assertNull(actual)
    }
}
