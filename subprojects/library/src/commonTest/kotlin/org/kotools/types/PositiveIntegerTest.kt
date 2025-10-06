package org.kotools.types

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
}
