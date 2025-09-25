package org.kotools.types

import kotlin.test.Test
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
}
