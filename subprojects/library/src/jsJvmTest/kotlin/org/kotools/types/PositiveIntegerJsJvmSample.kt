package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerJsJvmSample {
    @Test
    fun intToPositiveInteger() {
        val result: PositiveInteger = Int.MAX_VALUE.toPositiveInteger()
        assertEquals(expected = "2147483647", "$result")
    }

    @Test
    fun longToPositiveInteger() {
        val result: PositiveInteger = Long.MAX_VALUE.toPositiveInteger()
        assertEquals(expected = "9223372036854775807", "$result")
    }
}
