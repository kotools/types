package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerSample {
    // -------------------------- Type documentation ---------------------------

    @Suppress("INTEGER_OVERFLOW")
    @Test
    fun overflowProblem() {
        val x = 9223372036854775807
        val y = 10
        check(x + y == -9223372036854775799) // instead of 9223372036854775817
        check(-x - y == 9223372036854775799) // instead of -9223372036854775817
        check(x * y == -10L) // instead of 92233720368547758070
    }

    // ------------------------------- Creations -------------------------------

    @Test
    fun ofLong() {
        // Given
        val value: Long = Long.MAX_VALUE
        // When
        val result: Integer = Integer.of(value)
        // Then
        check("$result" == "$value")
    }

    @Test
    fun parse() {
        // Given
        val value = "+000123"
        // When
        val result: Integer = Integer.parse(value)
        // Then
        val expected: Integer = Integer.of(123)
        check(result == expected)
    }

    @Test
    fun parseOrNull() {
        // Given
        val value = "+000123"
        // When
        val result: Integer? = Integer.parseOrNull(value)
        // Then
        val expected: Integer = Integer.of(123)
        check(result == expected)
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinus() {
        // Given
        val integer: Integer = Integer.of(42)
        // When
        val result: Integer = -integer
        // Then
        val expected: Integer = Integer.of(-42)
        check(result == expected)
    }

    @Test
    fun plus() {
        // Given
        val x: Integer = Integer.of(9223372036854775807)
        val y: Integer = Integer.of(10)
        // When
        val sum: Integer = x + y
        // Then
        val expected: Integer = Integer.parse("9223372036854775817")
        check(sum == expected)
    }

    @Test
    fun minus() {
        // Given
        val x: Integer = Integer.of(-9223372036854775807)
        val y: Integer = Integer.of(10)
        // When
        val difference: Integer = x - y
        // Then
        val expected: Integer = Integer.parse("-9223372036854775817")
        check(difference == expected)
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEquality() {
        // Given
        val value: Long = Long.MAX_VALUE
        val x: Integer = Integer.of(value)
        val y: Integer = Integer.of(value)
        // When
        val equality: Boolean = x == y
        val hashConformity: Boolean = x.hashCode() == y.hashCode()
        // Then
        check(equality && hashConformity)
    }

    @Test
    fun compareTo() {
        // Given
        val x: Integer = Integer.of(Long.MIN_VALUE)
        val y: Integer = Integer.of(Long.MAX_VALUE)
        // When & Then
        check(x < y)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        // Given
        val integer: Integer = Integer.of(Long.MAX_VALUE)
        // When
        val result: String = integer.toString()
        // Then
        check(result == "9223372036854775807")
    }
}
