package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
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

    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsWithIntegerHavingSameValue() {
        // Given
        val number = 9_223_372_036_854_775_807
        val integer = Integer(number)
        val other = Integer(number)
        // When
        val result: Boolean = integer == other // or integer.equals(other)
        // Then
        assertTrue(result)
    }

    @Test
    fun hashCodeOverride() {
        // Given
        val number = 9_223_372_036_854_775_807
        val integer = Integer(number)
        // When
        val result: Int = integer.hashCode()
        // Then
        val expected: Int = Integer(number)
            .hashCode()
        assertEquals(expected, result)
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun plus() {
        // Given
        val number = 9_223_372_036_854_775_807
        val x = Integer(number)
        val y = Integer(number)
        // When
        val result: Integer = x + y
        // Then
        val expected: Integer = Integer.parse("18446744073709551614")
        assertEquals(expected, result)
    }

    @Test
    fun minus() {
        // Given
        val x = Integer(-9_223_372_036_854_775_807)
        val y = Integer(9_223_372_036_854_775_807)
        // When
        val result: Integer = x - y
        // Then
        val expected: Integer = Integer.parse("-18446744073709551614")
        assertEquals(expected, result)
    }

    @Test
    fun times() {
        // Given
        val x = Integer(9_223_372_036_854_775_807)
        val y = Integer(10)
        // When
        val result: Integer = x * y
        // Then
        val expected: Integer = Integer.parse("92233720368547758070")
        assertEquals(expected, result)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        // Given
        val number = 9_223_372_036_854_775_807
        val integer = Integer(number)
        // When
        val result: String = integer.toString()
        // Then
        val expected: String = number.toString()
        assertEquals(expected, result)
    }

    // ----------------------- Class-level declarations ------------------------

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
