package org.kotools.types

import org.kotools.types.Integer.Literal.n
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerSample {
    @Suppress("INTEGER_OVERFLOW", "UnusedUnaryOperator")
    @Test
    fun overflowProblem() {
        2147483647 + 1 // Expected: 2147483648, Actual: -2147483648
        -2147483648 - 1 // Expected: -2147483649, Actual: 2147483647
        2147483647 * 2 // Expected: 4294967294, Actual: -2
    }

    @Test
    fun overflowSolution() {
        2147483647.n + 1.n // 2147483648
        (-2147483648).n - 1.n // -2147483649
        2147483647.n * 2.n // 4294967294
    }

    @Test
    fun constructorLong() {
        // Given
        val number = 9223372036854775807
        // When
        val integer = Integer(number)
        // Then
        assertEquals(expected = "$number", "$integer")
    }

    @Test
    fun constructorString() {
        // Given
        val text = "18446744073709551614"
        // When
        val integer = Integer(text)
        // Then
        assertEquals(expected = text, "$integer")
    }

    @Test
    fun equalsOverride() {
        // Given
        val x = Integer(9223372036854775807)
        val y = Integer(9223372036854775807)
        // When
        val equality: Boolean = x == y // or x.equals(y)
        // Then
        assertTrue(equality)
    }

    @Test
    fun hashCodeOverride() {
        // Given
        val integer = Integer(9223372036854775807)
        // When
        val hashCode: Int = integer.hashCode()
        // Then
        val expected: Int = Integer(9223372036854775807)
            .hashCode()
        assertEquals(expected, hashCode)
    }

    @Test
    fun unaryMinus() {
        // Given
        val integer = Integer(9223372036854775807)
        // When
        val negative: Integer = -integer // or integer.unaryMinus()
        // Then
        val expected = Integer(-9223372036854775807)
        assertEquals(expected, negative)
    }

    @Test
    fun plus() {
        // Given
        val x = Integer(9223372036854775807)
        val y = Integer(9223372036854775807)
        // When
        val sum: Integer = x + y
        // Then
        val expected = Integer("18446744073709551614")
        assertEquals(expected, sum)
    }

    @Test
    fun minus() {
        // Given
        val x = Integer(-9223372036854775807)
        val y = Integer(9223372036854775807)
        // When
        val difference: Integer = x - y
        // Then
        val expected = Integer("-18446744073709551614")
        assertEquals(expected, difference)
    }

    @Test
    fun times() {
        // Given
        val x = Integer(9223372036854775807)
        val y = Integer(10)
        // When
        val product: Integer = x * y
        // Then
        val expected = Integer("92233720368547758070")
        assertEquals(expected, product)
    }

    @Test
    fun toStringOverride() {
        // Given
        val integer = Integer(9223372036854775807)
        // When
        val integerString: String = integer.toString()
        // Then
        assertEquals(expected = "9223372036854775807", integerString)
    }

    @Test
    fun nOnInt() {
        val integer: Integer = 2147483647.n
        val expected = Integer(2147483647L)
        assertEquals(expected, integer)
    }

    @Test
    fun nOnLong() {
        val integer: Integer = 2147483647L.n
        val expected = Integer(2147483647L)
        assertEquals(expected, integer)
    }
}
