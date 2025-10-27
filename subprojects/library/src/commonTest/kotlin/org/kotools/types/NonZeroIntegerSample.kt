package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonZeroIntegerSample {
    @Test
    fun constructorInt() {
        // Given
        val number = 2147483647
        // When
        val result = NonZeroInteger(number)
        // Then
        val resultAsString: String = result.toString()
        val expected: String = number.toString()
        assertEquals(expected, resultAsString)
    }

    @Test
    fun toStringOverride() {
        // Given
        val number = 2147483647
        val integer = NonZeroInteger(number)
        // When
        val result: String = integer.toString()
        // Then
        val expected: String = number.toString()
        assertEquals(expected, result)
    }
}
