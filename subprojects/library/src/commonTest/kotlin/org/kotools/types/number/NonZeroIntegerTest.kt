package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.integerExcept
import org.kotools.types.internal.errorMessage
import org.kotools.types.repeatTest
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonZeroIntegerTest {
    // ------------------------------- Creations -------------------------------

    @Test
    fun fromIntegerPreservesValue(): Unit = repeatTest {
        val integer: Integer = Random.integerExcept(illegal = Integer.of(0))

        val nonZeroInteger: NonZeroInteger = NonZeroInteger.fromInteger(integer)
        val safeNonZeroInteger: NonZeroInteger? =
            NonZeroInteger.fromIntegerOrNull(integer)

        val expected: String = integer.toString()
        val message = "Input: $integer"
        assertEquals(expected, actual = nonZeroInteger.toString(), message)
        assertEquals(expected, actual = safeNonZeroInteger.toString(), message)
    }

    @Test
    fun fromIntegerFailsWithZero() {
        val zero: Integer = Integer.of(0)

        val exception: IllegalArgumentException = assertFailsWith {
            NonZeroInteger.fromInteger(zero)
        }
        val expected: String = errorMessage("Integer other than zero", zero)
        assertEquals(expected, actual = exception.message)

        val safeNonZeroInteger: NonZeroInteger? =
            NonZeroInteger.fromIntegerOrNull(zero)
        assertNull(safeNonZeroInteger)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringDelegatesToInteger(): Unit = repeatTest {
        val integer: Integer = Random.integerExcept(illegal = Integer.of(0))

        val nonZeroInteger: NonZeroInteger = NonZeroInteger.fromInteger(integer)

        val actual: String = nonZeroInteger.toString()
        val expected: String = integer.toString()
        assertEquals(expected, actual, message = "Input: $integer")
    }
}
