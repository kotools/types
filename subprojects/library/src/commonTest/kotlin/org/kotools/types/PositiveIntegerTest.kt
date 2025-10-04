package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerTest {
    // ------------------------- Companion.of(String) --------------------------

    @Test
    fun ofPassesWithStringIntegerGreaterThanZero() {
        assertNotNull(PositiveInteger of "123456789")
    }

    @Test
    fun ofPassesWithStringSignedIntegerGreaterThanZero() {
        assertNotNull(PositiveInteger of "+123456789")
    }

    @Test
    fun ofFailsWithStringOtherThanInteger(): Unit =
        assertNull(PositiveInteger of "oops")

    @Test
    fun ofFailsWithStringIntegerRepresentingZero(): Unit =
        assertNull(PositiveInteger of "0")

    @Test
    fun ofFailsWithStringIntegerLessThanZero(): Unit =
        assertNull(PositiveInteger of "-123456789")
}
