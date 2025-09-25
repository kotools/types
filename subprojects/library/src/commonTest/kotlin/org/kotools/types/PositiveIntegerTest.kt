package org.kotools.types

import org.kotools.types.internal.ExceptionMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerTest {
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
