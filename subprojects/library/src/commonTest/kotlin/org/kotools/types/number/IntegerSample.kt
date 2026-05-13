package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
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
        val x: Integer = Integer.of(9223372036854775807)
        val y: Integer = Integer.of(10)
        check(x + y == Integer.parse("9223372036854775817"))
        check(-x - y == Integer.parse("-9223372036854775817"))
        check(x * y == Integer.parse("92233720368547758070"))
    }

    @Test
    fun divisionByZeroSolution() {
        // Common code
        val x: Integer = Integer.of(12)
        val y: Integer = Integer.of(0)
        val quotient: Result<Integer> = runCatching { x / y }
        val remainder: Result<Integer> = runCatching { x % y }
        check(quotient.exceptionOrNull() is ArithmeticException)
        check(remainder.exceptionOrNull() is ArithmeticException)
    }

    // ------------------------------- Creations -------------------------------

    @Test
    fun of() {
        val value = 9_223_372_036_854_775_807

        val result: Integer = Integer.of(value)

        val resultString: String = result.toString()
        val expected: String = value.toString()
        check(resultString == expected)
    }

    @Test
    fun parse() {
        val value = "+000123"

        val result: Integer = Integer.parse(value)

        val expected: Integer = Integer.of(123)
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
        val expected: Integer = Integer.of(number)
        check(result == expected)
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun equalsOverride() {
        // Given
        val number: Long = Long.MAX_VALUE
        val x: Integer = Integer.of(number)
        val y: Integer = Integer.of(number)
        // When
        val result: Boolean = x == y
        // Then
        check(result)
    }

    @Test
    fun hashCodeOverride() {
        // Given
        val number: Long = Long.MAX_VALUE
        val integer: Integer = Integer.of(number)
        // When
        val result: Int = integer.hashCode()
        // Then
        val expected: Int = Integer.of(number)
            .hashCode()
        check(result == expected)
    }

    @Test
    fun compareTo() {
        // Given
        val x: Integer = Integer.of(Long.MIN_VALUE)
        val y: Integer = Integer.of(Long.MAX_VALUE)
        // When
        val result: Boolean = x < y // or x.compareTo(y) < 0
        // Then
        check(result)
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinus() {
        // Given
        val x: Integer = Integer.of(9223372036854775807)
        // When
        val result: Integer = -x
        // Then
        val expected: Integer = Integer.of(-9223372036854775807)
        check(result == expected)
    }

    @Test
    fun plus() {
        // Given
        val x: Integer = Integer.of(9223372036854775807)
        val y: Integer = Integer.of(2)
        // When
        val result: Integer = x + y
        // Then
        val expected: Integer = Integer.parse("9223372036854775809")
        check(result == expected)
    }

    @Test
    fun minus() {
        // Given
        val x: Integer = Integer.of(-9223372036854775807)
        val y: Integer = Integer.of(2)
        // When
        val result: Integer = x - y
        // Then
        val expected: Integer = Integer.parse("-9223372036854775809")
        check(result == expected)
    }

    @Test
    fun times() {
        // Given
        val x: Integer = Integer.of(9223372036854775807)
        val y: Integer = Integer.of(10)
        // When
        val result: Integer = x * y
        // Then
        val expected: Integer = Integer.parse("92233720368547758070")
        check(result == expected)
    }

    @Test
    fun div() {
        // Given
        val x: Integer = Integer.parse("922337203685477580700")
        val y: Integer = Integer.of(10)
        // When
        val result: Integer = x / y
        // Then
        val expected: Integer = Integer.parse("92233720368547758070")
        check(result == expected)
    }

    @Test
    fun divOrNull() {
        // Given
        val x: Integer = Integer.parse("922337203685477580700")
        val y: Integer = Integer.of(10)
        // When
        val result: Integer? = x.divOrNull(y)
        // Then
        val expected: Integer = Integer.parse("92233720368547758070")
        check(result == expected)
    }

    @Test
    fun rem() {
        // Given
        val x: Integer = Integer.of(42)
        val y: Integer = Integer.of(5)
        // When
        val result: Integer = x % y
        // Then
        val expected: Integer = Integer.of(2)
        check(result == expected)
    }

    @Test
    fun remOrNull() {
        // Given
        val x: Integer = Integer.of(42)
        val y: Integer = Integer.of(5)
        // When
        val result: Integer? = x.remOrNull(y)
        // Then
        val expected: Integer = Integer.of(2)
        check(result == expected)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        // Given
        val number = 9223372036854775807
        val integer: Integer = Integer.of(number)
        // When
        val result = "$integer" // or integer.toString()
        // Then
        check(result == "$number")
    }
}
