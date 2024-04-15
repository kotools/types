package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ZeroTest {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun isEqualTo_should_pass_with_a_Byte_that_equals_zero() {
        val number: Byte = 0
        val actual: Boolean = Zero isEqualTo number
        assertTrue(actual, "$number should be considered as zero.")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun isEqualTo_should_fail_with_a_Byte_other_than_zero() {
        val number: Byte = listOf(Byte.MIN_VALUE..-1, 1..Byte.MAX_VALUE)
            .random()
            .random()
            .toByte()
        val actual: Boolean = Zero isEqualTo number
        assertFalse(actual, "$number shouldn't be considered as zero.")
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun toByte_should_pass() {
        val actual: Byte = Zero.toByte()
        val expected: Byte = 0.toByte()
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun toString_should_pass() {
        val actual: String = Zero.toString()
        val expected = "0"
        assertEquals(expected, actual)
    }
}
