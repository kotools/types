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
}
