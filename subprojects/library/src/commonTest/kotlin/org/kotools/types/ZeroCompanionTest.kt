package org.kotools.types

import org.kotools.types.internal.InvalidZero
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroCompanionTest {
    @Test
    fun orNullShouldPassWithByteThatEqualsZero() {
        val number: Byte = 0
        val actual: Zero? = Zero.orNull(number)
        assertNotNull(actual)
    }

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
    fun orThrowShouldPassWithByteThatEqualsZero() {
        val number: Byte = 0
        Zero.orThrow(number)
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
}
