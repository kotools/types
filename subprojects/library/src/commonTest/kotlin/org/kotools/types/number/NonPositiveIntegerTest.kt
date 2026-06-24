package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.errorMessage
import org.kotools.types.negativeIntegerString
import org.kotools.types.nonIntegerString
import org.kotools.types.nonPositiveInteger
import org.kotools.types.positiveInteger
import org.kotools.types.positiveIntegerString
import org.kotools.types.repeatTest
import org.kotools.types.zeroString
import kotlin.random.Random
import kotlin.random.nextLong
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonPositiveIntegerTest {
    // --------------------------- Factory functions ---------------------------

    @Test
    fun fromLongPreservesValue(): Unit = repeatTest {
        val value: Long = Random.nextLong(Long.MIN_VALUE..0)

        val nonPositiveInteger: NonPositiveInteger =
            NonPositiveInteger.fromLong(value)
        val safeNonPositiveInteger: NonPositiveInteger? =
            NonPositiveInteger.fromLongOrNull(value)

        val expected: Integer = Integer.fromLong(value)
        val message = "Input: $value"
        assertEquals(
            expected,
            actual = nonPositiveInteger.toInteger(),
            message
        )
        assertEquals(
            expected,
            actual = safeNonPositiveInteger?.toInteger(),
            message
        )
    }

    @Test
    fun fromLongFailsWithPositiveValue(): Unit = repeatTest {
        val value: Long = Random.nextLong(1..Long.MAX_VALUE)

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: $value"
        ) { NonPositiveInteger.fromLong(value) }
        val expected: String =
            errorMessage("Positive integer", Integer.fromLong(value))
        assertEquals(expected, actual = exception.message)

        val safeNonPositiveInteger: NonPositiveInteger? =
            NonPositiveInteger.fromLongOrNull(value)
        assertNull(safeNonPositiveInteger, message = "Input: $value")
    }

    @Test
    fun fromIntegerPreservesValue(): Unit = repeatTest {
        val integer: Integer = Random.nonPositiveInteger()
            .toInteger()

        val nonPositiveInteger: NonPositiveInteger =
            NonPositiveInteger.fromInteger(integer)
        val safeNonPositiveInteger: NonPositiveInteger? =
            NonPositiveInteger.fromIntegerOrNull(integer)

        val message = "Input: $integer"
        assertEquals(integer, actual = nonPositiveInteger.toInteger(), message)
        assertEquals(
            integer,
            actual = safeNonPositiveInteger?.toInteger(),
            message
        )
    }

    @Test
    fun fromIntegerFailsWithPositiveValue(): Unit = repeatTest {
        val integer: Integer = Random.positiveInteger()

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: $integer"
        ) { NonPositiveInteger.fromInteger(integer) }
        val expected: String = errorMessage("Positive integer", integer)
        assertEquals(expected, actual = exception.message)

        val safeNonPositiveInteger: NonPositiveInteger? =
            NonPositiveInteger.fromIntegerOrNull(integer)
        assertNull(safeNonPositiveInteger, message = "Input: $integer")
    }

    @Test
    fun parsingPreservesValueWithZero(): Unit = repeatTest {
        val value: String = Random.zeroString()

        val nonPositiveInteger: NonPositiveInteger =
            NonPositiveInteger.parse(value)

        val zero: Integer = Integer.fromLong(0)
        val message = "Input: \"$value\""
        assertEquals(zero, actual = nonPositiveInteger.toInteger(), message)
    }

    @Test
    fun parsingPreservesValueWithNegativeValue(): Unit = repeatTest {
        val value: String = Random.negativeIntegerString()

        val nonPositiveInteger: NonPositiveInteger =
            NonPositiveInteger.parse(value)

        val expected: Integer = Integer.parse(value)
        val message = "Input: \"$value\""
        assertEquals(
            expected,
            actual = nonPositiveInteger.toInteger(),
            message
        )
    }

    @Test
    fun parsingFailsWithNonIntegerString(): Unit = repeatTest {
        val value: String = Random.nonIntegerString()

        assertFailsWith<NumberFormatException>(message = "Input: \"$value\"") {
            NonPositiveInteger.parse(value)
        }
    }

    @Test
    fun parsingFailsWithPositiveValue(): Unit = repeatTest {
        val value: String = Random.positiveIntegerString()

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: \"$value\""
        ) { NonPositiveInteger.parse(value) }
        val integer: Integer = Integer.parse(value)
        val expected: String = errorMessage("Positive integer", integer)
        assertEquals(expected, actual = exception.message)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toIntegerRoundTripsWithFromInteger(): Unit = repeatTest {
        val integer: Integer = Random.nonPositiveInteger()
            .toInteger()

        val nonPositiveInteger: NonPositiveInteger =
            NonPositiveInteger.fromInteger(integer)
        val actual: Integer = nonPositiveInteger.toInteger()

        assertEquals(integer, actual, message = "Input: $integer")
    }
}
