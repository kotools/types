package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.errorMessage
import org.kotools.types.negativeIntegerString
import org.kotools.types.nonIntegerString
import org.kotools.types.nonNegativeInteger
import org.kotools.types.nonNegativeIntegerExcept
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
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonNegativeIntegerTest {
    // --------------------------- Factory functions ---------------------------

    @Test
    fun fromLongPreservesValue(): Unit = repeatTest {
        val value: Long = Random.nextLong(0..Long.MAX_VALUE)

        val nonNegativeInteger: NonNegativeInteger =
            NonNegativeInteger.fromLong(value)
        val safeNonNegativeInteger: NonNegativeInteger? =
            NonNegativeInteger.fromLongOrNull(value)

        val expected: String = value.toString()
        val message = "Input: $value"
        assertEquals(expected, actual = nonNegativeInteger.toString(), message)
        assertEquals(
            expected,
            actual = safeNonNegativeInteger.toString(),
            message
        )
    }

    @Test
    fun fromLongFailsWithNegativeValue(): Unit = repeatTest {
        val value: Long = Random.nextLong(Long.MIN_VALUE..-1)

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: $value"
        ) { NonNegativeInteger.fromLong(value) }
        val expected: String =
            errorMessage("Negative integer", Integer.fromLong(value))
        assertEquals(expected, actual = exception.message)

        val safeNonNegativeInteger: NonNegativeInteger? =
            NonNegativeInteger.fromLongOrNull(value)
        assertNull(safeNonNegativeInteger, message = "Input: $value")
    }

    @Test
    fun fromIntegerPreservesValue(): Unit = repeatTest {
        val integer: Integer = Random.nonNegativeInteger()
            .toInteger()

        val nonNegativeInteger: NonNegativeInteger =
            NonNegativeInteger.fromInteger(integer)
        val safeNonNegativeInteger: NonNegativeInteger? =
            NonNegativeInteger.fromIntegerOrNull(integer)

        val expected: String = integer.toString()
        val message = "Input: $integer"
        assertEquals(expected, actual = nonNegativeInteger.toString(), message)
        assertEquals(
            expected,
            actual = safeNonNegativeInteger.toString(),
            message
        )
    }

    @Test
    fun fromIntegerFailsWithNegativeValue(): Unit = repeatTest {
        val integer: Integer = -Random.positiveInteger()

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: $integer"
        ) { NonNegativeInteger.fromInteger(integer) }
        val expected: String = errorMessage("Negative integer", integer)
        assertEquals(expected, actual = exception.message)

        val safeNonNegativeInteger: NonNegativeInteger? =
            NonNegativeInteger.fromIntegerOrNull(integer)
        assertNull(safeNonNegativeInteger, message = "Input: $integer")
    }

    @Test
    fun parsingPreservesValueWithZero(): Unit = repeatTest {
        val value: String = Random.zeroString()

        val nonNegativeInteger: NonNegativeInteger =
            NonNegativeInteger.parse(value)
        val safeNonNegativeInteger: NonNegativeInteger? =
            NonNegativeInteger.parseOrNull(value)

        val message = "Input: \"$value\""
        assertEquals("0", actual = nonNegativeInteger.toString(), message)
        assertEquals("0", actual = safeNonNegativeInteger.toString(), message)
    }

    @Test
    fun parsingPreservesValueWithPositiveValue(): Unit = repeatTest {
        val value: String = Random.positiveIntegerString()

        val nonNegativeInteger: NonNegativeInteger =
            NonNegativeInteger.parse(value)
        val safeNonNegativeInteger: NonNegativeInteger? =
            NonNegativeInteger.parseOrNull(value)

        val expected: String = Integer.parse(value)
            .toString()
        val message = "Input: \"$value\""
        assertEquals(expected, actual = nonNegativeInteger.toString(), message)
        assertEquals(
            expected,
            actual = safeNonNegativeInteger.toString(),
            message
        )
    }

    @Test
    fun parsingFailsWithNonIntegerString(): Unit = repeatTest {
        val value: String = Random.nonIntegerString()

        assertFailsWith<NumberFormatException>(message = "Input: \"$value\"") {
            NonNegativeInteger.parse(value)
        }
        assertNull(
            NonNegativeInteger.parseOrNull(value),
            message = "Input: \"$value\""
        )
    }

    @Test
    fun parsingFailsWithNegativeValue(): Unit = repeatTest {
        val value: String = Random.negativeIntegerString()

        val exception: IllegalArgumentException = assertFailsWith(
            message = "Input: \"$value\""
        ) { NonNegativeInteger.parse(value) }
        val integer: Integer = Integer.parse(value)
        val expected: String = errorMessage("Negative integer", integer)
        assertEquals(expected, actual = exception.message)

        assertNull(
            NonNegativeInteger.parseOrNull(value),
            message = "Input: \"$value\""
        )
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEqualityPassesWithSameValues(): Unit = repeatTest {
        val nonNegativeInteger: NonNegativeInteger = Random.nonNegativeInteger()
        val other: NonNegativeInteger =
            NonNegativeInteger.fromInteger(nonNegativeInteger.toInteger())

        val message = "Inputs: this = $nonNegativeInteger, other = $other"
        assertEquals(nonNegativeInteger, other, message)
        assertEquals(nonNegativeInteger.hashCode(), other.hashCode(), message)
    }

    @Test
    fun structuralEqualityIsReflexive(): Unit = repeatTest {
        val x: NonNegativeInteger = Random.nonNegativeInteger()

        val message = "Input: $x"
        assertSame(x, x, message)
        assertEquals(x.hashCode(), x.hashCode(), message)
    }

    @Test
    fun structuralEqualityIsSymmetrical(): Unit = repeatTest {
        val x: NonNegativeInteger = Random.nonNegativeInteger()
        val y: NonNegativeInteger =
            NonNegativeInteger.fromInteger(x.toInteger())

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
        val x: NonNegativeInteger = Random.nonNegativeInteger()
        val y: NonNegativeInteger =
            NonNegativeInteger.fromInteger(x.toInteger())
        val z: NonNegativeInteger =
            NonNegativeInteger.fromInteger(y.toInteger())

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
        val nonNegativeInteger: NonNegativeInteger = Random.nonNegativeInteger()
        val other: NonNegativeInteger =
            Random.nonNegativeIntegerExcept(illegal = nonNegativeInteger)

        val message = "Inputs: this = $nonNegativeInteger, other = $other"
        assertNotEquals(nonNegativeInteger, other, message)
        assertNotEquals(
            nonNegativeInteger.hashCode(),
            other.hashCode(),
            message
        )
    }

    @Test
    fun structuralEqualityFailsWithDifferentTypes(): Unit = repeatTest {
        val nonNegativeInteger: NonNegativeInteger = Random.nonNegativeInteger()
        val other: Any = nonNegativeInteger.toString()

        val message = "Inputs: this = $nonNegativeInteger, other = \"$other\""
        assertNotEquals(nonNegativeInteger, other, message)
        assertNotEquals(
            nonNegativeInteger.hashCode(),
            other.hashCode(),
            message
        )
    }

    @Test
    fun structuralEqualityFailsWithNull(): Unit = repeatTest {
        val x: NonNegativeInteger = Random.nonNegativeInteger()
        val y: Any? = null

        val equality: Boolean = x == y
        val hashEquality: Boolean = x.hashCode() == y.hashCode()

        val message = "Structural equality must fail with null."
        assertFalse(equality, message)
        assertFalse(hashEquality, message)
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinusIsAlwaysNonPositive(): Unit = repeatTest {
        val x: NonNegativeInteger = Random.nonNegativeInteger()

        val result: NonPositiveInteger = -x

        val actual: Boolean = result.toInteger() <= Integer.ZERO
        assertTrue(actual, message = "Input: $x")
    }

    @Test
    fun unaryMinusOnZero() {
        val zero: NonNegativeInteger = NonNegativeInteger.fromLong(0)
        val actual: NonPositiveInteger = -zero
        val expected: NonPositiveInteger = NonPositiveInteger.fromLong(0)
        assertEquals(expected, actual)
    }

    @Test
    fun unaryMinusSanityCheck() {
        val x: NonNegativeInteger =
            NonNegativeInteger.parse("99999999999999999999")
        val actual: NonPositiveInteger = -x
        val expected: NonPositiveInteger =
            NonPositiveInteger.parse("-99999999999999999999")
        assertEquals(expected, actual)
    }

    @Test
    fun plusIsCommutative(): Unit = repeatTest {
        val x: NonNegativeInteger = Random.nonNegativeInteger()
        val y: NonNegativeInteger = Random.nonNegativeInteger()
        val message = "Inputs: x = $x, y = $y"
        assertEquals(x + y, y + x, message)
    }

    @Test
    fun plusHasZeroAsIdentityElement(): Unit = repeatTest {
        val x: NonNegativeInteger = Random.nonNegativeInteger()
        val zero: NonNegativeInteger = NonNegativeInteger.fromLong(0)
        val message = "Input: $x"
        assertEquals(x, x + zero, message)
        assertEquals(x, zero + x, message)
    }

    @Test
    fun plusIsAssociative(): Unit = repeatTest {
        val x: NonNegativeInteger = Random.nonNegativeInteger()
        val y: NonNegativeInteger = Random.nonNegativeInteger()
        val z: NonNegativeInteger = Random.nonNegativeInteger()

        val actual: NonNegativeInteger = (x + y) + z

        val expected: NonNegativeInteger = x + (y + z)
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun plusIsAlwaysNonNegative(): Unit = repeatTest {
        val x: NonNegativeInteger = Random.nonNegativeInteger()
        val y: NonNegativeInteger = Random.nonNegativeInteger()

        val sum: NonNegativeInteger = x + y

        val actual: Boolean = sum.toInteger() >= Integer.ZERO
        assertTrue(actual, message = "Inputs: x = $x, y = $y")
    }

    @Test
    fun plusSanityCheck() {
        val x: NonNegativeInteger =
            NonNegativeInteger.parse("99999999999999999999")
        val y: NonNegativeInteger = NonNegativeInteger.parse("1")
        val actual: NonNegativeInteger = x + y
        val expected: NonNegativeInteger =
            NonNegativeInteger.parse("100000000000000000000")
        assertEquals(expected, actual)
    }

    @Test
    fun timesWithNonNegativeIntegerIsCommutative(): Unit = repeatTest {
        val x: NonNegativeInteger = Random.nonNegativeInteger()
        val y: NonNegativeInteger = Random.nonNegativeInteger()
        val message = "Inputs: x = $x, y = $y"
        assertEquals(x * y, y * x, message)
    }

    @Test
    fun timesWithNonNegativeIntegerHasOneAsIdentityElement(): Unit =
        repeatTest {
            val x: NonNegativeInteger = Random.nonNegativeInteger()
            val one: NonNegativeInteger = NonNegativeInteger.fromLong(1)
            val message = "Input: $x"
            assertEquals(x, x * one, message)
            assertEquals(x, one * x, message)
        }

    @Test
    fun timesWithNonNegativeIntegerIsAssociative(): Unit = repeatTest {
        val x: NonNegativeInteger = Random.nonNegativeInteger()
        val y: NonNegativeInteger = Random.nonNegativeInteger()
        val z: NonNegativeInteger = Random.nonNegativeInteger()

        val actual: NonNegativeInteger = (x * y) * z

        val expected: NonNegativeInteger = x * (y * z)
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun timesWithNonNegativeIntegerIsAlwaysNonNegative(): Unit = repeatTest {
        val x: NonNegativeInteger = Random.nonNegativeInteger()
        val y: NonNegativeInteger = Random.nonNegativeInteger()

        val product: NonNegativeInteger = x * y

        val actual: Boolean = product.toInteger() >= Integer.ZERO
        assertTrue(actual, message = "Inputs: x = $x, y = $y")
    }

    @Test
    fun timesWithNonNegativeIntegerSanityCheck() {
        val x: NonNegativeInteger =
            NonNegativeInteger.parse("99999999999999999999")
        val y: NonNegativeInteger = NonNegativeInteger.parse("10")
        val actual: NonNegativeInteger = x * y
        val expected: NonNegativeInteger =
            NonNegativeInteger.parse("999999999999999999990")
        assertEquals(expected, actual)
    }

    @Test
    fun timesWithNonPositiveIntegerIsAlwaysNonPositive(): Unit = repeatTest {
        val x: NonNegativeInteger = Random.nonNegativeInteger()
        val y: NonPositiveInteger = Random.nonPositiveInteger()

        val product: NonPositiveInteger = x * y

        val actual: Boolean = product.toInteger() <= Integer.ZERO
        assertTrue(actual, message = "Inputs: x = $x, y = $y")
    }

    @Test
    fun timesWithNonPositiveIntegerOnZero() {
        val x: NonNegativeInteger = Random.nonNegativeInteger()
        val zero: NonPositiveInteger = NonPositiveInteger.fromLong(0)
        val actual: NonPositiveInteger = x * zero
        val expected: NonPositiveInteger = NonPositiveInteger.fromLong(0)
        assertEquals(expected, actual, message = "Input: $x")
    }

    @Test
    fun timesWithNonPositiveIntegerSanityCheck() {
        val x: NonNegativeInteger =
            NonNegativeInteger.parse("99999999999999999999")
        val y: NonPositiveInteger = NonPositiveInteger.parse("-10")
        val actual: NonPositiveInteger = x * y
        val expected: NonPositiveInteger =
            NonPositiveInteger.parse("-999999999999999999990")
        assertEquals(expected, actual)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toIntegerRoundTripsWithFromInteger(): Unit = repeatTest {
        val integer: Integer = Random.nonNegativeInteger()
            .toInteger()

        val nonNegativeInteger: NonNegativeInteger =
            NonNegativeInteger.fromInteger(integer)
        val actual: Integer = nonNegativeInteger.toInteger()

        assertEquals(integer, actual, message = "Input: $integer")
    }

    @Test
    fun toStringDelegatesToInteger(): Unit = repeatTest {
        val integer: Integer = Random.nonNegativeInteger()
            .toInteger()

        val nonNegativeInteger: NonNegativeInteger =
            NonNegativeInteger.fromInteger(integer)

        val actual: String = nonNegativeInteger.toString()
        val expected: String = integer.toString()
        assertEquals(expected, actual, message = "Input: $integer")
    }
}
