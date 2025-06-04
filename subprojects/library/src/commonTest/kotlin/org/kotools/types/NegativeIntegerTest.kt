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
    fun orNullPassWithIntThatIsLessThanZero() {
        val number: Int = (Int.MIN_VALUE..-1).random()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNotNull(actual)
    }

    @Test
    fun orNullFailWithIntThatEqualsZero() {
        val number: Int = Zero()
            .toInt()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullFailWithIntThatIsGreaterThanZero() {
        val number: Int = (1..Int.MAX_VALUE).random()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNull(actual)
    }

    // ------------------------ Companion.orNull(Long) -------------------------

    @Test
    fun orNullPassWithLongThatIsLessThanZero() {
        val number: Long = (Long.MIN_VALUE..-1).random()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNotNull(actual)
    }

    @Test
    fun orNullFailWithLongThatEqualsZero() {
        val number: Long = Zero()
            .toLong()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullFailWithLongThatIsGreaterThanZero() {
        val number: Long = (1..Long.MAX_VALUE).random()
        val actual: NegativeInteger? = NegativeInteger.orNull(number)
        assertNull(actual)
    }

    // ----------------------- Companion.orNull(String) ------------------------

    @Test
    fun orNullPassWithStringIntegerLessThanZero() {
        val text: String = (Long.MIN_VALUE..-1).random()
            .toString()
        val actual: NegativeInteger? = NegativeInteger.orNull(text)
        assertNotNull(actual)
    }

    @Test
    fun orNullFailWithStringRepresentingZero() {
        val text: String = Zero()
            .toString()
        val actual: NegativeInteger? = NegativeInteger.orNull(text)
        assertNull(actual)
    }

    @Test
    fun orNullFailWithStringIntegerGreaterThanZero() {
        val text: String = (1..Long.MAX_VALUE).random()
            .toString()
        val actual: NegativeInteger? = NegativeInteger.orNull(text)
        assertNull(actual)
    }

    // ------------------------ Companion.orThrow(Int) -------------------------

    @Test
    fun orThrowPassWithIntThatIsLessThanZero() {
        val number: Int = (Int.MIN_VALUE..-1).random()
        NegativeInteger.orThrow(number)
    }

    @Test
    fun orThrowFailWithIntThatEqualsZero() {
        val number: Int = Zero()
            .toInt()
        val expected: ExceptionMessage = ExceptionMessage.nonNegative(number)
        val actual: ExceptionMessage = assertThrowsIllegalArgumentException {
            NegativeInteger.orThrow(number)
        }
        assertEquals(expected, actual)
    }

    @Test
    fun orThrowFailWithIntThatIsGreaterThanZero() {
        val number: Int = (1..Int.MAX_VALUE).random()
        val expected: ExceptionMessage = ExceptionMessage.nonNegative(number)
        val actual: ExceptionMessage = assertThrowsIllegalArgumentException {
            NegativeInteger.orThrow(number)
        }
        assertEquals(expected, actual)
    }

    // ------------------------ Companion.orThrow(Long) ------------------------

    @Test
    fun orThrowPassWithLongThatIsLessThanZero() {
        val number: Long = (Long.MIN_VALUE..-1).random()
        NegativeInteger.orThrow(number)
    }

    @Test
    fun orThrowFailWithLongThatEqualsZero() {
        val number: Long = Zero()
            .toLong()
        val expected: ExceptionMessage = ExceptionMessage.nonNegative(number)
        val actual: ExceptionMessage = assertThrowsIllegalArgumentException {
            NegativeInteger.orThrow(number)
        }
        assertEquals(expected, actual)
    }

    @Test
    fun orThrowFailWithLongThatIsGreaterThanZero() {
        val number: Long = (1..Long.MAX_VALUE).random()
        val expected: ExceptionMessage = ExceptionMessage.nonNegative(number)
        val actual: ExceptionMessage = assertThrowsIllegalArgumentException {
            NegativeInteger.orThrow(number)
        }
        assertEquals(expected, actual)
    }

    // ----------------------- Companion.orThrow(String) -----------------------

    @Test
    fun orThrowPassWithStringIntegerLessThanZero() {
        val text: String = (Long.MIN_VALUE..-1).random()
            .toString()
        NegativeInteger.orThrow(text)
    }

    @Test
    fun orThrowFailWithStringRepresentingZero() {
        val text: String = Zero()
            .toString()
        val expected: ExceptionMessage =
            ExceptionMessage.nonNegativeInteger(text)
        val actual: ExceptionMessage = assertThrowsIllegalArgumentException {
            NegativeInteger.orThrow(text)
        }
        assertEquals(expected, actual)
    }

    @Test
    fun orThrowFailWithStringIntegerGreaterThanZero() {
        val text: String = (1..Long.MAX_VALUE).random()
            .toString()
        val expected: ExceptionMessage =
            ExceptionMessage.nonNegativeInteger(text)
        val actual: ExceptionMessage = assertThrowsIllegalArgumentException {
            NegativeInteger.orThrow(text)
        }
        assertEquals(expected, actual)
    }
}
