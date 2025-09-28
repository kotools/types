package org.kotools.types

import org.kotools.types.internal.ExceptionMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerTest {
    // ---------------------- String.toPositiveInteger() -----------------------

    @Test
    fun toPositiveIntegerPassesOnStringIntegerGreaterThanZero() {
        (1..Int.MAX_VALUE).random()
            .toString()
            .toPositiveInteger()
    }

    @Test
    fun toPositiveIntegerPassesOnStringSignedIntegerGreaterThanZero() {
        (1..Int.MAX_VALUE).random()
            .toString()
            .let { "+$it" }
            .toPositiveInteger()
    }

    @Test
    fun toPositiveIntegerFailsOnStringOtherThanInteger(): Unit =
        this.toPositiveIntegerFailsOn("oops")

    @Test
    fun toPositiveIntegerFailsOnStringIntegerRepresentingZero(): Unit =
        this.toPositiveIntegerFailsOn("0")

    @Test
    fun toPositiveIntegerFailsOnStringIntegerLessThanZero(): Unit =
        (Int.MIN_VALUE..-1).random()
            .toString()
            .let(this::toPositiveIntegerFailsOn)

    private fun toPositiveIntegerFailsOn(text: String) {
        val throwable: IllegalArgumentException =
            assertFailsWith(block = text::toPositiveInteger)
        val actual: ExceptionMessage = ExceptionMessage.from(throwable)
        val expected: ExceptionMessage =
            ExceptionMessage.nonPositiveInteger(text)
        assertEquals(expected, actual)
    }

    // ------------------- String.toPositiveIntegerOrNull() --------------------

    @Test
    fun toPositiveIntegerOrNullPassesOnStringIntegerGreaterThanZero() {
        val actual: PositiveInteger? = (1..Int.MAX_VALUE).random()
            .toString()
            .toPositiveIntegerOrNull()
        assertNotNull(actual)
    }

    @Test
    fun toPositiveIntegerOrNullPassesOnStringSignedIntegerGreaterThanZero() {
        val actual: PositiveInteger? = (1..Int.MAX_VALUE).random()
            .toString()
            .let { "+$it" }
            .toPositiveIntegerOrNull()
        assertNotNull(actual)
    }

    @Test
    fun toPositiveIntegerOrNullFailsOnStringOtherThanInteger(): Unit =
        this.toPositiveIntegerOrNullFailsOn("oops")

    @Test
    fun toPositiveIntegerOrNullFailsOnStringIntegerRepresentingZero(): Unit =
        this.toPositiveIntegerOrNullFailsOn("0")

    @Test
    fun toPositiveIntegerOrNullFailsOnStringIntegerLessThanZero(): Unit =
        (Int.MIN_VALUE..-1).random()
            .toString()
            .let(this::toPositiveIntegerOrNullFailsOn)

    private fun toPositiveIntegerOrNullFailsOn(text: String) {
        val actual: PositiveInteger? = text.toPositiveIntegerOrNull()
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
