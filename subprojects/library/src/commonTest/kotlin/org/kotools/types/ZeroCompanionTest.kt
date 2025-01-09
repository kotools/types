package org.kotools.types

import org.kotools.types.internal.InvalidZero
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class ZeroCompanionTest {
    @Test
    fun orNullShouldFailWithByteOtherThanZero() {
        val number: Byte = Byte.randomNonZero()
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
        val integer: Byte = Byte.randomNonZero()
        val decimal: Float = Random.nextFloat()
        val number: Float = integer + decimal
        val actual: Zero? = Zero.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullShouldFailWithDoubleOtherThanZero() {
        val integer: Byte = Byte.randomNonZero()
        val decimal: Double = Random.nextDouble()
        val number: Double = integer + decimal
        val actual: Zero? = Zero.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullShouldPassWithValidText(): Unit =
        sequenceOf("0", "000", "0.0", "0.000", "000.0", "000.000")
            .map(Zero.Companion::orNull)
            .forEach(::assertNotNull)

    @Test
    fun orNullShouldFailWithInvalidText(): Unit =
        sequenceOf("", " ", ".", "0.", ".0", "abc")
            .map(Zero.Companion::orNull)
            .forEach(::assertNull)

    @Test
    fun orThrowShouldFailWithByteOtherThanZero() {
        val number: Byte = Byte.randomNonZero()
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
        val integer: Byte = Byte.randomNonZero()
        val decimal: Float = Random.nextFloat()
        val number: Float = integer + decimal
        assertFailsWith<IllegalArgumentException> { Zero.orThrow(number) }
            .assertIsInvalidZero(number)
    }

    @Test
    fun orThrowShouldFailWithDoubleOtherThanZero() {
        val integer: Byte = Byte.randomNonZero()
        val decimal: Double = Random.nextDouble()
        val number: Double = integer + decimal
        assertFailsWith<IllegalArgumentException> { Zero.orThrow(number) }
            .assertIsInvalidZero(number)
    }

    @Test
    fun orThrowShouldPassWithValidText(): Unit =
        listOf("0", "000", "0.0", "0.000", "000.0", "000.000")
            .forEach(Zero.Companion::orThrow)

    @Test
    fun orThrowShouldFailWithInvalidText(): Unit =
        listOf("", " ", ".", "0.", ".0", "abc")
            .forEach {
                val exception: IllegalArgumentException =
                    assertFailsWith { Zero.orThrow(it) }
                val actual: String? = exception.message
                val expected = "'$it' is not a valid representation of zero."
                assertEquals(expected, actual)
            }
}

// ----------------------------- Number extensions -----------------------------

private fun Byte.Companion.randomNonZero(): Byte =
    listOf(MIN_VALUE..-1, 1..MAX_VALUE)
        .random()
        .random()
        .toByte()

// -------------------------------- Assertions ---------------------------------

private fun IllegalArgumentException.assertIsInvalidZero(number: Number) {
    val actual: String? = this.message
    val expected: String = InvalidZero(number)
        .toString()
    assertEquals(expected, actual)
}
