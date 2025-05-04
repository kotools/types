package org.kotools.types

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerCompanionTest {
    @Test
    fun orNullShouldPassWithIntNumberGreaterThanZero() {
        val number: Int = (1..Int.MAX_VALUE).random()
        val actual: PositiveInteger? = PositiveInteger.orNull(number)
        val message = "$number is greater than zero."
        assertNotNull(actual, message)
    }

    @Test
    fun orNullShouldFailWithIntNumberThatEqualsZero() {
        val number: Int = Zero()
            .toInt()
        val actual: PositiveInteger? = PositiveInteger.orNull(number)
        val message = "$number is not greater than zero."
        assertNull(actual, message)
    }

    @Test
    fun orNullShouldFailWithIntNumberLessThanZero() {
        val number: Int = (Int.MIN_VALUE..-1).random()
        val actual: PositiveInteger? = PositiveInteger.orNull(number)
        val message = "$number is not greater than zero."
        assertNull(actual, message)
    }
}
