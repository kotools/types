package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertNotNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerSample {
    // --------------------------- Factory functions ---------------------------

    @Test
    fun stringToPositiveInteger() {
        Int.MAX_VALUE.toString()
            .toPositiveInteger()
    }

    @Test
    fun stringToPositiveIntegerSigned() {
        Int.MAX_VALUE.toString()
            .let { "+$it" }
            .toPositiveInteger()
    }

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
}
