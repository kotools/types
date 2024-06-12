package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.internal.simpleNameOf
import org.kotools.types.internal.InvalidZero
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroTest {
    // -------------------- Structural equality operations ---------------------

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

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun compareTo_should_return_zero_with_the_same_Byte() {
        val zero = Zero()
        val other: Byte = 0
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareTo_should_return_a_negative_number_with_a_greater_Byte() {
        val zero = Zero()
        val other: Byte = (1..Byte.MAX_VALUE)
            .random()
            .toByte()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareTo_should_return_a_positive_number_with_a_less_Byte() {
        val zero = Zero()
        val other: Byte = (Byte.MIN_VALUE..-1)
            .random()
            .toByte()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    @Test
    fun compareTo_should_return_zero_with_the_same_Short() {
        val zero = Zero()
        val other: Short = 0
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareTo_should_return_a_negative_number_with_a_greater_Short() {
        val zero = Zero()
        val other: Short = (1..Short.MAX_VALUE)
            .random()
            .toShort()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareTo_should_return_a_positive_number_with_a_less_Short() {
        val zero = Zero()
        val other: Short = (Short.MIN_VALUE..-1)
            .random()
            .toShort()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    @Test
    fun compareTo_should_return_zero_with_the_same_Int() {
        val zero = Zero()
        val other = 0
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareTo_should_return_a_negative_number_with_a_greater_Int() {
        val zero = Zero()
        val other: Int = (1..Int.MAX_VALUE)
            .random()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareTo_should_return_a_positive_number_with_a_less_Int() {
        val zero = Zero()
        val other: Int = (Int.MIN_VALUE..-1)
            .random()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    @Test
    fun compareTo_should_return_zero_with_the_same_Long() {
        val zero = Zero()
        val other: Long = 0
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareTo_should_return_a_negative_number_with_a_greater_Long() {
        val zero = Zero()
        val other: Long = (1..Long.MAX_VALUE)
            .random()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareTo_should_return_a_positive_number_with_a_less_Long() {
        val zero = Zero()
        val other: Long = (Long.MIN_VALUE..-1)
            .random()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    @Test
    fun compareTo_should_return_zero_with_the_same_Float() {
        val zero = Zero()
        val other = 0f
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareTo_should_return_a_negative_number_with_a_greater_Float() {
        val zero = Zero()
        val other: Float = (1..Long.MAX_VALUE)
            .random()
            .toFloat()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareTo_should_return_a_positive_number_with_a_less_Float() {
        val zero = Zero()
        val other: Float = (Long.MIN_VALUE..-1)
            .random()
            .toFloat()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    @Test
    fun compareTo_should_return_zero_with_the_same_Double() {
        val zero = Zero()
        val other = 0.0
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareTo_should_return_a_negative_number_with_a_greater_Double() {
        val zero = Zero()
        val other: Double = (1..Long.MAX_VALUE)
            .random()
            .toDouble()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareTo_should_return_a_positive_number_with_a_less_Double() {
        val zero = Zero()
        val other: Double = (Long.MIN_VALUE..-1)
            .random()
            .toDouble()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    // ------------------------------ Conversions ------------------------------

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
    fun pattern_should_pass() {
        val actual: String = Zero.PATTERN
        val expected = "^[+-]?0+(?:\\.0+)?\$"
        assertEquals(expected, actual)
    }

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
    fun fromDouble_should_pass_with_a_Double_that_equals_zero() {
        val number = 0.0
        Zero.fromDouble(number)
    }

    @Test
    fun fromDouble_should_fail_with_a_Double_other_than_zero() {
        val number: Double = listOf(Long.MIN_VALUE..-1, 1..Long.MAX_VALUE)
            .random()
            .random()
            .toDouble()
        val exception: IllegalArgumentException = assertFailsWith {
            Zero.fromDouble(number)
        }
        val actual: String? = exception.message
        val expected: String = InvalidZero(number)
            .toString()
        assertEquals(expected, actual)
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

    @Test
    fun fromString_should_pass_with_a_valid_number() {
        val numbers: List<Any> = listOf(
            0, 0.0,
            "+0", "+000", "+0.000", "+000.000", // with unary plus
            "-0", "-000", "-0.000", "-000.000" // with unary minus
        )
        numbers.forEach(Zero.Companion::fromString)
    }

    @Test
    fun fromString_should_fail_with_an_invalid_number() {
        val numbers: List<Any> = listOf(
            ".0", "+.0", "-.0", // integer part missing
            "0,0", "+0,0", "-0,0", // comma as decimal point
            "0.", "+0.", "-0.", // decimal part missing
            "hello world", "123456789" // not zero number
        )
        numbers.forEach {
            val exception: IllegalArgumentException = assertFailsWith {
                Zero.fromString(it)
            }
            val actual: String? = exception.message?.takeIf(String::isNotBlank)
            assertNotNull(actual, message = "Exception should have a message.")
            val expected = "'$it' is not a valid representation of zero."
            assertEquals(expected, actual)
        }
    }

    @Test
    fun fromStringOrNull_should_pass_with_a_valid_number() {
        val numbers: List<Any> = listOf(
            0, 0.0,
            "+0", "+000", "+0.000", "+000.000", // with unary plus
            "-0", "-000", "-0.000", "-000.000" // with unary minus
        )
        numbers.forEach {
            val actual: Zero? = Zero.fromStringOrNull(it)
            val zeroType: String = simpleNameOf<Zero>()
            val message = "Converting '$it' to $zeroType should pass."
            assertNotNull(actual, message)
        }
    }

    @Test
    fun fromStringOrNull_should_fail_with_an_invalid_number() {
        val numbers: List<Any> = listOf(
            ".0", "+.0", "-.0", // integer part missing
            "0,0", "+0,0", "-0,0", // comma as decimal point
            "0.", "+0.", "-0.", // decimal part missing
            "hello world", "123456789" // not zero number
        )
        numbers.forEach {
            val actual: Zero? = Zero.fromStringOrNull(it)
            val zeroType: String = simpleNameOf<Zero>()
            val message = "Converting '$it' to $zeroType should fail."
            assertNull(actual, message)
        }
    }

    @Test
    fun orNull_should_pass_with_a_valid_number() {
        val numbers: List<Any> = listOf(
            0, 0.0,
            "+0", "+000", "+0.000", "+000.000", // with unary plus
            "-0", "-000", "-0.000", "-000.000" // with unary minus
        )
        numbers.forEach {
            val actual: Zero? = Zero.orNull(it)
            val zeroType: String = simpleNameOf<Zero>()
            val message = "Converting '$it' to $zeroType should pass."
            assertNotNull(actual, message)
        }
    }

    @Test
    fun orNull_should_fail_with_an_invalid_number() {
        val numbers: List<Any> = listOf(
            ".0", "+.0", "-.0", // integer part missing
            "0,0", "+0,0", "-0,0", // comma as decimal point
            "0.", "+0.", "-0.", // decimal part missing
            "hello world", "123456789" // not zero number
        )
        numbers.forEach {
            val actual: Zero? = Zero.orNull(it)
            val zeroType: String = simpleNameOf<Zero>()
            val message = "Converting '$it' to $zeroType should fail."
            assertNull(actual, message)
        }
    }

    @Test
    fun orThrow_should_pass_with_a_valid_number() {
        val numbers: List<Any> = listOf(
            0, 0.0,
            "+0", "+000", "+0.000", "+000.000", // with unary plus
            "-0", "-000", "-0.000", "-000.000" // with unary minus
        )
        numbers.forEach(Zero.Companion::orThrow)
    }

    @Test
    fun orThrow_should_fail_with_an_invalid_number() {
        val numbers: List<Any> = listOf(
            ".0", "+.0", "-.0", // integer part missing
            "0,0", "+0,0", "-0,0", // comma as decimal point
            "0.", "+0.", "-0.", // decimal part missing
            "hello world", "123456789" // not zero number
        )
        numbers.forEach {
            val exception: IllegalArgumentException = assertFailsWith {
                Zero.orThrow(it)
            }
            val actual: String? = exception.message?.takeIf(String::isNotBlank)
            assertNotNull(actual, message = "Exception should have a message.")
            val expected = "'$it' is not a valid representation of zero."
            assertEquals(expected, actual)
        }
    }
}
