package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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
        check(x + y == Integer.parse("9223372036854775809"))
        check(Integer.parse("-$x") - y == Integer.parse("-9223372036854775809"))
        check(x * y == Integer.parse("18446744073709551614"))
    }

    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsOverride() {
        val number: Long = Long.MAX_VALUE
        val x: Integer = Integer.from(number)
        val y: Integer = Integer.from(number)
        check(x == y)
    }

    @Test
    fun hashCodeOverride() {
        val number: Long = Long.MAX_VALUE
        val x: Integer = Integer.from(number)
        val y: Integer = Integer.from(number)
        check(x.hashCode() == y.hashCode())
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun plus() {
        val x: Integer = Integer.from(9223372036854775807)
        val y: Integer = Integer.from(2)
        val expected: Integer = Integer.parse("9223372036854775809")
        check(x + y == expected)
    }

    @Test
    fun minus() {
        val x: Integer = Integer.from(-9223372036854775807)
        val y: Integer = Integer.from(2)
        val expected: Integer = Integer.parse("-9223372036854775809")
        check(x - y == expected)
    }

    @Test
    fun times() {
        val x: Integer = Integer.from(9223372036854775807)
        val y: Integer = Integer.from(10)
        val expected: Integer = Integer.parse("92233720368547758070")
        check(x * y == expected)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        val number = 9223372036854775807
        val integer: Integer = Integer.from(number)
        check("$integer" == "$number")
    }

    // ----------------------- Class-level declarations ------------------------

    @Test
    fun from() {
        val number = 9223372036854775807
        val integer: Integer = Integer.from(number)
        check("$integer" == "$number")
    }

    @Test
    fun parseWithDecimalString() {
        // Given
        val text = "1234"
        // When
        val integer: Integer = Integer.parse(text)
        // Then
        val result: String = integer.toString()
        assertEquals(expected = text, result)
    }

    @Test
    fun parseOrNullWithDecimalString() {
        // Given
        val text = "1234"
        // When
        val integer: Integer? = Integer.parseOrNull(text)
        // Then
        assertNotNull(integer)
        val result: String = integer.toString()
        assertEquals(expected = text, result)
    }
}
