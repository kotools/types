package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerTest {
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
