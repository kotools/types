package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerCommonSample {
    @Test
    fun equalsOverride() {
        val integer: PositiveInteger = PositiveInteger.of("123456789") ?: fail()
        val other: PositiveInteger = integer.toString()
            .let(PositiveInteger.Companion::of)
            ?: fail()
        val result: Boolean = integer.equals(other)
        assertTrue(result)
    }

    @Test
    fun toStringOverride() {
        val text = "123456789"
        val integer: PositiveInteger = PositiveInteger.of(text) ?: fail()
        val result: String = integer.toString() // or "$integer"
        assertEquals(expected = text, result)
    }

    // ------------------------------- Companion -------------------------------

    @Test
    fun of() {
        assertNotNull(PositiveInteger of "+123456789")
    }
}
