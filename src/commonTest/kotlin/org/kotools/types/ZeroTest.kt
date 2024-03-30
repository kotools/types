package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

class ZeroTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun toString_should_return_the_string_representation_of_zero() {
        val actual: String = Zero.toString()
        val expected = "0"
        val message = "String representation of Zero should be '0'."
        assertEquals(expected, actual, message)
    }
}
