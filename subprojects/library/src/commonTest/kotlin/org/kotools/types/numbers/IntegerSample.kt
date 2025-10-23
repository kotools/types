package org.kotools.types.numbers

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerSample {
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
}
