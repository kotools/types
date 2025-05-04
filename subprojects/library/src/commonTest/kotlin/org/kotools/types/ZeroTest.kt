package org.kotools.types

import kotools.types.internal.hashCodeOf
import org.kotools.types.internal.ExceptionMessage
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroTest {
    // -------------------- Structural equality operations ---------------------

    @Test
    fun equalsShouldPassWithZero() {
        val first = Zero()
        val second = Zero()
        val actual: Boolean = first.equals(second)
        assertTrue(actual)
    }

    @Test
    fun equalsShouldFailWithAnotherTypeThanZero() {
        val first = Zero()
        val second: Any = "oops"
        val actual: Boolean = first.equals(second)
        assertFalse(actual)
    }

    @Test
    fun hashCodeShouldPass() {
        val actual: Int = Zero()
            .hashCode()
        val expected: Int = 0.toByte()
            .let(::hashCodeOf)
        assertEquals(expected, actual)
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun compareToShouldReturnZeroWithSameByte() {
        val zero = Zero()
        val other: Byte = 0
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareToShouldReturnNegativeNumberWithGreaterByte() {
        val zero = Zero()
        val other: Byte = (1..Byte.MAX_VALUE)
            .random()
            .toByte()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareToShouldReturnPositiveNumberWithLessByte() {
        val zero = Zero()
        val other: Byte = (Byte.MIN_VALUE..-1)
            .random()
            .toByte()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    @Test
    fun compareToShouldReturnZeroWithSameShort() {
        val zero = Zero()
        val other: Short = 0
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareToShouldReturnNegativeNumberWithGreaterShort() {
        val zero = Zero()
        val other: Short = (1..Short.MAX_VALUE)
            .random()
            .toShort()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareToShouldReturnPositiveNumberWithLessShort() {
        val zero = Zero()
        val other: Short = (Short.MIN_VALUE..-1)
            .random()
            .toShort()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    @Test
    fun compareToShouldReturnZeroWithSameInt() {
        val zero = Zero()
        val other = 0
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareToShouldReturnNegativeNumberWithGreaterInt() {
        val zero = Zero()
        val other: Int = (1..Int.MAX_VALUE)
            .random()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareToShouldReturnPositiveNumberWithLessInt() {
        val zero = Zero()
        val other: Int = (Int.MIN_VALUE..-1)
            .random()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    @Test
    fun compareToShouldReturnZeroWithSameLong() {
        val zero = Zero()
        val other: Long = 0
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareToShouldReturnNegativeNumberWithGreaterLong() {
        val zero = Zero()
        val other: Long = (1..Long.MAX_VALUE)
            .random()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareToShouldReturnPositiveNumberWithLessLong() {
        val zero = Zero()
        val other: Long = (Long.MIN_VALUE..-1)
            .random()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    @Test
    fun compareToShouldReturnZeroWithSameFloat() {
        val zero = Zero()
        val other = 0f
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareToShouldReturnNegativeNumberWithGreaterFloat() {
        val zero = Zero()
        val other: Float = (1..Long.MAX_VALUE)
            .random()
            .toFloat()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareToShouldReturnPositiveNumberWithLessFloat() {
        val zero = Zero()
        val other: Float = (Long.MIN_VALUE..-1)
            .random()
            .toFloat()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    @Test
    fun compareToShouldReturnZeroWithSameDouble() {
        val zero = Zero()
        val other = 0.0
        val actual: Int = zero.compareTo(other)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareToShouldReturnNegativeNumberWithGreaterDouble() {
        val zero = Zero()
        val other: Double = (1..Long.MAX_VALUE)
            .random()
            .toDouble()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual < 0 }
    }

    @Test
    fun compareToShouldReturnPositiveNumberWithLessDouble() {
        val zero = Zero()
        val other: Double = (Long.MIN_VALUE..-1)
            .random()
            .toDouble()
        val actual: Int = zero.compareTo(other)
        assertTrue { actual > 0 }
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toByteShouldPass() {
        val actual: Byte = Zero()
            .toByte()
        val expected: Byte = 0
        assertEquals(expected, actual)
    }

    @Test
    fun toShortShouldPass() {
        val actual: Short = Zero()
            .toShort()
        val expected: Short = 0
        assertEquals(expected, actual)
    }

    @Test
    fun toIntShouldPass() {
        val actual: Int = Zero()
            .toInt()
        val expected = 0
        assertEquals(expected, actual)
    }

    @Test
    fun toLongShouldPass() {
        val actual: Long = Zero()
            .toLong()
        val expected = 0L
        assertEquals(expected, actual)
    }

    @Test
    fun toFloatShouldPass() {
        val actual: Float = Zero()
            .toFloat()
        val expected = 0f
        assertEquals(expected, actual)
    }

    @Test
    fun toDoubleShouldPass() {
        val actual: Double = Zero()
            .toDouble()
        val expected = 0.0
        assertEquals(expected, actual)
    }

    @Test
    fun toCharShouldPass() {
        val actual: Char = Zero()
            .toChar()
        val expected = '0'
        assertEquals(expected, actual)
    }

    @Test
    fun toStringShouldPass() {
        val actual: String = Zero()
            .toString()
        val expected = "0"
        assertEquals(expected, actual)
    }
}

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroCompanionTest {
    @Test
    fun orNullShouldPassWithByteThatEqualsZero() {
        val number: Byte = 0
        val actual: Zero? = Zero.orNull(number)
        assertNotNull(actual)
    }

    @Test
    fun orNullShouldFailWithByteOtherThanZero() {
        val number: Byte = Byte.randomNonZero()
        val actual: Zero? = Zero.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullShouldPassWithShortThatEqualsZero() {
        val number: Short = 0
        val actual: Zero? = Zero.orNull(number)
        assertNotNull(actual)
    }

    @Test
    fun orNullShouldFailWithShortOtherThanZero() {
        val number: Short = setOf(Short.MIN_VALUE..-1, 1..Short.MAX_VALUE)
            .random()
            .random()
            .toShort()
        val actual: Zero? = Zero.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullShouldPassWithIntThatEqualsZero() {
        val number = 0
        val actual: Zero? = Zero.orNull(number)
        assertNotNull(actual)
    }

    @Test
    fun orNullShouldFailWithIntOtherThanZero() {
        val number: Int = setOf(Short.MIN_VALUE..-1, 1..Short.MAX_VALUE)
            .random()
            .random()
        val actual: Zero? = Zero.orNull(number)
        assertNull(actual)
    }


    @Test
    fun orNullShouldPassWithLongThatEqualsZero() {
        val number: Long = 0
        val actual: Zero? = Zero.orNull(number)
        assertNotNull(actual)
    }

    @Test
    fun orNullShouldFailWithLongOtherThanZero() {
        val number: Long = setOf(Long.MIN_VALUE..-1, 1..Long.MAX_VALUE)
            .random()
            .random()
        val actual: Zero? = Zero.orNull(number)
        assertNull(actual)
    }


    @Test
    fun orNullShouldPassWithFloatThatEqualsZero() {
        val number = 0f
        val actual: Zero? = Zero.orNull(number)
        assertNotNull(actual)
    }

    @Test
    fun orNullShouldFailWithFloatOtherThanZero() {
        val integer: Byte = Byte.randomNonZero()
        val decimal: Float = Random.nextFloat()
        val number: Float = integer + decimal
        val actual: Zero? = Zero.orNull(number)
        assertNull(actual)
    }


    @Test
    fun orNullShouldPassWithDoubleThatEqualsZero() {
        val number = 0.0
        val actual: Zero? = Zero.orNull(number)
        assertNotNull(actual)
    }

    @Test
    fun orNullShouldFailWithDoubleOtherThanZero() {
        val integer: Byte = Byte.randomNonZero()
        val decimal: Double = Random.nextDouble()
        val number: Double = integer + decimal
        val actual: Zero? = Zero.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullShouldPassWithStringRepresentingZero(): Unit =
        sequenceOf("0", "000", "0.0", "0.000", "000.0", "000.000")
            .map(Zero.Companion::orNull)
            .forEach(::assertNotNull)

    @Test
    fun orNullShouldFailWithStringNotRepresentingZero(): Unit =
        sequenceOf("", " ", ".", "0.", ".0", "abc")
            .map(Zero.Companion::orNull)
            .forEach(::assertNull)

    @Test
    fun orThrowShouldPassWithByteThatEqualsZero() {
        val number: Byte = 0
        Zero.orThrow(number)
    }

    @Test
    fun orThrowShouldFailWithByteOtherThanZero() {
        val number: Byte = Byte.randomNonZero()
        assertThrowsIllegalArgumentException { Zero.orThrow(number) }
            .assertEquals { ExceptionMessage.nonZero(number) }
    }

    @Test
    fun orThrowShouldPassWithShortThatEqualsZero() {
        val number: Short = 0
        Zero.orThrow(number)
    }

    @Test
    fun orThrowShouldFailWithShortOtherThanZero() {
        val number: Short = setOf(Short.MIN_VALUE..-1, 1..Short.MAX_VALUE)
            .random()
            .random()
            .toShort()
        assertThrowsIllegalArgumentException { Zero.orThrow(number) }
            .assertEquals { ExceptionMessage.nonZero(number) }
    }

    @Test
    fun orThrowShouldPassWithIntThatEqualsZero() {
        val number = 0
        Zero.orThrow(number)
    }

    @Test
    fun orThrowShouldFailWithIntOtherThanZero() {
        val number: Int = setOf(Int.MIN_VALUE..-1, 1..Int.MAX_VALUE)
            .random()
            .random()
        assertThrowsIllegalArgumentException { Zero.orThrow(number) }
            .assertEquals { ExceptionMessage.nonZero(number) }
    }

    @Test
    fun orThrowShouldPassWithLongThatEqualsZero() {
        val number = 0L
        Zero.orThrow(number)
    }

    @Test
    fun orThrowShouldFailWithLongOtherThanZero() {
        val number: Long = setOf(Long.MIN_VALUE..-1, 1..Long.MAX_VALUE)
            .random()
            .random()
        assertThrowsIllegalArgumentException { Zero.orThrow(number) }
            .assertEquals { ExceptionMessage.nonZero(number) }
    }

    @Test
    fun orThrowShouldPassWithFloatThatEqualsZero() {
        val number = 0f
        Zero.orThrow(number)
    }

    @Test
    fun orThrowShouldFailWithFloatOtherThanZero() {
        val integer: Byte = Byte.randomNonZero()
        val decimal: Float = Random.nextFloat()
        val number: Float = integer + decimal
        assertThrowsIllegalArgumentException { Zero.orThrow(number) }
            .assertEquals { ExceptionMessage.nonZero(number) }
    }

    @Test
    fun orThrowShouldPassWithDoubleThatEqualsZero() {
        val number = 0.0
        Zero.orThrow(number)
    }

    @Test
    fun orThrowShouldFailWithDoubleOtherThanZero() {
        val integer: Byte = Byte.randomNonZero()
        val decimal: Double = Random.nextDouble()
        val number: Double = integer + decimal
        assertThrowsIllegalArgumentException { Zero.orThrow(number) }
            .assertEquals { ExceptionMessage.nonZero(number) }
    }

    @Test
    fun orThrowShouldPassWithStringRepresentingZero(): Unit =
        listOf("0", "000", "0.0", "0.000", "000.0", "000.000")
            .forEach(Zero.Companion::orThrow)

    @Test
    fun orThrowShouldFailWithStringNotRepresentingZero(): Unit =
        listOf("", " ", ".", "0.", ".0", "abc")
            .forEach {
                assertThrowsIllegalArgumentException { Zero.orThrow(it) }
                    .assertEquals { ExceptionMessage.nonZero(it) }
            }
}

// ----------------------------- Number extensions -----------------------------

private fun Byte.Companion.randomNonZero(): Byte =
    listOf(MIN_VALUE..-1, 1..MAX_VALUE)
        .random()
        .random()
        .toByte()
