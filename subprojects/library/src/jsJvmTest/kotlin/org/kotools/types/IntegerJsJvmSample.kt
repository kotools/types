package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerJsJvmSample {
    @Test
    fun constructorLong() {
        val integer = Integer(Long.MAX_VALUE)
        assertEquals(expected = "9223372036854775807", "$integer")
    }
}
