package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertNotNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerSample {
    // --------------------------- Factory functions ---------------------------

    @Test
    fun stringToPositiveIntegerOrNull() {
        val integer: PositiveInteger? = Int.MAX_VALUE.toString()
            .toPositiveIntegerOrNull()
        assertNotNull(integer)
    }

    @Test
    fun stringToPositiveIntegerOrNullSigned() {
        val integer: PositiveInteger? = Int.MAX_VALUE.toString()
            .let { "+$it" }
            .toPositiveIntegerOrNull()
        assertNotNull(integer)
    }

    // ------------------------------- Companion -------------------------------

    @Test
    fun ofString() {
        val integer: PositiveInteger? = PositiveInteger of "123456789"
        assertNotNull(integer)
    }

    @Test
    fun ofStringSigned() {
        val integer: PositiveInteger? = PositiveInteger of "+123456789"
        assertNotNull(integer)
    }
}
