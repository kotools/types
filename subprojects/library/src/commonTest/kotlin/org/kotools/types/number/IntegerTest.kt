package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.random.Random
import kotlin.random.nextLong
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerTest {
    // ------------------------------- Creations -------------------------------

    @Test
    fun ofWithLongRepresentingZero() {
        // Given
        val value = 0L
        // When
        val actual: Integer = Integer.of(value)
        // Then
        assertEquals(expected = "$value", "$actual")
    }

    @Test
    fun ofWithLongMaxValue() {
        // Given
        val value: Long = Long.MAX_VALUE
        // When
        val actual: Integer = Integer.of(value)
        // Then
        assertEquals(expected = "$value", "$actual")
    }

    @Test
    fun ofWithLongMinValue() {
        // Given
        val value: Long = Long.MIN_VALUE
        // When
        val actual: Integer = Integer.of(value)
        // Then
        assertEquals(expected = "$value", "$actual")
    }

    @Test
    fun ofWithRandomLong(): Unit = repeatTest {
        // Given
        val value: Long = Random.nextLong()
        // When
        val actual: Integer = Integer.of(value)
        // Then
        assertEquals(expected = "$value", "$actual")
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEqualityReturnsTrueWithSameIntegerValue(): Unit = repeatTest {
        // Given
        val value: Long = Random.nextLong()
        val x: Integer = Integer.of(value)
        val y: Integer = Integer.of(value)
        // When
        val equality: Boolean = x == y
        val hashConformity: Boolean = x.hashCode() == y.hashCode()
        // Then
        assertTrue(equality, "$x and $y must be structurally equal.")
        assertTrue(hashConformity, "$x and $y must have the same hash code.")
    }

    @Test
    fun structuralEqualityReturnsFalseWithAnotherTypeThanInteger(): Unit =
        repeatTest {
            // Given
            val value: Long = Random.nextLong()
            val x: Integer = Integer.of(value)
            // When
            val equality: Boolean = x.equals(value)
            val hashConformity: Boolean = x.hashCode() == value.hashCode()
            // Then
            assertFalse(equality, "Integer can't be equal to another type.")
            assertFalse(
                hashConformity,
                "Integer must produce a unique hash code."
            )
        }

    @Test
    fun structuralEqualityReturnsFalseWithAnotherIntegerValue(): Unit =
        repeatTest {
            // Given
            val random = Random.Default
            val x: Integer = Integer.of(random.nextLong())
            val y: Integer = Integer.of(random.nextLong())
            // When
            val equality: Boolean = x == y
            val hashConformity: Boolean = x.hashCode() == y.hashCode()
            // Then
            assertFalse(equality, "$x and $y must be different.")
            assertFalse(
                hashConformity,
                "$x and $y must have different hash codes."
            )
        }

    @Test
    fun compareToReturnsZeroWithSameIntegerValue(): Unit = repeatTest {
        // Given
        val value: Long = Random.nextLong()
        val x: Integer = Integer.of(value)
        val y: Integer = Integer.of(value)
        // When
        val actual: Int = x.compareTo(y)
        // Then
        assertEquals(expected = 0, actual, "$x and $y must be equal.")
    }

    @Test
    fun compareToReturnsPositiveNumberWithLesserIntegerValue(): Unit =
        repeatTest {
            // Given
            val value: Long = Random.nextLong(
                (Long.MIN_VALUE + 1)..Long.MAX_VALUE
            )
            val x: Integer = Integer.of(value)
            val y: Integer = Integer.of(Random.nextLong(Long.MIN_VALUE..<value))
            // When
            val actual: Int = x.compareTo(y)
            // Then
            assertTrue("$x must be greater than ${y}.") { actual > 0 }
        }

    @Test
    fun compareToReturnsNegativeNumberWithGreaterIntegerValue(): Unit =
        repeatTest {
            // Given
            val value: Long = Random.nextLong(Long.MIN_VALUE..<Long.MAX_VALUE)
            val x: Integer = Integer.of(value)
            val y: Integer = Integer.of(
                Random.nextLong((value + 1)..Long.MAX_VALUE)
            )
            // When
            val actual: Int = x.compareTo(y)
            // Then
            assertTrue("$x must be less than ${y}.") { actual < 0 }
        }

    @Test
    fun compareToIsAlignedWithLongComparisons() {
        // Given
        val random = Random.Default
        val xValue: Long = random.nextLong()
        val x: Integer = Integer.of(xValue)
        val yValue: Long = random.nextLong()
        val y: Integer = Integer.of(yValue)
        // When
        val actual: Int = x.compareTo(y)
        // Then
        val expected: Int = xValue.compareTo(yValue)
        val message = "Integer must align with Long comparisons."
        assertEquals(expected, actual, message)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringHasUniqueRepresentationOnZero() {
        // Given
        val zero = Integer.of(0)
        // When
        val actual: String = zero.toString()
        // Then
        assertEquals(expected = "0", actual)
    }

    @Test
    fun toStringIgnoresPlusSignOnPositiveInteger(): Unit = repeatTest {
        // Given
        val value: Long = Random.nextLong(1..Long.MAX_VALUE)
        val integer: Integer = Integer.of(value)
        // When
        val actual: String = integer.toString()
        // Then
        assertFalse(message = "$actual must not start with '+'.") {
            actual.startsWith('+')
        }
        assertEquals(expected = "$value", actual)
    }

    @Test
    fun toStringKeepsMinusSignOnNegativeInteger(): Unit = repeatTest {
        // Given
        val value: Long = Random.nextLong(Long.MIN_VALUE..-1)
        val integer: Integer = Integer.of(value)
        // When
        val actual: String = integer.toString()
        // Then
        assertTrue(message = "$actual must start with '-'.") {
            actual.startsWith('-')
        }
        assertEquals(expected = "$value", actual)
    }
}

private inline fun repeatTest(block: () -> Unit): Unit = repeat(times = 1_000) {
    block()
}
