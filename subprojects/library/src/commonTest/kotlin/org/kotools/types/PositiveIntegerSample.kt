package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertNotNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerSample {
    // ------------------------------- Companion -------------------------------

    @Test
    fun orNullByte() {
        val integer: PositiveInteger? = PositiveInteger.orNull(Byte.MAX_VALUE)
        assertNotNull(integer)
    }

    @Test
    fun orNullShort() {
        val integer: PositiveInteger? = PositiveInteger.orNull(Short.MAX_VALUE)
        assertNotNull(integer)
    }

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
    fun orThrowByte() {
        PositiveInteger.orThrow(Byte.MAX_VALUE)
    }

    @Test
    fun orThrowShort() {
        PositiveInteger.orThrow(Short.MAX_VALUE)
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
