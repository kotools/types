package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.hashCodeOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroTest {
    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsShouldPassWithZero() {
        val first = Zero()
        val second = Zero()
        val actual: Boolean = first.equals(second)
        assertTrue(actual)
    }

    @Test
    fun equalsShouldFailWithAnotherTypeThanZero() {
        val first = Zero()
        val second: Any = "oops"
        val actual: Boolean = first.equals(second)
        assertFalse(actual)
    }

    @Test
    fun hashCodeShouldPass() {
        val actual: Int = Zero()
            .hashCode()
        val expected: Int = 0.toByte()
            .let(::hashCodeOf)
        assertEquals(expected, actual)
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun compareToShouldReturnZeroWithSameByte() {
        val zero = Zero()
        val other: Byte = 0
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareToShouldReturnNegativeNumberWithGreaterByte() {
        val zero = Zero()
        val other: Byte = (1..Byte.MAX_VALUE)
            .random()
            .toByte()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareToShouldReturnPositiveNumberWithLessByte() {
        val zero = Zero()
        val other: Byte = (Byte.MIN_VALUE..-1)
            .random()
            .toByte()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    @Test
    fun compareToShouldReturnZeroWithSameShort() {
        val zero = Zero()
        val other: Short = 0
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareToShouldReturnNegativeNumberWithGreaterShort() {
        val zero = Zero()
        val other: Short = (1..Short.MAX_VALUE)
            .random()
            .toShort()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareToShouldReturnPositiveNumberWithLessShort() {
        val zero = Zero()
        val other: Short = (Short.MIN_VALUE..-1)
            .random()
            .toShort()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    @Test
    fun compareToShouldReturnZeroWithSameInt() {
        val zero = Zero()
        val other = 0
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareToShouldReturnNegativeNumberWithGreaterInt() {
        val zero = Zero()
        val other: Int = (1..Int.MAX_VALUE)
            .random()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareToShouldReturnPositiveNumberWithLessInt() {
        val zero = Zero()
        val other: Int = (Int.MIN_VALUE..-1)
            .random()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    @Test
    fun compareToShouldReturnZeroWithSameLong() {
        val zero = Zero()
        val other: Long = 0
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareToShouldReturnNegativeNumberWithGreaterLong() {
        val zero = Zero()
        val other: Long = (1..Long.MAX_VALUE)
            .random()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareToShouldReturnPositiveNumberWithLessLong() {
        val zero = Zero()
        val other: Long = (Long.MIN_VALUE..-1)
            .random()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    @Test
    fun compareToShouldReturnZeroWithSameFloat() {
        val zero = Zero()
        val other = 0f
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareToShouldReturnNegativeNumberWithGreaterFloat() {
        val zero = Zero()
        val other: Float = (1..Long.MAX_VALUE)
            .random()
            .toFloat()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareToShouldReturnPositiveNumberWithLessFloat() {
        val zero = Zero()
        val other: Float = (Long.MIN_VALUE..-1)
            .random()
            .toFloat()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    @Test
    fun compareToShouldReturnZeroWithSameDouble() {
        val zero = Zero()
        val other = 0.0
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareToShouldReturnNegativeNumberWithGreaterDouble() {
        val zero = Zero()
        val other: Double = (1..Long.MAX_VALUE)
            .random()
            .toDouble()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareToShouldReturnPositiveNumberWithLessDouble() {
        val zero = Zero()
        val other: Double = (Long.MIN_VALUE..-1)
            .random()
            .toDouble()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toByteShouldPass() {
        val actual: Byte = Zero()
            .toByte()
        val expected: Byte = 0
        assertEquals(expected, actual)
    }

    @Test
    fun toShortShouldPass() {
        val actual: Short = Zero()
            .toShort()
        val expected: Short = 0
        assertEquals(expected, actual)
    }

    @Test
    fun toIntShouldPass() {
        val actual: Int = Zero()
            .toInt()
        val expected = 0
        assertEquals(expected, actual)
    }

    @Test
    fun toLongShouldPass() {
        val actual: Long = Zero()
            .toLong()
        val expected = 0L
        assertEquals(expected, actual)
    }

    @Test
    fun toFloatShouldPass() {
        val actual: Float = Zero()
            .toFloat()
        val expected = 0f
        assertEquals(expected, actual)
    }

    @Test
    fun toDoubleShouldPass() {
        val actual: Double = Zero()
            .toDouble()
        val expected = 0.0
        assertEquals(expected, actual)
    }

    @Test
    fun toCharShouldPass() {
        val actual: Char = Zero()
            .toChar()
        val expected = '0'
        assertEquals(expected, actual)
    }

    @Test
    fun toStringShouldPass() {
        val actual: String = Zero()
            .toString()
        val expected = "0"
        assertEquals(expected, actual)
    }
}
