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
    fun ofWithRandomLong() {
        // Given
        val value: Long = Random.nextLong()
        // When
        val actual: Integer = Integer.of(value)
        // Then
        assertEquals(expected = "$value", "$actual")
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
    fun toStringIgnoresPlusSignOnPositiveInteger() {
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
    fun toStringKeepsMinusSignOnNegativeInteger() {
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
