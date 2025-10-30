package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
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
    fun parseWithDecimalString() {
        val text = "1234"
        val integer: Integer = Integer.parse(text)
        assertEquals(expected = text, actual = "$integer")
    }

    @Test
    fun parseWithPlusSignedDecimalString() {
        val plusSign = "+"
        val text = "${plusSign}1234"
        val integer: Integer = Integer.parse(text)
        val expected: String = text.removePrefix(plusSign)
        assertEquals(expected, actual = "$integer")
    }

    @Test
    fun parseWithMinusSignedDecimalString() {
        val text = "-1234"
        val integer: Integer = Integer.parse(text)
        assertEquals(expected = text, actual = "$integer")
    }

    @Test
    fun parseWithBlankString() {
        val text = "   "
        val exception: IllegalArgumentException = assertFailsWith {
            Integer.parse(text)
        }
        val expected = "Integer should not be blank"
        assertEquals(expected, actual = exception.message)
    }

    @Test
    fun parseWithNonDecimalString() {
        val text = "oops"
        val exception: IllegalArgumentException = assertFailsWith {
            Integer.parse(text)
        }
        val expected = "Integer can only contain an optional + or - sign, " +
                "followed by a sequence of digits, was: $text"
        assertEquals(expected, actual = exception.message)
    }

    @Test
    fun parseOrNullWithDecimalString() {
        val text = "1234"
        val integer: Integer? = Integer.parseOrNull(text)
        assertNotNull(integer)
        assertEquals(expected = text, actual = "$integer")
    }

    @Test
    fun parseOrNullWithPlusSignedDecimalString() {
        val plusSign = "+"
        val text = "${plusSign}1234"
        val integer: Integer? = Integer.parseOrNull(text)
        assertNotNull(integer)
        val expected: String = text.removePrefix(plusSign)
        assertEquals(expected, actual = "$integer")
    }

    @Test
    fun parseOrNullWithMinusSignedDecimalString() {
        val text = "-1234"
        val integer: Integer? = Integer.parseOrNull(text)
        assertNotNull(integer)
        assertEquals(expected = text, actual = "$integer")
    }

    @Test
    fun parseOrNullWithBlankString() {
        val actual: Integer? = Integer.parseOrNull("  ")
        assertNull(actual)
    }

    @Test
    fun parseOrNullWithNonDecimalString() {
        val actual: Integer? = Integer.parseOrNull("oops")
        assertNull(actual)
    }
}
