package org.kotools.types

import org.kotools.types.internal.ErrorMessage
import kotlin.random.Random
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
        val number: Byte = Byte.randomNonZero()
        val actual: Zero? = Zero.orNull(number)
        assertNull(actual)
    }

    @Test
    fun orNullShouldPassWithShortThatEqualsZero() {
        val number: Short = 0
        val actual: Zero? = Zero.orNull(number)
        assertNotNull(actual)
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
    fun orNullShouldPassWithIntThatEqualsZero() {
        val number = 0
        val actual: Zero? = Zero.orNull(number)
        assertNotNull(actual)
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
    fun orNullShouldPassWithLongThatEqualsZero() {
        val number: Long = 0
        val actual: Zero? = Zero.orNull(number)
        assertNotNull(actual)
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
    fun orNullShouldPassWithFloatThatEqualsZero() {
        val number = 0f
        val actual: Zero? = Zero.orNull(number)
        assertNotNull(actual)
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
    fun orNullShouldPassWithDoubleThatEqualsZero() {
        val number = 0.0
        val actual: Zero? = Zero.orNull(number)
        assertNotNull(actual)
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
    fun orNullShouldPassWithStringRepresentingZero(): Unit =
        sequenceOf("0", "000", "0.0", "0.000", "000.0", "000.000")
            .map(Zero.Companion::orNull)
            .forEach(::assertNotNull)

    @Test
    fun orNullShouldFailWithStringNotRepresentingZero(): Unit =
        sequenceOf("", " ", ".", "0.", ".0", "abc")
            .map(Zero.Companion::orNull)
            .forEach(::assertNull)

    @Test
    fun orThrowShouldPassWithStringRepresentingZero(): Unit =
        listOf("0", "000", "0.0", "0.000", "000.0", "000.000")
            .forEach(Zero.Companion::orThrow)

    @Test
    fun orThrowShouldFailWithStringNotRepresentingZero(): Unit =
        listOf("", " ", ".", "0.", ".0", "abc")
            .forEach {
                val exception: IllegalArgumentException =
                    assertFailsWith { Zero.orThrow(it) }
                val actual: String? = exception.message
                val expected: String = ErrorMessage.invalidZero(it)
                assertEquals(expected, actual)
            }

    @Test
    fun orThrowShouldFailWithAnyNotRepresentingZero(): Unit =
        listOf<Any>("", " ", ".", "0.", ".0", "abc")
            .forEach {
                val exception: IllegalArgumentException =
                    assertFailsWith { Zero.orThrow(it) }
                val actual: String? = exception.message
                val expected: String = ErrorMessage.invalidZero(it)
                assertEquals(expected, actual)
            }
}

// ----------------------------- Number extensions -----------------------------

private fun Byte.Companion.randomNonZero(): Byte =
    listOf(MIN_VALUE..-1, 1..MAX_VALUE)
        .random()
        .random()
        .toByte()
