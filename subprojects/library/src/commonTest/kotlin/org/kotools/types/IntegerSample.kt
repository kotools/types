package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerSample {
    @Suppress("INTEGER_OVERFLOW")
    @Test
    fun overflowProblem() {
        // Addition
        val actualSum: Int = 2_147_483_647 + 1
        val expectedSum: Int = -2_147_483_648
        assertEquals(expectedSum, actualSum)

        // Subtraction
        val actualDifference: Int = -2_147_483_648 - 1
        val expectedDifference = 2_147_483_647
        assertEquals(expectedDifference, actualDifference)

        // Multiplication
        val actualProduct: Int = 2_147_483_647 * 2
        val expectedProduct: Int = -2
        assertEquals(expectedProduct, actualProduct)
    }

    @Test
    fun overflowSolution() {
        // Addition
        val actualSum: Integer = Integer(2_147_483_647) + Integer(1)
        val expectedSum = Integer(2_147_483_648)
        assertEquals(expectedSum, actualSum)

        // Subtraction
        val actualDifference: Integer = Integer(-2_147_483_648) - Integer(1)
        val expectedDifference = Integer(-2_147_483_649)
        assertEquals(expectedDifference, actualDifference)

        // Multiplication
        val actualProduct: Integer = Integer(2_147_483_647) * Integer(2)
        val expectedProduct = Integer(4_294_967_294)
        assertEquals(expectedProduct, actualProduct)
    }

    // ----------------------------- Constructors ------------------------------

    @Test
    fun constructorWithLong() {
        // Given
        val number = 9_223_372_036_854_775_807
        // When
        val integer = Integer(number)
        // Then
        val result: String = integer.toString()
        val expected: String = number.toString()
        assertEquals(expected, result)
    }

    @Test
    fun constructorWithDecimalString() {
        // Given
        val text = "18446744073709551614"
        // When
        val integer = Integer(text)
        // Then
        val result: String = integer.toString()
        assertEquals(expected = text, result)
    }

    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsWithIntegerHavingSameValue() {
        // Given
        val integer = Integer(Long.MAX_VALUE)
        val other = Integer(Long.MAX_VALUE)
        // When
        val result: Boolean = integer == other // or integer.equals(other)
        // Then
        assertTrue(result)
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

    // ------------------------- Arithmetic operations -------------------------

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

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        // Given
        val integer = Integer(9223372036854775807)
        // When
        val integerString: String = integer.toString()
        // Then
        assertEquals(expected = "9223372036854775807", integerString)
    }
}
