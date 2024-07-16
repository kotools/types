package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroCommonSample {
    @Test
    fun primaryConstructor() {
        Zero()
    }

    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsOverride() {
        val first = Zero()
        val second = Zero()
        val actual: Boolean = first == second // or first.equals(second)
        assertTrue(actual)
    }

    @Test
    fun hashCodeOverride() {
        val first = Zero()
        val second = Zero()
        val actual: Boolean = first.hashCode() == second.hashCode()
        assertTrue(actual)
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun compareToByte() {
        val zero = Zero()
        val other: Byte = 1
        val actual: Boolean = zero < other // or zero.compareTo(other) < 0
        assertTrue(actual)
    }

    @Test
    fun compareToShort() {
        val zero = Zero()
        val other: Short = 1
        val actual: Boolean = zero < other // or zero.compareTo(other) < 0
        assertTrue(actual)
    }

    @Test
    fun compareToInt() {
        val zero = Zero()
        val other = 1
        val actual: Boolean = zero < other // or zero.compareTo(other) < 0
        assertTrue(actual)
    }

    @Test
    fun compareToLong() {
        val zero = Zero()
        val other: Long = 1
        val actual: Boolean = zero < other // or zero.compareTo(other) < 0
        assertTrue(actual)
    }

    @Test
    fun compareToFloat() {
        val zero = Zero()
        val other = 0.01f
        val actual: Boolean = zero < other // or zero.compareTo(other) < 0
        assertTrue(actual)
    } // END

    @Test
    fun compareToDouble() {
        val zero = Zero()
        val other = 0.01
        val actual: Boolean = zero < other // or zero.compareTo(other) < 0
        assertTrue(actual)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toByte() {
        val actual: Byte = Zero()
            .toByte()
        val expected: Byte = 0
        assertEquals(expected, actual)
    }

    @Test
    fun toShort() {
        val actual: Short = Zero()
            .toShort()
        val expected: Short = 0
        assertEquals(expected, actual)
    }

    @Test
    fun toInt() {
        val actual: Int = Zero()
            .toInt()
        val expected = 0
        assertEquals(expected, actual)
    }

    @Test
    fun toLong() {
        val actual: Long = Zero()
            .toLong()
        val expected = 0L
        assertEquals(expected, actual)
    }

    @Test
    fun toFloat() {
        val actual: Float = Zero()
            .toFloat()
        val expected = 0f
        assertEquals(expected, actual)
    }

    @Test
    fun toDouble() {
        val actual: Double = Zero()
            .toDouble()
        val expected = 0.0
        assertEquals(expected, actual)
    }

    @Test
    fun toChar() {
        val actual: Char = Zero()
            .toChar()
        val expected = '0'
        assertEquals(expected, actual)
    }

    @Test
    fun toStringOverride() {
        val actual: String = Zero()
            .toString()
        val expected = "0"
        assertEquals(expected, actual)
    }
}
