package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerSample {
    @Test
    fun toStringOverride() {
        val text = "123456789"
        val integer: PositiveInteger? = PositiveInteger of text
        assertEquals(expected = text, "$integer")
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
