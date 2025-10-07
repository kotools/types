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
    fun hashCodeOverride() {
        val integer: PositiveInteger = PositiveInteger.of("123456789") ?: fail()
        val hashCode: Int = integer.hashCode()
        val other: Int = integer.toString()
            .let(PositiveInteger.Companion::of)
            ?.hashCode()
            ?: fail()
        assertEquals(hashCode, other)
    }

    @Test
    fun plus() {
        val integer: PositiveInteger = PositiveInteger.of("123456789") ?: fail()
        val other: PositiveInteger = Long.MAX_VALUE.toString()
            .let(PositiveInteger.Companion::of)
            ?: fail()
        val result: PositiveInteger = integer + other
        val expected: PositiveInteger =
            PositiveInteger.of("9223372036978232596") ?: fail()
        assertEquals(expected, result)
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

    @Test
    fun minimum() {
        val result: PositiveInteger = PositiveInteger.minimum()
        val expected: PositiveInteger = PositiveInteger.of("1") ?: fail()
        assertEquals(expected, result)
    }
}
