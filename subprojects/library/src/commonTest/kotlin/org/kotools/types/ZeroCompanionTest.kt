package org.kotools.types

import org.kotools.types.internal.InvalidZero
import kotlin.random.Random
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
    fun orNullShouldFailWithShortOtherThanZero() {
        val number: Short = setOf(Short.MIN_VALUE..-1, 1..Short.MAX_VALUE)
            .random()
            .random()
            .toShort()
        val actual: Zero? = Zero.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullShouldFailWithIntOtherThanZero() {
        val number: Int = setOf(Short.MIN_VALUE..-1, 1..Short.MAX_VALUE)
            .random()
            .random()
        val actual: Zero? = Zero.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullShouldFailWithLongOtherThanZero() {
        val number: Long = setOf(Long.MIN_VALUE..-1, 1..Long.MAX_VALUE)
            .random()
            .random()
        val actual: Zero? = Zero.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullShouldFailWithFloatOtherThanZero() {
        val number: Float = Float.randomNonZero()
        val actual: Zero? = Zero.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orThrowShouldFailWithByteOtherThanZero() {
        val number: Byte = setOf(Byte.MIN_VALUE..-1, 1..Byte.MAX_VALUE)
            .random()
            .random()
            .toByte()
        assertFailsWith<IllegalArgumentException> { Zero.orThrow(number) }
            .assertIsInvalidZero(number)
    }

    @Test
    fun orThrowShouldFailWithShortOtherThanZero() {
        val number: Short = setOf(Short.MIN_VALUE..-1, 1..Short.MAX_VALUE)
            .random()
            .random()
            .toShort()
        assertFailsWith<IllegalArgumentException> { Zero.orThrow(number) }
            .assertIsInvalidZero(number)
    }

    @Test
    fun orThrowShouldFailWithIntOtherThanZero() {
        val number: Int = setOf(Int.MIN_VALUE..-1, 1..Int.MAX_VALUE)
            .random()
            .random()
        assertFailsWith<IllegalArgumentException> { Zero.orThrow(number) }
            .assertIsInvalidZero(number)
    }

    @Test
    fun orThrowShouldFailWithLongOtherThanZero() {
        val number: Long = setOf(Long.MIN_VALUE..-1, 1..Long.MAX_VALUE)
            .random()
            .random()
        assertFailsWith<IllegalArgumentException> { Zero.orThrow(number) }
            .assertIsInvalidZero(number)
    }

    @Test
    fun orThrowShouldFailWithFloatOtherThanZero() {
        val number: Float = Float.randomNonZero()
        assertFailsWith<IllegalArgumentException> { Zero.orThrow(number) }
            .assertIsInvalidZero(number)
    }
}

// ----------------------------- Number extensions -----------------------------

private fun Float.Companion.randomNonZero(): Float {
    val negativeRange: IntRange = Byte.MIN_VALUE..-1
    val positiveRange: IntRange = 1..Byte.MAX_VALUE
    val integer: Int = negativeRange.plus(positiveRange)
        .random()
    val decimal: Float = Random.nextFloat()
    return integer + decimal
}

// -------------------------------- Assertions ---------------------------------

private fun IllegalArgumentException.assertIsInvalidZero(number: Number) {
    val actual: String? = this.message
    val expected: String = InvalidZero(number)
        .toString()
    assertEquals(expected, actual)
}
