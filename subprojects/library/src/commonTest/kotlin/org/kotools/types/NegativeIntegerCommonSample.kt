package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertNotNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class NegativeIntegerCommonSample {
    // ------------------------------- Companion -------------------------------

    @Test
    fun orNullInt() {
        val number: Int = (Int.MIN_VALUE..-1).random()
        val integer: NegativeInteger? = NegativeInteger.orNull(number)
        assertNotNull(integer)
    }

    @Test
    fun orNullLong() {
        val number: Long = (Long.MIN_VALUE..-1).random()
        val integer: NegativeInteger? = NegativeInteger.orNull(number)
        assertNotNull(integer)
    }

    @Test
    fun orNullString() {
        val text: String = (Long.MIN_VALUE..-1).random()
            .toString()
        val integer: NegativeInteger? = NegativeInteger.orNull(text)
        assertNotNull(integer)
    }

    @Test
    fun orThrowInt() {
        val number: Int = (Int.MIN_VALUE..-1).random()
        NegativeInteger.orThrow(number)
    }

    @Test
    fun orThrowLong() {
        val number: Long = (Long.MIN_VALUE..-1).random()
        NegativeInteger.orThrow(number)
    }

    @Test
    fun orThrowString() {
        val text: String = (Long.MIN_VALUE..-1).random()
            .toString()
        NegativeInteger.orThrow(text)
    }
}
