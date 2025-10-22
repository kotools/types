package org.kotools.types.numbers

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerSample {
    @Test
    fun constructorLong() {
        val integer = Integer(Long.MAX_VALUE)
        assertEquals(expected = "9223372036854775807", "$integer")
    }

    @Test
    fun constructorString() {
        val integer = Integer("${Long.MAX_VALUE}")
        assertEquals(expected = "9223372036854775807", "$integer")

        val plusSignedInteger = Integer("+${Long.MAX_VALUE}")
        assertEquals(expected = "9223372036854775807", "$plusSignedInteger")

        val minusSignedInteger = Integer("${Long.MIN_VALUE}")
        assertEquals(expected = "-9223372036854775808", "$minusSignedInteger")
    }
}
