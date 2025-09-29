package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerTest {
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

    // ------------------------- Companion.of(String) --------------------------

    @Test
    fun ofPassesWithStringIntegerGreaterThanZero() {
        val actual: PositiveInteger? = PositiveInteger of "123456789"
        assertNotNull(actual)
    }

    @Test
    fun ofPassesWithStringSignedIntegerGreaterThanZero() {
        val actual: PositiveInteger? = PositiveInteger of "+123456789"
        assertNotNull(actual)
    }

    @Test
    fun ofFailsWithStringOtherThanInteger() {
        val actual: PositiveInteger? = PositiveInteger of "oops"
        assertNull(actual)
    }

    @Test
    fun ofFailsWithStringIntegerRepresentingZero() {
        val actual: PositiveInteger? = PositiveInteger of "0"
        assertNull(actual)
    }

    @Test
    fun ofFailsWithStringIntegerLessThanZero() {
        val actual: PositiveInteger? = PositiveInteger of "-123456789"
        assertNull(actual)
    }
}
