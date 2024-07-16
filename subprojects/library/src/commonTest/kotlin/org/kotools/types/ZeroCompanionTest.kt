package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.InvalidZero
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroCompanionTest {
    @Test
    fun fromByteShouldPassWithByteThatEqualsZero() {
        val number: Byte = 0
        Zero.fromByte(number)
    }

    @Test
    fun fromByteShouldFailWithByteOtherThanZero() {
        val number: Byte = setOf(Byte.MIN_VALUE..-1, 1..Byte.MAX_VALUE)
            .random()
            .random()
            .toByte()
        val exception: IllegalArgumentException = assertFailsWith {
            Zero.fromByte(number)
        }
        val actual: String? = exception.message
        val expected: String = InvalidZero(number)
            .toString()
        assertEquals(expected, actual)
    }
}
