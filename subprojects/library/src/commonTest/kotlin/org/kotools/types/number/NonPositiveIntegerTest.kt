package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.errorMessage
import org.kotools.types.negativeIntegerString
import org.kotools.types.nonIntegerString
import org.kotools.types.nonPositiveInteger
import org.kotools.types.nonPositiveIntegerExcept
import org.kotools.types.positiveInteger
import org.kotools.types.positiveIntegerString
import org.kotools.types.repeatTest
import org.kotools.types.zeroString
import kotlin.random.Random
import kotlin.random.nextLong
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

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
        val safeNonPositiveInteger: NonPositiveInteger? =
            NonPositiveInteger.parseOrNull(value)

        val zero: Integer = Integer.fromLong(0)
        val message = "Input: \"$value\""
        assertEquals(zero, actual = nonPositiveInteger.toInteger(), message)
        assertEquals(
            zero,
            actual = safeNonPositiveInteger?.toInteger(),
            message
        )
    }

    @Test
    fun parsingPreservesValueWithNegativeValue(): Unit = repeatTest {
        val value: String = Random.negativeIntegerString()

        val nonPositiveInteger: NonPositiveInteger =
            NonPositiveInteger.parse(value)
        val safeNonPositiveInteger: NonPositiveInteger? =
            NonPositiveInteger.parseOrNull(value)

        val expected: Integer = Integer.parse(value)
        val message = "Input: \"$value\""
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
    fun parsingFailsWithNonIntegerString(): Unit = repeatTest {
        val value: String = Random.nonIntegerString()

        assertFailsWith<NumberFormatException>(message = "Input: \"$value\"") {
            NonPositiveInteger.parse(value)
        }
        assertNull(
            NonPositiveInteger.parseOrNull(value),
            message = "Input: \"$value\""
        )
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

        assertNull(
            NonPositiveInteger.parseOrNull(value),
            message = "Input: \"$value\""
        )
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEqualityPassesWithSameValues(): Unit = repeatTest {
        val nonPositiveInteger: NonPositiveInteger = Random.nonPositiveInteger()
        val other: NonPositiveInteger =
            NonPositiveInteger.fromInteger(nonPositiveInteger.toInteger())

        val message = "Inputs: this = $nonPositiveInteger, other = $other"
        assertEquals(nonPositiveInteger, other, message)
        assertEquals(nonPositiveInteger.hashCode(), other.hashCode(), message)
    }

    @Test
    fun structuralEqualityIsReflexive(): Unit = repeatTest {
        val x: NonPositiveInteger = Random.nonPositiveInteger()

        val message = "Input: $x"
        assertSame(x, x, message)
        assertEquals(x.hashCode(), x.hashCode(), message)
    }

    @Test
    fun structuralEqualityIsSymmetrical(): Unit = repeatTest {
        val x: NonPositiveInteger = Random.nonPositiveInteger()
        val y: NonPositiveInteger =
            NonPositiveInteger.fromInteger(x.toInteger())

        val xHashCode: Int = x.hashCode()
        val yHashCode: Int = y.hashCode()

        val message = "Inputs: x = $x, y = $y"
        assertEquals(x, y, message)
        assertEquals(xHashCode, yHashCode, message)

        assertEquals(y, x, message)
        assertEquals(yHashCode, xHashCode, message)
    }

    @Test
    fun structuralEqualityIsTransitive(): Unit = repeatTest {
        val x: NonPositiveInteger = Random.nonPositiveInteger()
        val y: NonPositiveInteger =
            NonPositiveInteger.fromInteger(x.toInteger())
        val z: NonPositiveInteger =
            NonPositiveInteger.fromInteger(y.toInteger())

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
        val nonPositiveInteger: NonPositiveInteger = Random.nonPositiveInteger()
        val other: NonPositiveInteger =
            Random.nonPositiveIntegerExcept(illegal = nonPositiveInteger)

        val message = "Inputs: this = $nonPositiveInteger, other = $other"
        assertNotEquals(nonPositiveInteger, other, message)
        assertNotEquals(
            nonPositiveInteger.hashCode(),
            other.hashCode(),
            message
        )
    }

    @Test
    fun structuralEqualityFailsWithDifferentTypes(): Unit = repeatTest {
        val nonPositiveInteger: NonPositiveInteger = Random.nonPositiveInteger()
        val other: Any = nonPositiveInteger.toInteger()

        val message = "Inputs: this = $nonPositiveInteger, other = $other"
        assertNotEquals(nonPositiveInteger, other, message)
        assertNotEquals(
            nonPositiveInteger.hashCode(),
            other.hashCode(),
            message
        )
    }

    @Test
    fun structuralEqualityFailsWithNull(): Unit = repeatTest {
        val x: NonPositiveInteger = Random.nonPositiveInteger()
        val y: Any? = null

        val equality: Boolean = x == y
        val hashEquality: Boolean = x.hashCode() == y.hashCode()

        val message = "Structural equality must fail with null."
        assertFalse(equality, message)
        assertFalse(hashEquality, message)
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
