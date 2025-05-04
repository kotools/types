package org.kotools.types

import org.kotools.types.internal.ExceptionMessage
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
        PositiveInteger.orNull(number)
            .assertNull(number)
    }

    @Test
    fun orNullShouldFailWithIntNumberLessThanZero() {
        val number: Int = (Int.MIN_VALUE..-1).random()
        PositiveInteger.orNull(number)
            .assertNull(number)
    }

    @Test
    fun orNullShouldPassWithLongNumberGreaterThanZero() {
        val number: Long = (1..Long.MAX_VALUE).random()
        val actual: PositiveInteger? = PositiveInteger.orNull(number)
        val message = "$number is greater than zero."
        assertNotNull(actual, message)
    }

    @Test
    fun orNullShouldFailWithLongNumberThatEqualsZero() {
        val number: Long = Zero()
            .toLong()
        PositiveInteger.orNull(number)
            .assertNull(number)
    }

    @Test
    fun orNullShouldFailWithLongNumberLessThanZero() {
        val number: Long = (Long.MIN_VALUE..-1).random()
        PositiveInteger.orNull(number)
            .assertNull(number)
    }

    @Test
    fun orThrowShouldPassWithIntNumberGreaterThanZero() {
        val number: Int = (1..Int.MAX_VALUE).random()
        PositiveInteger.orThrow(number)
    }

    @Test
    fun orThrowShouldFailWithIntNumberThatEqualsZero() {
        val number: Int = Zero()
            .toInt()
        assertThrowsIllegalArgumentException { PositiveInteger.orThrow(number) }
            .assertEquals { ExceptionMessage.nonPositive(number) }
    }

    @Test
    fun orThrowShouldFailWithIntNumberLessThanZero() {
        val number: Int = (Int.MIN_VALUE..-1).random()
        assertThrowsIllegalArgumentException { PositiveInteger.orThrow(number) }
            .assertEquals { ExceptionMessage.nonPositive(number) }
    }

    @Test
    fun orThrowShouldPassWithLongNumberGreaterThanZero() {
        val number: Long = (1..Long.MAX_VALUE).random()
        PositiveInteger.orThrow(number)
    }

    @Test
    fun orThrowShouldFailWithLongNumberThatEqualsZero() {
        val number: Long = Zero()
            .toLong()
        assertThrowsIllegalArgumentException { PositiveInteger.orThrow(number) }
            .assertEquals { ExceptionMessage.nonPositive(number) }
    }

    @Test
    fun orThrowShouldFailWithLongNumberLessThanZero() {
        val number: Long = (Long.MIN_VALUE..-1).random()
        assertThrowsIllegalArgumentException { PositiveInteger.orThrow(number) }
            .assertEquals { ExceptionMessage.nonPositive(number) }
    }
}

// -------------------------------- Assertions ---------------------------------

@OptIn(ExperimentalKotoolsTypesApi::class)
private fun PositiveInteger?.assertNull(number: Number) {
    val message: String = ExceptionMessage.nonPositive(number)
        .toString()
    assertNull(this, message)
}
