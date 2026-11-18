package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.errorMessage
import org.kotools.types.negativeInteger
import org.kotools.types.negativeIntegerExcept
import org.kotools.types.negativeIntegerString
import org.kotools.types.nonIntegerString
import org.kotools.types.nonNegativeInteger
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
class NegativeIntegerTest {
    // --------------------------- Factory functions ---------------------------

    @Test
    fun fromLongPreservesValue(): Unit = repeatTest {
        val value: Long = Random.nextLong(Long.MIN_VALUE..-1)

        val negativeInteger: NegativeInteger = NegativeInteger.fromLong(value)
        val safeNegativeInteger: NegativeInteger? =
            NegativeInteger.fromLongOrNull(value)

        val expected: Integer = Integer.fromLong(value)
        val message = "Input: $value"
        assertEquals(expected, actual = negativeInteger.toInteger(), message)
        assertEquals(
            expected,
            actual = safeNegativeInteger?.toInteger(),
            message
        )
    }

    @Test
    fun fromLongFailsWithNonNegativeValue(): Unit = repeatTest {
        val value: Long = Random.nextLong(0..Long.MAX_VALUE)

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: $value"
        ) { NegativeInteger.fromLong(value) }
        val expected: String =
            errorMessage("Non-negative integer", Integer.fromLong(value))
        assertEquals(expected, actual = exception.message)

