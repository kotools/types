package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroCommonSample {
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

    // ------------------------------- Companion -------------------------------

    @Test
    fun orNull() {
        val number: Byte = 0
        val zero: Zero? = Zero.orNull(number)
        assertNotNull(zero)
    }

    @Test
    fun orNullWithShort() {
        val number: Short = 0
        val zero: Zero? = Zero.orNull(number)
        assertNotNull(zero)
    }

    @Test
    fun orNullWithInt() {
        val zero: Zero? = Zero.orNull(0)
        assertNotNull(zero)
    }

    @Test
    fun orNullWithLong() {
        val zero: Zero? = Zero.orNull(0L)
        assertNotNull(zero)
    }

    @Test
    fun orNullWithFloat() {
        val zero: Zero? = Zero.orNull(0f)
        assertNotNull(zero)
    }

    @Test
    fun orNullWithDouble() {
        val zero: Zero? = Zero.orNull(0.0)
        assertNotNull(zero)
    }

    @Test
    fun orNullWithString() {
        listOf("0", "000", "0.0", "0.000", "000.0", "000.000")
            .map(Zero.Companion::orNull)
            .forEach(::assertNotNull)
    }

    @Test
    fun orThrowWithByte() {
        val number: Byte = 0
        val isSuccess: Boolean = try {
            Zero.orThrow(number)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @Test
    fun orThrowWithShort() {
        val number: Short = 0
        val isSuccess: Boolean = try {
            Zero.orThrow(number)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @Test
    fun orThrowWithInt() {
        val isSuccess: Boolean = try {
            Zero.orThrow(0)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @Test
    fun orThrowWithLong() {
        val isSuccess: Boolean = try {
            Zero.orThrow(0L)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @Test
    fun orThrowWithFloat() {
        val isSuccess: Boolean = try {
            Zero.orThrow(0f)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @Test
    fun orThrowWithDouble() {
        val isSuccess: Boolean = try {
            Zero.orThrow(0.0)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }

    @Test
    fun orThrowWithString() {
        val isSuccess: Boolean = try {
            listOf("0", "000", "0.0", "0.000", "000.0", "000.000")
                .forEach(Zero.Companion::orThrow)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
        assertTrue(isSuccess)
    }
}
