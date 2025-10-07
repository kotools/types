package org.kotools.types

import kotools.types.internal.hashCodeOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerTest {
    // ----------------------------- equals(Any?) ------------------------------

    @Test
    fun equalsPassesWithPositiveIntegerHavingSameStringRepresentation() {
        val integer: PositiveInteger = PositiveInteger.of("123456789") ?: fail()
        val other: PositiveInteger = integer.toString()
            .let(PositiveInteger.Companion::of)
            ?: fail()
        val actual: Boolean = integer.equals(other)
        assertTrue(actual)
    }

    @Test
    fun equalsFailsWithNull() {
        val integer: PositiveInteger = PositiveInteger.of("123456789") ?: fail()
        val other: Any? = null
        val actual: Boolean = integer.equals(other)
        assertFalse(actual)
    }

    @Test
    fun equalsFailsWithAnotherTypeThanPositiveInteger() {
        val integer: PositiveInteger = PositiveInteger.of("123456789") ?: fail()
        val other: String = integer.toString()
        val actual: Boolean = integer.equals(other)
        assertFalse(actual)
    }

    @Test
    fun equalsFailsWithPositiveIntegerHavingAnotherStringRepresentation() {
        val integer: PositiveInteger = PositiveInteger.of("123456789") ?: fail()
        val other: PositiveInteger = integer.toString()
            .reversed()
            .let(PositiveInteger.Companion::of)
            ?: fail()
        val actual: Boolean = integer.equals(other)
        assertFalse(actual)
    }

    // ------------------------------ hashCode() -------------------------------

    @Test
    fun hashCodePasses() {
        val text = "123456789"
        val actual: Int? = PositiveInteger.of(text)
            ?.hashCode()
        val expected: Int = hashCodeOf(text)
        assertEquals(expected, actual)
    }

    // ------------------------- plus(PositiveInteger) -------------------------

    @Test
    fun plusPassesWithPositiveIntegerHavingSameNumberOfDigits() {
        val integer: PositiveInteger = PositiveInteger.of("1234") ?: fail()
        val other: PositiveInteger = PositiveInteger.of("1234") ?: fail()
        val actual: PositiveInteger = integer + other
        val expected: PositiveInteger = PositiveInteger.of("2468") ?: fail()
        assertEquals(expected, actual)
    }

    @Test
    fun plusPassesWithPositiveIntegerHavingMoreDigits() {
        val integer: PositiveInteger = PositiveInteger.of("1234") ?: fail()
        val other: PositiveInteger = PositiveInteger.of("12345") ?: fail()
        val actual: PositiveInteger = integer + other
        val expected: PositiveInteger = PositiveInteger.of("13579") ?: fail()
        assertEquals(expected, actual)
    }

    @Test
    fun plusPassesWithPositiveIntegerHavingLessDigits() {
        val integer: PositiveInteger = PositiveInteger.of("1234") ?: fail()
        val other: PositiveInteger = PositiveInteger.of("123") ?: fail()
        val actual: PositiveInteger = integer + other
        val expected: PositiveInteger = PositiveInteger.of("1357") ?: fail()
        assertEquals(expected, actual)
    }

    @Test
    fun plusPassesWithPositiveIntegerWithCarry() {
        val integer: PositiveInteger = PositiveInteger.of("999") ?: fail()
        val other: PositiveInteger = PositiveInteger.minimum()
        val actual: PositiveInteger = integer + other
        val expected: PositiveInteger = PositiveInteger.of("1000") ?: fail()
        assertEquals(expected, actual)
    }

    @Test
    fun plusPassesWithPositiveIntegerBeingMaximumValueOfLong() {
        val integer: PositiveInteger = PositiveInteger.of("123456789") ?: fail()
        val other: PositiveInteger = Long.MAX_VALUE.toString()
            .let(PositiveInteger.Companion::of)
            ?: fail()
        val actual: PositiveInteger = integer + other
        val expected: PositiveInteger =
            PositiveInteger.of("9223372036978232596") ?: fail()
        assertEquals(expected, actual)
    }

    // ------------------------ times(PositiveInteger) -------------------------

    @Test
    fun timesPassesWithMinimumValueOfPositiveInteger() {
        val integer: PositiveInteger = PositiveInteger.of("1234") ?: fail()
        val other: PositiveInteger = PositiveInteger.minimum()
        val actual: PositiveInteger = integer * other
        assertEquals(expected = integer, actual)
    }

    @Test
    fun timesPassesOnMinimumValueOfPositiveInteger() {
        val integer: PositiveInteger = PositiveInteger.minimum()
        val other: PositiveInteger = PositiveInteger.of("5") ?: fail()
        val actual: PositiveInteger = integer * other
        assertEquals(expected = other, actual)
    }

    @Test
    fun timesPassesWithSingleDigitPositiveInteger() {
        val integer: PositiveInteger = PositiveInteger.of("809") ?: fail()
        val other: PositiveInteger = PositiveInteger.of("3") ?: fail()
        val actual: PositiveInteger = integer * other
        val expected: PositiveInteger = PositiveInteger.of("2427") ?: fail()
        assertEquals(expected, actual)
    }

    @Test
    fun timesPassesWithMultipleDigitsPositiveInteger() {
        val integer: PositiveInteger = PositiveInteger.of("23958233") ?: fail()
        val other: PositiveInteger = PositiveInteger.of("5830") ?: fail()
        val actual: PositiveInteger = integer * other
        val expected: PositiveInteger = PositiveInteger.of("139676498390")
            ?: fail()
        assertEquals(expected, actual)
    }

    @Test
    fun timesPassesWithPositiveIntegerBeingMaximumValueOfLong() {
        val integer: PositiveInteger = PositiveInteger.of("2") ?: fail()
        val other: PositiveInteger = Long.MAX_VALUE.toString()
            .let(PositiveInteger.Companion::of)
            ?: fail()
        val actual: PositiveInteger = integer * other
        val expected: PositiveInteger =
            PositiveInteger.of("18446744073709551614") ?: fail()
        assertEquals(expected, actual)
    }

    // ------------------------------ toString() -------------------------------

    @Test
    fun toStringPassesFromInteger() {
        val text = "123456789"
        val actual: String = PositiveInteger.of(text)
            ?.toString()
            ?: fail()
        assertEquals(expected = text, actual)
    }

    @Test
    fun toStringPassesFromSignedInteger() {
        val expected = "123456789"
        val actual: String = PositiveInteger.of("+$expected")
            ?.toString()
            ?: fail()
        assertEquals(expected, actual)
    }

    // ------------------------- Companion.of(String) --------------------------

    @Test
    fun ofPassesWithStringIntegerGreaterThanZero() {
        assertNotNull(PositiveInteger of "123456789")
    }

    @Test
    fun ofPassesWithStringSignedIntegerGreaterThanZero() {
        assertNotNull(PositiveInteger of "+123456789")
    }

    @Test
    fun ofFailsWithStringOtherThanInteger(): Unit =
        assertNull(PositiveInteger of "oops")

    @Test
    fun ofFailsWithStringIntegerRepresentingZero(): Unit =
        assertNull(PositiveInteger of "0")

    @Test
    fun ofFailsWithStringIntegerLessThanZero(): Unit =
        assertNull(PositiveInteger of "-123456789")

    // -------------------------- Companion.minimum() --------------------------

    @Test
    fun minimumReturns1() {
        val actual: PositiveInteger = PositiveInteger.minimum()
        val expected: PositiveInteger = PositiveInteger.of("1") ?: fail()
        assertEquals(expected, actual)
    }
}
