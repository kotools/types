package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.fail

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerTest {
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
