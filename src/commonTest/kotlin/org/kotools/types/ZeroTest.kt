package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

class ZeroTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun toString_should_pass() {
        val actual: String = Zero.toString()
        val expected = "0"
        assertEquals(expected, actual)
    }
}
