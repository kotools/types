package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.errorMessage
import org.kotools.types.negativeIntegerString
import org.kotools.types.nonIntegerString
import org.kotools.types.nonPositiveInteger
import org.kotools.types.positiveInteger
import org.kotools.types.positiveIntegerExcept
import org.kotools.types.positiveIntegerString
import org.kotools.types.repeatTest
import org.kotools.types.zeroString
import kotlin.random.Random
import kotlin.random.nextLong
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerTest {
    // --------------------------- Factory functions ---------------------------

    @Test
    fun fromLongPreservesValue(): Unit = repeatTest {
        val value: Long = Random.nextLong(1..Long.MAX_VALUE)

        val positiveInteger: PositiveInteger = PositiveInteger.fromLong(value)
        val safePositiveInteger: PositiveInteger? =
            PositiveInteger.fromLongOrNull(value)

        val expected: Integer = Integer.fromLong(value)
        val message = "Input: $value"
        assertEquals(expected, actual = positiveInteger.toInteger(), message)
        assertEquals(
            expected,
            actual = safePositiveInteger?.toInteger(),
            message
        )
    }

    @Test
    fun fromLongFailsWithNonPositiveValue(): Unit = repeatTest {
        val value: Long = Random.nextLong(Long.MIN_VALUE..0)

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: $value"
        ) { PositiveInteger.fromLong(value) }
        val expected: String =
            errorMessage("Non-positive integer", Integer.fromLong(value))
        assertEquals(expected, actual = exception.message)

        val safePositiveInteger: PositiveInteger? =
            PositiveInteger.fromLongOrNull(value)
        assertNull(safePositiveInteger, message = "Input: $value")
    }

    @Test
    fun fromIntegerPreservesValue(): Unit = repeatTest {
        val integer: Integer = Random.positiveInteger()
            .toInteger()

        val positiveInteger: PositiveInteger =
            PositiveInteger.fromInteger(integer)
        val safePositiveInteger: PositiveInteger? =
            PositiveInteger.fromIntegerOrNull(integer)

        val message = "Input: $integer"
        assertEquals(integer, actual = positiveInteger.toInteger(), message)
        assertEquals(
            integer,
            actual = safePositiveInteger?.toInteger(),
            message
        )
    }

    @Test
    fun fromIntegerFailsWithNonPositiveValue(): Unit = repeatTest {
        val integer: Integer = Random.nonPositiveInteger()
            .toInteger()

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: $integer"
        ) { PositiveInteger.fromInteger(integer) }
        val expected: String = errorMessage("Non-positive integer", integer)
        assertEquals(expected, actual = exception.message)

        val safePositiveInteger: PositiveInteger? =
            PositiveInteger.fromIntegerOrNull(integer)
        assertNull(safePositiveInteger, message = "Input: $integer")
    }

    @Test
    fun parsingPreservesValue(): Unit = repeatTest {
        val value: String = Random.positiveIntegerString()

        val positiveInteger: PositiveInteger = PositiveInteger.parse(value)
        val safePositiveInteger: PositiveInteger? =
            PositiveInteger.parseOrNull(value)

        val expected: Integer = Integer.parse(value)
        val message = "Input: \"$value\""
        assertEquals(expected, actual = positiveInteger.toInteger(), message)
        assertEquals(
            expected,
            actual = safePositiveInteger?.toInteger(),
            message
        )
    }

    @Test
    fun parsingFailsWithNonIntegerString(): Unit = repeatTest {
        val value: String = Random.nonIntegerString()

        assertFailsWith<NumberFormatException>(message = "Input: \"$value\"") {
            PositiveInteger.parse(value)
        }
        assertNull(
            PositiveInteger.parseOrNull(value),
            message = "Input: \"$value\""
        )
    }

    @Test
    fun parsingFailsWithZero(): Unit = repeatTest {
        val value: String = Random.zeroString()

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: \"$value\""
        ) { PositiveInteger.parse(value) }
        val expected: String =
            errorMessage("Non-positive integer", Integer.ZERO)
        assertEquals(expected, actual = exception.message)

        assertNull(
            PositiveInteger.parseOrNull(value),
            message = "Input: \"$value\""
        )
    }

    @Test
    fun parsingFailsWithNegativeValue(): Unit = repeatTest {
        val value: String = Random.negativeIntegerString()

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: \"$value\""
        ) { PositiveInteger.parse(value) }
        val integer: Integer = Integer.parse(value)
        val expected: String = errorMessage("Non-positive integer", integer)
        assertEquals(expected, actual = exception.message)

        assertNull(
            PositiveInteger.parseOrNull(value),
            message = "Input: \"$value\""
        )
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEqualityPassesWithSameValues(): Unit = repeatTest {
        val positiveInteger: PositiveInteger = Random.positiveInteger()
        val other: PositiveInteger =
            PositiveInteger.fromInteger(positiveInteger.toInteger())

        val message = "Inputs: this = $positiveInteger, other = $other"
        assertEquals(positiveInteger, other, message)
        assertEquals(positiveInteger.hashCode(), other.hashCode(), message)
    }

    @Test
    fun structuralEqualityIsReflexive(): Unit = repeatTest {
        val x: PositiveInteger = Random.positiveInteger()

        val message = "Input: $x"
        assertSame(x, x, message)
        assertEquals(x.hashCode(), x.hashCode(), message)
    }

    @Test
    fun structuralEqualityIsSymmetrical(): Unit = repeatTest {
        val x: PositiveInteger = Random.positiveInteger()
        val y: PositiveInteger = PositiveInteger.fromInteger(x.toInteger())

        val xHashCode: Int = x.hashCode()
        val yHashCode: Int = y.hashCode()

        val messageSuffix = "expected to be symmetrical (x = $x, y = $y)."
        assertTrue("Integers equality $messageSuffix") { x == y && y == x }
        assertTrue("Integers' hash code $messageSuffix") {
            xHashCode == yHashCode && yHashCode == xHashCode
        }
    }

    @Test
    fun structuralEqualityIsTransitive(): Unit = repeatTest {
        val x: PositiveInteger = Random.positiveInteger()
        val y: PositiveInteger = PositiveInteger.fromInteger(x.toInteger())
        val z: PositiveInteger = PositiveInteger.fromInteger(y.toInteger())

        val xHashCode: Int = x.hashCode()
        val yHashCode: Int = y.hashCode()
        val zHashCode: Int = z.hashCode()

        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(x, y, message)
        assertEquals(xHashCode, yHashCode, message)

        assertEquals(y, z, message)
        assertEquals(yHashCode, zHashCode, message)

        assertEquals(x, z, message)
        assertEquals(xHashCode, zHashCode, message)
    }

    @Test
    fun structuralEqualityFailsWithDifferentValues(): Unit = repeatTest {
        val positiveInteger: PositiveInteger = Random.positiveInteger()
        val other: PositiveInteger =
            Random.positiveIntegerExcept(illegal = positiveInteger)

        assertNotEquals(positiveInteger, other)
    }

    @Test
    fun structuralEqualityFailsWithDifferentTypes(): Unit = repeatTest {
        val positiveInteger: PositiveInteger = Random.positiveInteger()
        val other: Any = positiveInteger.toInteger()

        assertNotEquals(positiveInteger, other)
    }

    @Test
    fun structuralEqualityFailsWithNull(): Unit = repeatTest {
        val positiveInteger: PositiveInteger = Random.positiveInteger()
        val other: Any? = null

        assertNotEquals(positiveInteger, other)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toNonNegativeIntegerPreservesValue(): Unit = repeatTest {
        val positiveInteger: PositiveInteger = Random.positiveInteger()

        val result: NonNegativeInteger = positiveInteger.toNonNegativeInteger()

        assertEquals(
            positiveInteger.toInteger(),
            actual = result.toInteger(),
            message = "Input: $positiveInteger"
        )
    }

    @Test
    fun toNonZeroIntegerPreservesValue(): Unit = repeatTest {
        val positiveInteger: PositiveInteger = Random.positiveInteger()

        val result: NonZeroInteger = positiveInteger.toNonZeroInteger()

        assertEquals(
            positiveInteger.toInteger(),
            actual = result.toInteger(),
            message = "Input: $positiveInteger"
        )
    }

    @Test
    fun toIntegerRoundTripsWithFromInteger(): Unit = repeatTest {
        val integer: Integer = Random.positiveInteger()
            .toInteger()

        val positiveInteger: PositiveInteger =
            PositiveInteger.fromInteger(integer)
        val actual: Integer = positiveInteger.toInteger()

        assertEquals(integer, actual, message = "Input: $integer")
    }

    @Test
    fun toStringDelegatesToInteger(): Unit = repeatTest {
        val integer: Integer = Random.positiveInteger()
            .toInteger()

        val positiveInteger: PositiveInteger =
            PositiveInteger.fromInteger(integer)

        val actual: String = positiveInteger.toString()
        val expected: String = integer.toString()
        assertEquals(expected, actual, message = "Input: $integer")
    }
}
