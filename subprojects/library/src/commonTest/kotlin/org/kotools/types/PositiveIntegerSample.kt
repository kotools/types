package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertNotNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerSample {
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
