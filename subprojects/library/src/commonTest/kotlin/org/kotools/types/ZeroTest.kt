package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.InvalidZero
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
    fun toInt_should_pass() {
        val actual: Int = Zero()
            .toInt()
        val expected = 0
        assertEquals(expected, actual)
    }

    @Test
    fun toLong_should_pass() {
        val actual: Long = Zero()
            .toLong()
        val expected = 0L
        assertEquals(expected, actual)
    }

    @Test
    fun toFloat_should_pass() {
        val actual: Float = Zero()
            .toFloat()
        val expected = 0f
        assertEquals(expected, actual)
    }

    @Test
    fun toDouble_should_pass() {
        val actual: Double = Zero()
            .toDouble()
        val expected = 0.0
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
        val expected: String = InvalidZero(number)
            .toString()
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
        val expected: String = InvalidZero(number)
            .toString()
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

    @Test
    fun fromInt_should_pass_with_an_Int_that_equals_zero() {
        Zero.fromInt(0)
    }

    @Test
    fun fromInt_should_fail_with_an_Int_other_than_zero() {
        val number: Int = listOf(Int.MIN_VALUE..-1, 1..Int.MAX_VALUE)
            .random()
            .random()
        val exception: IllegalArgumentException = assertFailsWith {
            Zero.fromInt(number)
        }
        val actual: String? = exception.message
        val expected: String = InvalidZero(number)
            .toString()
        assertEquals(expected, actual)
    }

    @Test
    fun fromIntOrNull_should_pass_with_an_Int_that_equals_zero() {
        val number = 0
        val actual: Zero? = Zero.fromIntOrNull(number)
        assertNotNull(actual)
    }

    @Test
    fun fromIntOrNull_should_fail_with_an_Int_other_than_zero() {
        val number: Int = listOf(Int.MIN_VALUE..-1, 1..Int.MAX_VALUE)
            .random()
            .random()
        val actual: Zero? = Zero.fromIntOrNull(number)
        assertNull(actual)
    }

    @Test
    fun fromLong_should_pass_with_a_Long_that_equals_zero() {
        val number = 0L
        Zero.fromLong(number)
    }

    @Test
    fun fromLong_should_fail_with_a_Long_other_than_zero() {
        val number: Long = listOf(Long.MIN_VALUE..-1, 1..Long.MAX_VALUE)
            .random()
            .random()
        val exception: IllegalArgumentException = assertFailsWith {
            Zero.fromLong(number)
        }
        val actual: String? = exception.message
        val expected: String = InvalidZero(number)
            .toString()
        assertEquals(expected, actual)
    }

    @Test
    fun fromLongOrNull_should_pass_with_a_Long_that_equals_zero() {
        val number = 0L
        val actual: Zero? = Zero.fromLongOrNull(number)
        assertNotNull(actual)
    }

    @Test
    fun fromLongOrNull_should_fail_with_a_Long_other_than_zero() {
        val number: Long = listOf(Long.MIN_VALUE..-1, 1..Long.MAX_VALUE)
            .random()
            .random()
        val actual: Zero? = Zero.fromLongOrNull(number)
        assertNull(actual)
    }

    @Test
    fun fromFloat_should_pass_with_a_Float_that_equals_zero() {
        val number = 0f
        Zero.fromFloat(number)
    }

    @Test
    fun fromFloat_should_fail_with_a_Float_other_than_zero() {
        val number: Float = listOf(Long.MIN_VALUE..-1, 1..Long.MAX_VALUE)
            .random()
            .random()
            .toFloat()
        val exception: IllegalArgumentException = assertFailsWith {
            Zero.fromFloat(number)
        }
        val actual: String? = exception.message
        val expected: String = InvalidZero(number)
            .toString()
        assertEquals(expected, actual)
    }

    @Test
    fun fromFloatOrNull_should_pass_with_a_Float_that_equals_zero() {
        val number = 0f
        val actual: Zero? = Zero.fromFloatOrNull(number)
        assertNotNull(actual)
    }

    @Test
    fun fromFloatOrNull_should_fail_with_a_Float_other_than_zero() {
        val number: Float = listOf(Long.MIN_VALUE..-1, 1..Long.MAX_VALUE)
            .random()
            .random()
            .toFloat()
        val actual: Zero? = Zero.fromFloatOrNull(number)
        assertNull(actual)
    }

    @Test
    fun fromDoubleOrNull_should_pass_with_a_Double_that_equals_zero() {
        val number = 0.0
        val actual: Zero? = Zero.fromDoubleOrNull(number)
        assertNotNull(actual)
    }

    @Test
    fun fromDoubleOrNull_should_fail_with_a_Double_other_than_zero() {
        val number: Double = listOf(Long.MIN_VALUE..-1, 1..Long.MAX_VALUE)
            .random()
            .random()
            .toDouble()
        val actual: Zero? = Zero.fromDoubleOrNull(number)
        assertNull(actual)
    }
}
