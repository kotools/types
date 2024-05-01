package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroTest {
    @Test
    fun structural_equality_should_pass_with_another_Zero() {
        val first = Zero()
        val second = Zero()
        assertEquals(first, second)
        val firstHashCode: Int = first.hashCode()
        val secondHashCode: Int = second.hashCode()
        assertEquals(firstHashCode, secondHashCode)
    }

    @Test
    fun structural_equality_should_fail_with_null() {
        val first = Zero()
        val second: Any? = null
        assertNotEquals(second, first)
        val firstHashCode: Int = first.hashCode()
        val secondHashCode: Int = second.hashCode()
        assertNotEquals(secondHashCode, firstHashCode)
    }

    @Test
    fun structural_equality_should_fail_with_another_type_than_Zero() {
        val first = Zero()
        val second: Any = "oops"
        assertNotEquals(second, first)
        val firstHashCode: Int = first.hashCode()
        val secondHashCode: Int = second.hashCode()
        assertNotEquals(secondHashCode, firstHashCode)
    }

    @Test
    fun toByte_should_pass() {
        val actual: Byte = Zero()
            .toByte()
        val expected: Byte = 0
        assertEquals(expected, actual)
    }

    @Test
    fun toShort_should_pass() {
        val actual: Short = Zero()
            .toShort()
        val expected: Short = 0
        assertEquals(expected, actual)
    }

    @Test
    fun toString_should_pass() {
        val actual: String = Zero()
            .toString()
        val expected = "0"
        assertEquals(expected, actual)
    }
}

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroCompanionTest {
    @Test
    fun fromByte_should_pass_with_a_Byte_that_equals_zero() {
        val number: Byte = 0
        Zero.fromByte(number)
    }

    @Test
    fun fromByte_should_fail_with_a_Byte_other_than_zero() {
        val number: Byte = listOf(Byte.MIN_VALUE..-1, 1..Byte.MAX_VALUE)
            .random()
            .random()
            .toByte()
        val exception: IllegalArgumentException = assertFailsWith {
            Zero.fromByte(number)
        }
        val actual: String? = exception.message
        val expected = "'$number' shouldn't be other than zero."
        assertEquals(expected, actual)
    }

    @Test
    fun fromByteOrNull_should_pass_with_a_Byte_that_equals_zero() {
        val number: Byte = 0
        val actual: Zero? = Zero.fromByteOrNull(number)
        assertNotNull(actual)
    }

    @Test
    fun fromByteOrNull_should_fail_with_a_Byte_other_than_zero() {
        val number: Byte = listOf(Byte.MIN_VALUE..-1, 1..Byte.MAX_VALUE)
            .random()
            .random()
            .toByte()
        val actual: Zero? = Zero.fromByteOrNull(number)
        assertNull(actual)
    }

    @Test
    fun fromShort_should_pass_with_a_Short_that_equals_zero() {
        val number: Short = 0
        Zero.fromShort(number)
    }

    @Test
    fun fromShort_should_fail_with_a_Short_other_than_zero() {
        val number: Short = listOf(Short.MIN_VALUE..-1, 1..Short.MAX_VALUE)
            .random()
            .random()
            .toShort()
        val exception: IllegalArgumentException = assertFailsWith {
            Zero.fromShort(number)
        }
        val actual: String? = exception.message
        val expected = "'$number' shouldn't be other than zero."
        assertEquals(expected, actual)
    }

    @Test
    fun fromShortOrNull_should_pass_with_a_Short_that_equals_zero() {
        val number: Short = 0
        val actual: Zero? = Zero.fromShortOrNull(number)
        assertNotNull(actual)
    }

    @Test
    fun fromShortOrNull_should_fail_with_a_Short_other_than_zero() {
        val number: Short = listOf(Short.MIN_VALUE..-1, 1..Short.MAX_VALUE)
            .random()
            .random()
            .toShort()
        val actual: Zero? = Zero.fromShortOrNull(number)
        assertNull(actual)
    }
}
