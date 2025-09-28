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

    // ------------------------------- Companion -------------------------------

    @Test
    fun orNullString() {
        val text: String = Byte.MAX_VALUE.toString()
        val integer: PositiveInteger? = PositiveInteger.orNull(text)
        assertNotNull(integer)
    }

    @Test
    fun orNullStringSigned() {
        val text: String = '+' + Short.MAX_VALUE.toString()
        val integer: PositiveInteger? = PositiveInteger.orNull(text)
        assertNotNull(integer)
    }

    @Test
    fun orThrowString() {
        val text: String = Int.MAX_VALUE.toString()
        PositiveInteger.orThrow(text)
    }

    @Test
    fun orThrowStringSigned() {
        val text: String = '+' + Int.MAX_VALUE.toString()
        PositiveInteger.orThrow(text)
    }
}
