package org.kotools.types

import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerSample {
    @Suppress("INTEGER_OVERFLOW")
    @Test
    fun overflowProblem() {
        val x = 9223372036854775807
        val y = 2
        check(x + y == -9223372036854775807) // instead of 9223372036854775809
        check(-x - y == 9223372036854775807) // instead of -9223372036854775809
        check(x * y == -2L) // instead of 18446744073709551614
    }

    @Test
    fun overflowSolution() {
        val x: Integer = Integer.from(9223372036854775807)
        val y: Integer = Integer.from(2)
        check(x + y == Integer.fromDecimal("9223372036854775809"))
        check(-x - y == Integer.fromDecimal("-9223372036854775809"))
        check(x * y == Integer.fromDecimal("18446744073709551614"))
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
        val x: Integer = Integer.fromDecimal("123")
        val y: Integer = Integer.fromDecimal("123456")
        // When
        val result: Int = x.compareTo(y)
        // Then
        val expected: Int = "$x".compareTo("$y")
        check(result == expected)
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinus() {
        // Given
        val number = 123456L
        val x: Integer = Integer.from(number)
        // When
        val result: Integer = -x // or x.unaryMinus()
        // Then
        val expected: Integer = Integer.from(-number)
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
        val x: Integer = Integer.from(21)
        val y: Integer = Integer.from(5)
        // When
        val result: Integer = x / y
        // Then
        val expected: Integer = Integer.from(4)
        check(result == expected)
    }

    @Test
    fun rem() {
        // Given
        val x: Integer = Integer.from(22)
        val y: Integer = Integer.from(5)
        // When
        val result: Integer = x % y
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

    @Test
    fun zero() {
        // When
        val result: Integer = Integer.zero()
        // Then
        val expected: Integer = Integer.from(0)
        check(result == expected)
    }
}
