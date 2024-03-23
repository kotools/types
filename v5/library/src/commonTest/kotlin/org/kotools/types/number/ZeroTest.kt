package org.kotools.types.number

import org.kotools.types.ExperimentalApi
import kotlin.test.Test
import kotlin.test.assertEquals

class ZeroTest {
    @OptIn(ExperimentalApi::class)
    @Test
    fun toString_should_pass() {
        val actual: String = Zero.toString()
        val expected = "0"
        val message = "'Zero.toString()' should return '$expected'."
        assertEquals(expected, actual, message)
    }
}
