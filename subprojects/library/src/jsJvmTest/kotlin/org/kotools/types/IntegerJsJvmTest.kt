package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerJsJvmTest {
    @Test
    fun constructorPassesWithMaximumLong() {
        val number: Long = Long.MAX_VALUE
        val integer = Integer(number)
        assertEquals(expected = "$number", actual = "$integer")
    }

    @Test
    fun constructorPassesWithMinimumLong() {
        val number: Long = Long.MIN_VALUE
        val integer = Integer(number)
        assertEquals(expected = "$number", actual = "$integer")
    }
}
