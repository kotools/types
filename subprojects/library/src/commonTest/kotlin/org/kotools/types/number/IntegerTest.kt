package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.integer
import org.kotools.types.integerExcept
import org.kotools.types.nonIntegerString
import org.kotools.types.nonZeroIntegerStringWithLeadingZeros
import org.kotools.types.positiveIntegerString
import org.kotools.types.repeatTest
import org.kotools.types.zeroString
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerTest {
    // ------------------------------- Creations -------------------------------

    @Test
    fun ofPreservesLongRepresentation(): Unit = repeatTest {
        val value: Long = Random.nextLong()

        val integer: Integer = Integer.of(value)

        val actual: String = integer.toString()
        val expected: String = value.toString()
        assertEquals(expected, actual, message = "Input: $value")
    }

    @Test
    fun parsingNormalizesZero(): Unit = repeatTest {
        val value: String = Random.zeroString()

        val integer: Integer = Integer.parse(value)
        val safeInteger: Integer? = Integer.parseOrNull(value)

        val expected: Integer = Integer.of(0)
        val message = "Input: \"$value\""
        assertEquals(expected, actual = integer, message)
        assertEquals(expected, actual = safeInteger, message)
    }

    @Test
    fun parsingRemovesPlusSign(): Unit = repeatTest {
        val value: String = Random.positiveIntegerString()

        val integer: Integer = Integer.parse(value)
        val safeInteger: Integer? = Integer.parseOrNull(value)

        val actual: String = integer.toString()
        assertFalse(
            "\"$actual\" must not start with plus sign (input: \"$value\")."
        ) { actual.startsWith('+') }
        assertEquals(integer, safeInteger, message = "Input: \"$value\"")
    }

    @Test
    fun parsingRemovesLeadingZerosFromNonZeroInteger(): Unit = repeatTest {
        val value: String = Random.nonZeroIntegerStringWithLeadingZeros()

        val integer: Integer = Integer.parse(value)
        val safeInteger: Integer? = Integer.parseOrNull(value)

        val actual: String = integer.toString()
        assertTrue(
            "\"$actual\" must not have leading zeros (input: \"$value\")."
        ) { actual.first(Char::isDigit) != '0' }
        assertEquals(integer, safeInteger, message = "Input: \"$value\"")
    }

    @Test
    fun parsingPreservesCanonicalRepresentation(): Unit = repeatTest {
        val value: String = Random.nextLong()
            .toString()

        val integer: Integer = Integer.parse(value)
        val safeInteger: Integer? = Integer.parseOrNull(value)

        val actual: String = integer.toString()
        val message = "Input: \"$value\""
        assertEquals(expected = value, actual, message)
        assertEquals(integer, safeInteger, message)
    }

    @Test
    fun parsingFailsWithNonintegerString(): Unit = repeatTest {
        val value: String = Random.nonIntegerString()

        val message = "Input: \"$value\""
        val exception: NumberFormatException =
            assertFailsWith(message) { Integer.parse(value) }
        val expected = "\"$value\" is not a valid integer."
        assertEquals(expected, actual = exception.message, message)

        val safeInteger: Integer? = Integer.parseOrNull(value)
        assertNull(safeInteger, message)
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEqualityPassesWithSameIntegers(): Unit = repeatTest {
        val integer: Integer = Random.integer()
        val other: Integer = Integer.parse("$integer")

        val message = "Inputs: this = $integer, other = $other"
        assertEquals(integer, other, message)
        assertEquals(integer.hashCode(), other.hashCode(), message)
    }

    @Test
    fun structuralEqualityIsReflexive(): Unit = repeatTest {
        val x: Integer = Random.integer()

        val message = "Input: $x"
        assertSame(x, x, message)
        assertEquals(x.hashCode(), x.hashCode(), message)
    }

    @Test
    fun structuralEqualityIsSymmetrical(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Integer.parse("$x")

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
        val x: Integer = Random.integer()
        val y: Integer = Integer.parse("$x")
        val z: Integer = Integer.parse("$y")

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
    fun structuralEqualityIsConsistent(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()

        val equalityConsistency: Boolean = (x == y) == (x == y)
        val hashConsistency: Boolean =
            (x.hashCode() == y.hashCode()) == (x.hashCode() == y.hashCode())
        val actual = equalityConsistency && hashConsistency

        assertTrue(actual, message = "Structural equality must be consistent.")
    }

    @Test
    fun structuralEqualityFailsWithDifferentIntegers(): Unit = repeatTest {
        val integer: Integer = Random.integer()
        val other: Integer = Random.integerExcept(illegal = integer)

        val message = "Inputs: this = $integer, other = $other"
        assertNotEquals(integer, other, message)
        assertNotEquals(integer.hashCode(), other.hashCode(), message)
    }

    @Test
    fun structuralEqualityFailsWithDifferentTypes(): Unit = repeatTest {
        val integer: Integer = Random.integer()
        val other: Any = integer.toString()

        val message = "Inputs: this = $integer, other = \"$other\""
        assertNotEquals(integer, other, message)
        assertNotEquals(integer.hashCode(), other.hashCode(), message)
    }

    @Test
    fun structuralEqualityFailsWithNull(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Any? = null

        val equality: Boolean = x == y
        val hashEquality: Boolean = x.hashCode() == y.hashCode()

        val message = "Structural equality must fail with null."
        assertFalse(equality, message)
        assertFalse(hashEquality, message)
    }

    @Test
    fun compareToIsReflexive(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val actual: Int = x.compareTo(x)
        assertEquals(expected = 0, actual, message = "Input: $x")
    }

    @Test
    fun compareToIsAntiSymmetric(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()
        val xy: Int = x.compareTo(y)
        val yx: Int = y.compareTo(x)
        val message = "Inputs: x = $x, y = $y"
        when {
            xy < 0 -> assertTrue(yx > 0, message)
            xy > 0 -> assertTrue(yx < 0, message)
            else -> assertEquals(expected = 0, yx, message)
        }
    }

    @Test
    fun compareToWithSmallerInteger() {
        val x: Integer = Integer.parse("-99999999999999999999")
        val y: Integer = Integer.parse("99999999999999999999")
        val actual: Int = x.compareTo(y)
        assertTrue(actual < 0)
    }

    @Test
    fun compareToWithEqualInteger() {
        val x: Integer = Integer.parse("99999999999999999999")
        val y: Integer = Integer.parse("99999999999999999999")
        val actual: Int = x.compareTo(y)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareToWithLargerInteger() {
        val x: Integer = Integer.parse("99999999999999999999")
        val y: Integer = Integer.parse("-99999999999999999999")
        val actual: Int = x.compareTo(y)
        assertTrue(actual > 0)
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinusIsInvolutory(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val actual: Integer = -(-x)
        assertEquals(x, actual, message = "Input: $x")
    }

    @Test
    fun unaryMinusIsAdditiveInverse(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val zero: Integer = Integer.of(0)

        val actual: Integer = x + (-x)

        assertEquals(expected = zero, actual, message = "Input: $x")
    }

    @Test
    fun unaryMinusReversesComparison(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()

        val actual: Int = (-y).compareTo(-x)

        val expected: Int = x.compareTo(y)
        assertEquals(expected, actual, message = "Inputs: x = $x, y = $y")
    }

    @Test
    fun unaryMinusOnZero() {
        val x: Integer = Integer.of(0)
        val actual: Integer = -x
        assertEquals(x, actual)
    }

    @Test
    fun unaryMinusOnPositiveInteger() {
        val x: Integer = Integer.parse("99999999999999999999")
        val actual: Integer = -x
        val expected: Integer = Integer.parse("-99999999999999999999")
        assertEquals(expected, actual)
    }

    @Test
    fun unaryMinusOnNegativeInteger() {
        val x: Integer = Integer.parse("-99999999999999999999")
        val actual: Integer = -x
        val expected: Integer = Integer.parse("99999999999999999999")
        assertEquals(expected, actual)
    }

    @Test
    fun plusIsCommutative(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()
        val message = "Inputs: x = $x, y = $y"
        assertEquals(x + y, y + x, message)
    }

    @Test
    fun plusHasZeroAsIdentityElement(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val zero: Integer = Integer.of(0)
        val message = "Input: $x"
        assertEquals(x, x + zero, message)
        assertEquals(x, zero + x, message)
    }

    @Test
    fun plusSanityCheck() {
        val x: Integer = Integer.parse("99999999999999999999")
        val y: Integer = Integer.parse("1")
        val actual: Integer = x + y
        val expected: Integer = Integer.parse("100000000000000000000")
        assertEquals(expected, actual)
    }

    @Test
    fun minusHasZeroAsRightIdentityElement(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val zero: Integer = Integer.of(0)
        val message = "Input: $x"
        assertEquals(x, x - zero, message)
    }

    @Test
    fun minusOfSameIntegersIsZero(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val zero: Integer = Integer.of(0)
        val message = "Input: $x"
        assertEquals(zero, x - x, message)
    }

    @Test
    fun minusSanityCheck() {
        val x: Integer = Integer.parse("-99999999999999999999")
        val y: Integer = Integer.parse("1")
        val actual: Integer = x - y
        val expected: Integer = Integer.parse("-100000000000000000000")
        assertEquals(expected, actual)
    }

    @Test
    fun timesIsCommutative(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()
        val message = "Inputs: x = $x, y = $y"
        assertEquals(x * y, y * x, message)
    }

    @Test
    fun timesHasOneAsIdentityElement(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val one: Integer = Integer.of(1)
        val message = "Input: $x"
        assertEquals(x, x * one, message)
        assertEquals(x, one * x, message)
    }

    @Test
    fun timesSanityCheck() {
        val x: Integer = Integer.parse("99999999999999999999")
        val y: Integer = Integer.parse("10")
        val actual: Integer = x * y
        val expected: Integer = Integer.parse("999999999999999999990")
        assertEquals(expected, actual)
    }

    @Test
    fun divSanityCheck() {
        val x: Integer = Integer.parse("922337203685477580700")
        val y: Integer = Integer.of(10)

        val actual: Integer = x / y

        val expected: Integer = Integer.parse("92233720368547758070")
        assertEquals(expected, actual)
    }

    @Test
    fun divHasRightIdentity(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val one: Integer = Integer.of(1)

        val actual: Integer = x / one

        assertEquals(expected = x, actual, message = "Input: $x")
    }

    @Test
    fun divHasAbsorbingZeroDividend(): Unit = repeatTest {
        val zero: Integer = Integer.of(0)
        val other: Integer = Random.integerExcept(illegal = zero)

        val actual: Integer = zero / other

        assertEquals(expected = zero, actual, message = "Input: $other")
    }

    @Test
    fun divOfSameIntegerIsOne(): Unit = repeatTest {
        val x: Integer = Random.integerExcept(illegal = Integer.of(0))

        val actual: Integer = x / x

        val one: Integer = Integer.of(1)
        assertEquals(expected = one, actual, message = "Input: $x")
    }

    @Test
    fun divSatisfiesDivisionAlgorithm(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integerExcept(illegal = Integer.of(0))

        val quotient: Integer = x / y
        val remainder: Integer = x % y
        val actual: Integer = quotient * y + remainder

        assertEquals(expected = x, actual, message = "Inputs: x = $x, y = $y")
    }

    @Test
    fun divByZeroThrowsException(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val zero: Integer = Integer.of(0)

        val exception: ArithmeticException = assertFailsWith { x / zero }

        val expected = "Integer can't be divided by zero."
        val message = "Input: $x"
        assertEquals(expected, actual = exception.message, message)
    }

    @Test
    fun divOrNullSanityCheck() {
        val x: Integer = Integer.parse("922337203685477580700")
        val y: Integer = Integer.of(10)

        val actual: Integer? = x.divOrNull(y)

        val expected: Integer = Integer.parse("92233720368547758070")
        assertEquals(expected, actual)
    }

    @Test
    fun divOrNullHasRightIdentity(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val one: Integer = Integer.of(1)

        val actual: Integer? = x.divOrNull(one)

        assertEquals(expected = x, actual, message = "Input: $x")
    }

    @Test
    fun divOrNullHasAbsorbingZeroDividend(): Unit = repeatTest {
        val zero: Integer = Integer.of(0)
        val other: Integer = Random.integerExcept(illegal = zero)

        val actual: Integer? = zero.divOrNull(other)

        assertEquals(expected = zero, actual, message = "Input: $other")
    }

    @Test
    fun divOrNullIsConsistentWithDiv(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integerExcept(illegal = Integer.of(0))

        val actual: Integer? = x.divOrNull(y)

        val expected: Integer = x / y
        assertEquals(expected, actual, message = "Inputs: x = $x, y = $y")
    }

    @Test
    fun divOrNullWithZero(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Integer.of(0)

        val actual: Integer? = x.divOrNull(y)

        assertNull(actual)
    }

    @Test
    fun remSanityCheck() {
        val x: Integer = Integer.of(42)
        val y: Integer = Integer.of(5)

        val actual: Integer = x % y

        val expected: Integer = Integer.of(2)
        assertEquals(expected, actual)
    }

    @Test
    fun remByZeroThrowsException(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Integer.of(0)

        val exception: ArithmeticException = assertFailsWith { x % y }

        val expected = "Integer can't be divided by zero."
        val message = "Input: $x"
        assertEquals(expected, actual = exception.message, message)
    }

    @Test
    fun remOrNullSanityCheck() {
        val x: Integer = Integer.of(42)
        val y: Integer = Integer.of(5)

        val actual: Integer? = x.remOrNull(y)

        val expected: Integer = Integer.of(2)
        assertEquals(expected, actual)
    }

    @Test
    fun remOrNullWithZeroReturnsNull(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Integer.of(0)

        val actual: Integer? = x.remOrNull(y)

        assertNull(actual)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringReturnsCanonicalDecimalString(): Unit = repeatTest {
        val integer: Integer = Random.integer()

        val actual: String = integer.toString()

        assertTrue("\"$actual\" must not have leading plus sign (+).") {
            !actual.startsWith('+')
        }
        if (integer == Integer.of(0)) assertEquals(expected = "0", actual)
        else assertTrue("\"$actual\" must not have leading zeros.") {
            actual.first(Char::isDigit) != '0'
        }
    }
}
