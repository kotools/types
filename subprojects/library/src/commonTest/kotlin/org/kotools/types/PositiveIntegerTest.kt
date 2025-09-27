package org.kotools.types

import org.kotools.types.internal.ExceptionMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerTest {
    // ------------------------ Companion.orNull(Byte) -------------------------

    @Test
    fun orNullPassesWithByteGreaterThanZero() {
        val number: Byte = (1..Byte.MAX_VALUE).random()
            .toByte()
        val actual: PositiveInteger? = PositiveInteger.orNull(number)
        assertNotNull(actual)
    }

    @Test
    fun orNullFailsWithByteThatEqualsZero() {
        val number: Byte = 0
        val actual: PositiveInteger? = PositiveInteger.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullFailsWithByteLessThanZero() {
        val number: Byte = (Byte.MIN_VALUE..-1).random()
            .toByte()
        val actual: PositiveInteger? = PositiveInteger.orNull(number)
        assertNull(actual)
    }

    // ------------------------ Companion.orNull(Short) ------------------------

    @Test
    fun orNullPassesWithShortGreaterThanZero() {
        val number: Short = (1..Short.MAX_VALUE).random()
            .toShort()
        val actual: PositiveInteger? = PositiveInteger.orNull(number)
        assertNotNull(actual)
    }

    @Test
    fun orNullFailsWithShortThatEqualsZero() {
        val number: Short = 0
        val actual: PositiveInteger? = PositiveInteger.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullFailsWithShortLessThanZero() {
        val number: Short = (Short.MIN_VALUE..-1).random()
            .toShort()
        val actual: PositiveInteger? = PositiveInteger.orNull(number)
        assertNull(actual)
    }

    // ----------------------- Companion.orNull(String) ------------------------

    @Test
    fun orNullPassesWithStringIntegerGreaterThanZero() {
        val text: String = (1..Long.MAX_VALUE).random()
            .toString()
        val actual: PositiveInteger? = PositiveInteger.orNull(text)
        assertNotNull(actual)
    }

    @Test
    fun orNullPassesWithStringSignedIntegerGreaterThanZero() {
        val text: String = (1..Long.MAX_VALUE).random()
            .toString()
            .let { "+$it" }
        val actual: PositiveInteger? = PositiveInteger.orNull(text)
        assertNotNull(actual)
    }

    @Test
    fun orNullFailsWithStringIntegerRepresentingZero() {
        val text = "0"
        val actual: PositiveInteger? = PositiveInteger.orNull(text)
        assertNull(actual)
    }

    @Test
    fun orNullFailsWithStringIntegerLessThanZero() {
        val text: String = (Long.MIN_VALUE..-1).random()
            .toString()
        val actual: PositiveInteger? = PositiveInteger.orNull(text)
        assertNull(actual)
    }

    // ------------------------ Companion.orThrow(Byte) ------------------------

    @Test
    fun orThrowPassesWithByteGreaterThanZero() {
        val number: Byte = (1..Byte.MAX_VALUE).random()
            .toByte()
        PositiveInteger.orThrow(number)
    }

    @Test
    fun orThrowFailsWithByteThatEqualsZero() {
        val number: Byte = 0
        val throwable: IllegalArgumentException = assertFailsWith {
            PositiveInteger.orThrow(number)
        }
        val actual: ExceptionMessage = ExceptionMessage.from(throwable)
        val expected: ExceptionMessage = ExceptionMessage.nonPositive(number)
        assertEquals(expected, actual)
    }

    @Test
    fun orThrowFailsWithByteLessThanZero() {
        val number: Byte = (Byte.MIN_VALUE..-1).random()
            .toByte()
        val throwable: IllegalArgumentException = assertFailsWith {
            PositiveInteger.orThrow(number)
        }
        val actual: ExceptionMessage = ExceptionMessage.from(throwable)
        val expected: ExceptionMessage = ExceptionMessage.nonPositive(number)
        assertEquals(expected, actual)
    }

    // ----------------------- Companion.orThrow(String) -----------------------

    @Test
    fun orThrowPassesWithStringIntegerGreaterThanZero() {
        val text: String = (1..Long.MAX_VALUE).random()
            .toString()
        PositiveInteger.orThrow(text)
    }

    @Test
    fun orThrowPassesWithStringSignedIntegerGreaterThanZero() {
        val text: String = (1..Long.MAX_VALUE).random()
            .toString()
            .let { "+$it" }
        PositiveInteger.orThrow(text)
    }

    @Test
    fun orThrowFailsWithStringIntegerRepresentingZero() {
        val text = "0"
        val throwable: IllegalArgumentException = assertFailsWith {
            PositiveInteger.orThrow(text)
        }
        val actual: ExceptionMessage = ExceptionMessage.from(throwable)
        val expected: ExceptionMessage =
            ExceptionMessage.nonPositiveInteger(text)
        assertEquals(expected, actual)
    }

    @Test
    fun orThrowFailsWithStringIntegerLessThanZero() {
        val text: String = (Long.MIN_VALUE..-1).random()
            .toString()
        val throwable: IllegalArgumentException = assertFailsWith {
            PositiveInteger.orThrow(text)
        }
        val actual: ExceptionMessage = ExceptionMessage.from(throwable)
        val expected: ExceptionMessage =
            ExceptionMessage.nonPositiveInteger(text)
        assertEquals(expected, actual)
    }
}
