package org.kotools.types.numbers

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerTest {
    @Test
    fun constructorWithMaximumLong() {
        val number: Long = Long.MAX_VALUE
        val integer = Integer(number)
        assertEquals(expected = "$number", actual = "$integer")
    }

    @Test
    fun constructorWithMinimumLong() {
        val number: Long = Long.MIN_VALUE
        val integer = Integer(number)
        assertEquals(expected = "$number", actual = "$integer")
    }
}
