package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerJsJvmSample {
    @Test
    fun longToPositiveInteger() {
        val number = 123456L
        val result: String = number.toPositiveInteger()
            .toString()
        assertEquals(expected = "$number", result)
    }
}