        val safeNegativeInteger: NegativeInteger? =
            NegativeInteger.fromLongOrNull(value)
        assertNull(safeNegativeInteger, message = "Input: $value")
    }

    @Test
    fun fromIntegerPreservesValue(): Unit = repeatTest {
        val integer: Integer = Random.negativeInteger()
            .toInteger()

        val negativeInteger: NegativeInteger =
            NegativeInteger.fromInteger(integer)
        val safeNegativeInteger: NegativeInteger? =
            NegativeInteger.fromIntegerOrNull(integer)

        val message = "Input: $integer"
        assertEquals(integer, actual = negativeInteger.toInteger(), message)
        assertEquals(
            integer,
            actual = safeNegativeInteger?.toInteger(),
            message
        )
    }

    @Test
    fun fromIntegerFailsWithNonNegativeValue(): Unit = repeatTest {
        val integer: Integer = Random.nonNegativeInteger()
            .toInteger()

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: $integer"
        ) { NegativeInteger.fromInteger(integer) }
        val expected: String = errorMessage("Non-negative integer", integer)
        assertEquals(expected, actual = exception.message)

        val safeNegativeInteger: NegativeInteger? =
            NegativeInteger.fromIntegerOrNull(integer)
        assertNull(safeNegativeInteger, message = "Input: $integer")
    }

    @Test
    fun parsingPreservesValue(): Unit = repeatTest {
        val value: String = Random.negativeIntegerString()

        val negativeInteger: NegativeInteger = NegativeInteger.parse(value)
        val safeNegativeInteger: NegativeInteger? =
            NegativeInteger.parseOrNull(value)

        val expected: Integer = Integer.parse(value)
        val message = "Input: \"$value\""
        assertEquals(expected, actual = negativeInteger.toInteger(), message)
        assertEquals(
            expected,
            actual = safeNegativeInteger?.toInteger(),
            message
        )
    }

    @Test
    fun parsingFailsWithNonIntegerString(): Unit = repeatTest {
        val value: String = Random.nonIntegerString()

        assertFailsWith<NumberFormatException>(message = "Input: \"$value\"") {
            NegativeInteger.parse(value)
        }
        assertNull(
            NegativeInteger.parseOrNull(value),
            message = "Input: \"$value\""
        )
    }

    @Test
    fun parsingFailsWithZero(): Unit = repeatTest {
        val value: String = Random.zeroString()

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: \"$value\""
        ) { NegativeInteger.parse(value) }
        val expected: String =
            errorMessage("Non-negative integer", Integer.ZERO)
        assertEquals(expected, actual = exception.message)

        assertNull(
            NegativeInteger.parseOrNull(value),
            message = "Input: \"$value\""
        )
    }

    @Test
    fun parsingFailsWithPositiveValue(): Unit = repeatTest {
        val value: String = Random.positiveIntegerString()

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: \"$value\""
        ) { NegativeInteger.parse(value) }
        val integer: Integer = Integer.parse(value)
        val expected: String = errorMessage("Non-negative integer", integer)
        assertEquals(expected, actual = exception.message)

        assertNull(
            NegativeInteger.parseOrNull(value),
            message = "Input: \"$value\""
        )
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEqualityPassesWithSameValues(): Unit = repeatTest {
        val negativeInteger: NegativeInteger = Random.negativeInteger()
        val other: NegativeInteger =
            NegativeInteger.fromInteger(negativeInteger.toInteger())

        val message = "Inputs: this = $negativeInteger, other = $other"
        assertEquals(negativeInteger, other, message)
        assertEquals(negativeInteger.hashCode(), other.hashCode(), message)
    }

    @Test
    fun structuralEqualityIsReflexive(): Unit = repeatTest {
        val x: NegativeInteger = Random.negativeInteger()

        val message = "Input: $x"
        assertSame(x, x, message)
        assertEquals(x.hashCode(), x.hashCode(), message)
    }

    @Test
    fun structuralEqualityIsSymmetrical(): Unit = repeatTest {
        val x: NegativeInteger = Random.negativeInteger()
        val y: NegativeInteger = NegativeInteger.fromInteger(x.toInteger())

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
        val x: NegativeInteger = Random.negativeInteger()
        val y: NegativeInteger = NegativeInteger.fromInteger(x.toInteger())
        val z: NegativeInteger = NegativeInteger.fromInteger(y.toInteger())

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
        val negativeInteger: NegativeInteger = Random.negativeInteger()
        val other: NegativeInteger =
            Random.negativeIntegerExcept(illegal = negativeInteger)

        assertNotEquals(negativeInteger, other)
    }

    @Test
    fun structuralEqualityFailsWithDifferentTypes(): Unit = repeatTest {
        val negativeInteger: NegativeInteger = Random.negativeInteger()
        val other: Any = negativeInteger.toInteger()

        assertNotEquals(negativeInteger, other)
    }

    @Test
    fun structuralEqualityFailsWithNull(): Unit = repeatTest {
        val negativeInteger: NegativeInteger = Random.negativeInteger()
        val other: Any? = null

        assertNotEquals(negativeInteger, other)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toNonPositiveIntegerPreservesValue(): Unit = repeatTest {
        val negativeInteger: NegativeInteger = Random.negativeInteger()

        val result: NonPositiveInteger =
            negativeInteger.toNonPositiveInteger()

        assertEquals(
            negativeInteger.toInteger(),
            actual = result.toInteger(),
            message = "Input: $negativeInteger"
        )
    }

    @Test
    fun toNonZeroIntegerPreservesValue(): Unit = repeatTest {
        val negativeInteger: NegativeInteger = Random.negativeInteger()

        val result: NonZeroInteger = negativeInteger.toNonZeroInteger()

        assertEquals(
            negativeInteger.toInteger(),
            actual = result.toInteger(),
            message = "Input: $negativeInteger"
        )
    }

    @Test
    fun toIntegerRoundTripsWithFromInteger(): Unit = repeatTest {
        val integer: Integer = Random.negativeInteger()
            .toInteger()

        val negativeInteger: NegativeInteger =
            NegativeInteger.fromInteger(integer)
        val actual: Integer = negativeInteger.toInteger()

        assertEquals(integer, actual, message = "Input: $integer")
    }

    @Test
    fun toStringDelegatesToInteger(): Unit = repeatTest {
        val integer: Integer = Random.negativeInteger()
            .toInteger()

        val negativeInteger: NegativeInteger =
            NegativeInteger.fromInteger(integer)

        val actual: String = negativeInteger.toString()
        val expected: String = integer.toString()
        assertEquals(expected, actual, message = "Input: $integer")
    }
}
