package org.kotools.types

import org.kotools.types.internal.InvalidZero
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroCompanionTest {
    @Test
    fun orNullShouldFailWithByteOtherThanZero() {
        val number: Byte = setOf(Byte.MIN_VALUE..-1, 1..Byte.MAX_VALUE)
            .random()
            .random()
            .toByte()
        val actual: Zero? = Zero.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orThrowShouldFailWithByteOtherThanZero() {
        val number: Byte = setOf(Byte.MIN_VALUE..-1, 1..Byte.MAX_VALUE)
            .random()
            .random()
            .toByte()
        val exception: IllegalArgumentException = assertFailsWith {
            Zero.orThrow(number)
        }
        val actual: String? = exception.message
        val expected: String = InvalidZero(number)
            .toString()
        assertEquals(expected, actual)
    }

    @Test
    fun orThrowShouldFailWithShortOtherThanZero() {
        val number: Short = setOf(Short.MIN_VALUE..-1, 1..Short.MAX_VALUE)
            .random()
            .random()
            .toShort()
        val exception: IllegalArgumentException = assertFailsWith {
            Zero.orThrow(number)
        }
        val actual: String? = exception.message
        val expected: String = InvalidZero(number)
            .toString()
        assertEquals(expected, actual)
    }
}
