package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonZeroIntegerSample {
    @Test
    fun constructorWithNonZeroInt() {
        // Given
        val number = 2_147_483_647
        // When
        val result = NonZeroInteger(number)
        // Then
        val resultAsString: String = result.toString()
        val expected: String = number.toString()
        assertEquals(expected, resultAsString)
    }

    @Test
    fun constructorWithNonZeroLong() {
        // Given
        val number = 9_223_372_036_854_775_807
        // When
        val result = NonZeroInteger(number)
        // Then
        val resultAsString: String = result.toString()
        val expected: String = number.toString()
        assertEquals(expected, resultAsString)
    }

    @Test
    fun toInteger() {
        // Given
        val number = 2_147_483_647
        val x = NonZeroInteger(number)
        // When
        val result: Integer = x.toInteger()
        // Then
        val expected = Integer(number)
        assertEquals(expected, result)
    }

    @Test
    fun toStringOverride() {
        // Given
        val number = 2_147_483_647
        val integer = NonZeroInteger(number)
        // When
        val result: String = integer.toString()
        // Then
        val expected: String = number.toString()
        assertEquals(expected, result)
    }
}
