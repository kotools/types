package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerCommonSample {
    @Test
    fun equalsOverride() {
        val number: Int = (1..Int.MAX_VALUE).random()
        val integer: PositiveInteger = PositiveInteger.orThrow(number)
        val other: PositiveInteger = PositiveInteger.orThrow(number)
        val equality: Boolean = integer == other // or integer.equals(other)
        val message = "Positive integers with the same value are equal."
        assertTrue(equality, message)
    }

    @Test
    fun toStringOverride() {
        val number: Int = (1..Int.MAX_VALUE).random()
        val integerAsString: String = PositiveInteger.orThrow(number)
            .toString()
        assertEquals(expected = "$number", integerAsString)
    }
}
