package org.kotools.types

import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerSample {
    // ------------------------------ Motivations ------------------------------

    @Suppress("INTEGER_OVERFLOW")
    @Test
    fun overflowProblem() {
        val x = 9223372036854775807
        val y = 10
        check(x + y == -9223372036854775799) // instead of 9223372036854775817
        check(-x - y == 9223372036854775799) // instead of -9223372036854775817
        check(x * y == -10L) // instead of 92233720368547758070
    }

    @Test
    fun overflowSolution() {
        val x: Integer = Integer.from(9223372036854775807)
        val y: Integer = Integer.from(10)
        check(x + y == Integer.fromDecimal("9223372036854775817"))
        check(-x - y == Integer.fromDecimal("-9223372036854775817"))
        check(x * y == Integer.fromDecimal("92233720368547758070"))
    }

    @Test
    fun divisionByZeroSolution() {
        // Common code
        val x: Integer = Integer.from(12)
        val y: Integer = Integer.from(0)
        val quotient: Result<Integer> = kotlin.runCatching { x / y }
        val remainder: Result<Integer> = kotlin.runCatching { x % y }
        check(quotient.exceptionOrNull() is ArithmeticException)
        check(remainder.exceptionOrNull() is ArithmeticException)
    }

    // ------------------------------- Creations -------------------------------

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

    @Test
    fun compareTo() {
        // Given
        val x: Integer = Integer.from(Long.MIN_VALUE)
        val y: Integer = Integer.from(Long.MAX_VALUE)
        // When
        val result: Boolean = x < y // or x.compareTo(y) < 0
        // Then
        check(result)
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinus() {
        // Given
        val x: Integer = Integer.from(9223372036854775807)
        // When
        val result: Integer = -x
        // Then
        val expected: Integer = Integer.from(-9223372036854775807)
        check(result == expected)
    }

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

    @Test
    fun div() {
        // Given
        val x: Integer = Integer.fromDecimal("922337203685477580700")
        val y: Integer = Integer.from(10)
        // When
        val result: Integer = x / y
        // Then
        val expected: Integer = Integer.fromDecimal("92233720368547758070")
        check(result == expected)
    }

    @Test
    fun divOrNull() {
        // Given
        val x: Integer = Integer.fromDecimal("922337203685477580700")
        val y: Integer = Integer.from(10)
        // When
        val result: Integer? = x.divOrNull(y)
        // Then
        val expected: Integer = Integer.fromDecimal("92233720368547758070")
        check(result == expected)
    }

    @Test
    fun rem() {
        // Given
        val x: Integer = Integer.from(42)
        val y: Integer = Integer.from(5)
        // When
        val result: Integer = x % y
        // Then
        val expected: Integer = Integer.from(2)
        check(result == expected)
    }

    @Test
    fun remOrNull() {
        // Given
        val x: Integer = Integer.from(42)
        val y: Integer = Integer.from(5)
        // When
        val result: Integer? = x.remOrNull(y)
        // Then
        val expected: Integer = Integer.from(2)
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
}
