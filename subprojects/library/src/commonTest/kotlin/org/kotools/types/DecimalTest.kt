package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalKotoolsTypesApi::class)
class DecimalTest {
    @Test
    fun createsFromLong() {
        // Given
        val number: Long = Long.MAX_VALUE
        // When
        val result: Decimal = Decimal.fromInteger(number)
        // Then
        assertEquals(expected = "$number", "$result")
    }
}
