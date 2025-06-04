package org.kotools.types

import org.kotools.types.internal.ExceptionMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class NegativeIntegerTest {
    // ------------------------------ toString() -------------------------------

    @Test
    fun toStringPass() {
        val number: Int = (Int.MIN_VALUE..-1).random()
        val expected: String = number.toString()
        val actual: String = NegativeInteger.orThrow(number)
            .toString()
        assertEquals(expected, actual)
    }

    // ------------------------- Companion.orNull(Int) -------------------------

    @Test
    fun orNullShouldPassWithIntThatIsLessThanZero() {
        val number: Int = (Int.MIN_VALUE..-1).random()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNotNull(actual)
    }

    @Test
    fun orNullShouldFailWithIntThatEqualsZero() {
        val number: Int = Zero()
            .toInt()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullShouldFailWithIntThatIsGreaterThanZero() {
        val number: Int = (1..Int.MAX_VALUE).random()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNull(actual)
    }

    // ------------------------ Companion.orNull(Long) -------------------------

    @Test
    fun orNullShouldPassWithLongThatIsLessThanZero() {
        val number: Long = (Long.MIN_VALUE..-1).random()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNotNull(actual)
    }

    @Test
    fun orNullShouldFailWithLongThatEqualsZero() {
        val number: Long = Zero()
            .toLong()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullShouldFailWithLongThatIsGreaterThanZero() {
        val number: Long = (1..Long.MAX_VALUE).random()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNull(actual)
    }

    // ----------------------- Companion.orNull(String) ------------------------

    @Test
    fun orNullShouldPassWithStringIntegerLessThanZero() {
        val text: String = (Long.MIN_VALUE..-1).random()
            .toString()
        val actual: NegativeInteger? = NegativeInteger.orNull(text)
        assertNotNull(actual)
    }

    @Test
    fun orNullShouldFailWithStringRepresentingZero() {
        val text: String = Zero()
            .toString()
        val actual: NegativeInteger? = NegativeInteger.orNull(text)
        assertNull(actual)
    }

    @Test
    fun orNullShouldFailWithStringIntegerGreaterThanZero() {
        val text: String = (1..Long.MAX_VALUE).random()
            .toString()
        val actual: NegativeInteger? = NegativeInteger.orNull(text)
        assertNull(actual)
    }

    // ------------------------ Companion.orThrow(Int) -------------------------

    @Test
    fun orThrowShouldPassWithIntThatIsLessThanZero() {
        val number: Int = (Int.MIN_VALUE..-1).random()
        NegativeInteger.orThrow(number)
    }

    @Test
    fun orThrowShouldFailWithIntThatEqualsZero() {
        val number: Int = Zero()
            .toInt()
        val actual: ExceptionMessage = assertThrowsIllegalArgumentException {
            NegativeInteger.orThrow(number)
        }
        val expected: ExceptionMessage = ExceptionMessage.nonNegative(number)
        assertEquals(expected, actual)
    }

    @Test
    fun orThrowShouldFailWithIntThatIsGreaterThanZero() {
        val number: Int = (1..Int.MAX_VALUE).random()
        val actual: ExceptionMessage = assertThrowsIllegalArgumentException {
            NegativeInteger.orThrow(number)
        }
        val expected: ExceptionMessage = ExceptionMessage.nonNegative(number)
        assertEquals(expected, actual)
    }

    // ------------------------ Companion.orThrow(Long) ------------------------

    @Test
    fun orThrowShouldPassWithLongThatIsLessThanZero() {
        val number: Long = (Long.MIN_VALUE..-1).random()
        NegativeInteger.orThrow(number)
    }

    @Test
    fun orThrowShouldFailWithLongThatEqualsZero() {
        val number: Long = Zero()
            .toLong()
        val actual: ExceptionMessage = assertThrowsIllegalArgumentException {
            NegativeInteger.orThrow(number)
        }
        val expected: ExceptionMessage = ExceptionMessage.nonNegative(number)
        assertEquals(expected, actual)
    }

    @Test
    fun orThrowShouldFailWithLongThatIsGreaterThanZero() {
        val number: Long = (1..Long.MAX_VALUE).random()
        val actual: ExceptionMessage = assertThrowsIllegalArgumentException {
            NegativeInteger.orThrow(number)
        }
        val expected: ExceptionMessage = ExceptionMessage.nonNegative(number)
        assertEquals(expected, actual)
    }

    // ----------------------- Companion.orThrow(String) -----------------------

    @Test
    fun orThrowShouldPassWithStringIntegerLessThanZero() {
        val text: String = (Long.MIN_VALUE..-1).random()
            .toString()
        NegativeInteger.orThrow(text)
    }

    @Test
    fun orThrowShouldFailWithStringRepresentingZero() {
        val text: String = Zero()
            .toString()
        val actual: ExceptionMessage = assertThrowsIllegalArgumentException {
            NegativeInteger.orThrow(text)
        }
        val expected: ExceptionMessage =
            ExceptionMessage.nonNegativeInteger(text)
        assertEquals(expected, actual)
    }

    @Test
    fun orThrowShouldFailWithStringIntegerGreaterThanZero() {
        val text: String = (1..Long.MAX_VALUE).random()
            .toString()
        val actual: ExceptionMessage = assertThrowsIllegalArgumentException {
            NegativeInteger.orThrow(text)
        }
        val expected: ExceptionMessage =
            ExceptionMessage.nonNegativeInteger(text)
        assertEquals(expected, actual)
    }
}
