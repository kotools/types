package org.kotools.types

import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerSample {
    @Suppress("INTEGER_OVERFLOW")
    @Test
    fun overflowProblem() {
        // Addition
        val sum: Long = 9223372036854775807 + 2
        check(sum == -9223372036854775807)
        // Expected: 9223372036854775807 + 2 = 9223372036854775809

        // Subtraction
        val difference: Long = -9223372036854775807 - 2
        check(difference == 9223372036854775807)
        // Expected: -9223372036854775807 - 2 = -9223372036854775809

        // Multiplication
        val product: Long = 9223372036854775807 * 10
        check(product == -10L)
        // Expected: 9223372036854775807 * 10 = 92233720368547758070
    }

    @Test
    fun overflowSolution() {
        // Addition
        val sum: Integer = Integer.from(9223372036854775807) + Integer.from(2)
        check(sum == Integer.fromDecimal("9223372036854775809"))

        // Subtraction
        val difference: Integer =
            Integer.from(-9223372036854775807) - Integer.from(2)
        check(difference == Integer.fromDecimal("-9223372036854775809"))

        // Multiplication
        val product: Integer =
            Integer.from(9223372036854775807) * Integer.from(10)
        check(product == Integer.fromDecimal("92233720368547758070"))
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun equalsOverride() {
        // Given
        val number: Long = Long.MAX_VALUE
        val x: Integer = Integer.from(number)
        val y: Integer = Integer.from(number)
        // When
        val result: Boolean = x == y
        // Then
        check(result)
    }

    @Test
    fun hashCodeOverride() {
        // Given
        val number: Long = Long.MAX_VALUE
        val integer: Integer = Integer.from(number)
        // When
        val result: Int = integer.hashCode()
        // Then
        val expected: Int = Integer.from(number)
            .hashCode()
        check(result == expected)
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun plus() {
        // Given
        val x: Integer = Integer.from(9223372036854775807)
        val y: Integer = Integer.from(2)
        // When
        val result: Integer = x + y
        // Then
        val expected: Integer = Integer.fromDecimal("9223372036854775809")
        check(result == expected)
    }

    @Test
    fun minus() {
        // Given
        val x: Integer = Integer.from(-9223372036854775807)
        val y: Integer = Integer.from(2)
        // When
        val result: Integer = x - y
        // Then
        val expected: Integer = Integer.fromDecimal("-9223372036854775809")
        check(result == expected)
    }

    @Test
    fun times() {
        // Given
        val x: Integer = Integer.from(9223372036854775807)
        val y: Integer = Integer.from(10)
        // When
        val result: Integer = x * y
        // Then
        val expected: Integer = Integer.fromDecimal("92233720368547758070")
        check(result == expected)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        // Given
        val number = 9223372036854775807
        val integer: Integer = Integer.from(number)
        // When
        val result = "$integer" // or integer.toString()
        // Then
        check(result == "$number")
    }

    // ----------------------- Class-level declarations ------------------------

    @Test
    fun from() {
        // Given
        val number = 9223372036854775807
        // When
        val result: Integer = Integer.from(number)
        // Then
        check("$result" == "$number")
    }

    @Test
    fun fromDecimal() {
        // Given
        val number = 123456L
        val text = "$number"
        // When
        val result: Integer = Integer.fromDecimal(text)
        // Then
        val expected: Integer = Integer.from(number)
        check(result == expected)
    }

    @Test
    fun fromDecimalOrNull() {
        // Given
        val number = 123456L
        val text = "$number"
        // When
        val result: Integer? = Integer.fromDecimalOrNull(text)
        // Then
        val expected: Integer = Integer.from(number)
        check(result == expected)
    }
}
