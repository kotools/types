package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.integerExcept
import org.kotools.types.internal.errorMessage
import org.kotools.types.nonIntegerString
import org.kotools.types.nonZeroIntegerStringWithLeadingZeros
import org.kotools.types.repeatTest
import org.kotools.types.zeroString
import kotlin.random.Random
import kotlin.random.nextLong
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonZeroIntegerTest {
    // ------------------------------- Creations -------------------------------

    @Test
    fun fromLongPreservesValue(): Unit = repeatTest {
        val value: Long = Random.nextLong(1..Long.MAX_VALUE)
            .let { if (Random.nextBoolean()) it else -it }

        val nonZeroInteger: NonZeroInteger = NonZeroInteger.fromLong(value)
        val safeNonZeroInteger: NonZeroInteger? =
            NonZeroInteger.fromLongOrNull(value)

        val expected: String = value.toString()
        val message = "Input: $value"
        assertEquals(expected, actual = nonZeroInteger.toString(), message)
        assertEquals(expected, actual = safeNonZeroInteger.toString(), message)
    }

    @Test
    fun fromLongFailsWithZero() {
        val exception: IllegalArgumentException =
            assertFailsWith { NonZeroInteger.fromLong(0) }
        val expected: String =
            errorMessage("Integer other than zero", Integer.of(0))
        assertEquals(expected, actual = exception.message)

        val safeNonZeroInteger: NonZeroInteger? =
            NonZeroInteger.fromLongOrNull(0)
        assertNull(safeNonZeroInteger)
    }

    @Test
    fun parsingRemovesLeadingZeros(): Unit = repeatTest {
        val value: String = Random.nonZeroIntegerStringWithLeadingZeros()

        val nonZeroInteger: NonZeroInteger = NonZeroInteger.parse(value)
        val safeNonZeroInteger: NonZeroInteger? =
            NonZeroInteger.parseOrNull(value)

        val actual: String = nonZeroInteger.toString()
        val message = "Input: \"$value\""
        assertTrue(
            "\"$actual\" must not have leading zeros (input: \"$value\")."
        ) { actual.first(Char::isDigit) != '0' }
        assertEquals(actual, safeNonZeroInteger.toString(), message)
    }

    @Test
    fun parsingFailsWithNonintegerString(): Unit = repeatTest {
        val value: String = Random.nonIntegerString()

        assertFailsWith<NumberFormatException>(message = "Input: \"$value\"") {
            NonZeroInteger.parse(value)
        }
        assertNull(NonZeroInteger.parseOrNull(value), message = "Input: \"$value\"")
    }

    @Test
    fun parsingFailsWithZero(): Unit = repeatTest {
        val value: String = Random.zeroString()

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: \"$value\""
        ) { NonZeroInteger.parse(value) }
        val expected: String =
            errorMessage("Integer other than zero", Integer.of(0))
        assertEquals(expected, actual = exception.message)

        assertNull(NonZeroInteger.parseOrNull(value), message = "Input: \"$value\"")
    }

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
