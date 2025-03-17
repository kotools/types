package org.kotools.types

import org.kotools.types.internal.ExceptionMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerCompanionTest {
    @Test
    fun orThrowShouldFailWithByteThatEqualsZero() {
        val number: Byte = 0
        val exception: IllegalArgumentException = assertFailsWith {
            PositiveInteger.orThrow(number)
        }
        val actual: ExceptionMessage = ExceptionMessage.orThrow(exception)
        val expected: ExceptionMessage = ExceptionMessage.nonPositive(number)
        assertEquals(expected, actual)
    }

    @Test
    fun orThrowShouldFailWithByteThatIsLessThanZero() {
        val number: Byte = (Byte.MIN_VALUE..-1)
            .random()
            .toByte()
        val exception: IllegalArgumentException = assertFailsWith {
            PositiveInteger.orThrow(number)
        }
        val actual: ExceptionMessage = ExceptionMessage.orThrow(exception)
        val expected: ExceptionMessage = ExceptionMessage.nonPositive(number)
        assertEquals(expected, actual)
    }
}
