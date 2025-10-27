package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonZeroIntegerTest {
    @Test
    fun constructorWithZeroInt() {
        // Given
        val number = 0
        // When
        val result: IllegalArgumentException = assertFailsWith {
            NonZeroInteger(number)
        }
        // Then
        val expected = "NonZeroInteger shouldn't be zero"
        assertEquals(expected, result.message)
    }
}
