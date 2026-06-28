package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.integer
import org.kotools.types.integerExcept
import org.kotools.types.internal.errorMessage
import org.kotools.types.nonIntegerString
import org.kotools.types.nonZeroInteger
import org.kotools.types.nonZeroIntegerStringWithLeadingZeros
import org.kotools.types.positiveInteger
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
    fun fromLongPreservesLongRepresentation(): Unit = repeatTest {
        val value: Long = Random.nextLong()

        val integer: Integer = Integer.fromLong(value)

        val actual: String = integer.toString()
        val expected: String = value.toString()
        assertEquals(expected, actual, message = "Input: $value")
    }

    @Test
    fun parsingNormalizesZero(): Unit = repeatTest {
        val value: String = Random.zeroString()

        val integer: Integer = Integer.parse(value)
        val safeInteger: Integer? = Integer.parseOrNull(value)

        val expected: Integer = Integer.ZERO
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

        val message: String = errorMessage("Input", value)
        val exception: NumberFormatException = assertFailsWith(message) {
            Integer.parse(value)
        }
        val expected: String =
            errorMessage("Invalid integer representation", value)
        assertEquals(expected, actual = exception.message)

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
    fun compareToIsTransitive(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()
        val z: Integer = Random.integer()

        val xy: Int = x.compareTo(y)
        val yz: Int = y.compareTo(z)

        val message = "Inputs: x = $x, y = $y, z = $z"
        if (xy <= 0 && yz <= 0) assertTrue(x <= z, message)
        if (xy >= 0 && yz >= 0) assertTrue(x >= z, message)
    }

    @Test
    fun compareToIsConsistentWithEquals(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()

        val actual: Boolean = x.compareTo(y) == 0

        val expected: Boolean = x == y
        val message = "Inputs: x = $x, y = $y"
        assertEquals(expected, actual, message)
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

        val actual: Integer = x + (-x)

        assertEquals(expected = Integer.ZERO, actual, message = "Input: $x")
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
    fun unaryMinusInversesSign(): Unit = repeatTest {
        val zero: Integer = Integer.ZERO
        val x: Integer = Random.integerExcept(illegal = zero)

        val xSign: Int = x.compareTo(zero)
        val negXSign: Int = (-x).compareTo(zero)

        val message = "Input: $x"
        when {
            xSign > 0 -> assertTrue(negXSign < 0, message)
            else -> assertTrue(negXSign > 0, message)
        }
    }

    @Test
    fun unaryMinusOnZero() {
        val x: Integer = Integer.ZERO

        val actual: Integer = -x

        assertEquals(expected = x, actual)
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
        val zero: Integer = Integer.ZERO

        val actual: Integer = x + zero

        val message = "Input: $x"
        assertEquals(expected = x, actual, message)
        assertEquals(expected = zero + x, actual, message)
    }

    @Test
    fun plusIsAssociative(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()
        val z: Integer = Random.integer()

        val actual: Integer = (x + y) + z

        val expected: Integer = x + (y + z)
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun plusIsCompatibleWithOrdering(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()
        val z: Integer = Random.integer()

        val actual: Int = (x + z).compareTo(y + z)

        val expected: Int = x.compareTo(y)
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun plusHasUniqueInverseElement(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()

        val actual: Boolean = x == -y

        val expected: Boolean = x + y == Integer.ZERO
        val message = "Inputs: x = $x, y = $y"
        assertEquals(expected, actual, message)
    }

    @Test
    fun plusSatisfiesCancellation(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()
        val z: Integer = Random.integer()

        val actual: Boolean = x + z == y + z

        val expected: Boolean = x == y
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun plusDistributesNegation(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()

        val actual: Integer = -(x + y)

        val expected: Integer = (-x) + (-y)
        val message = "Inputs: x = $x, y = $y"
        assertEquals(expected, actual, message)
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
        val zero: Integer = Integer.ZERO

        val actual: Integer = x - zero

        assertEquals(expected = x, actual, message = "Input: $x")
    }

    @Test
    fun minusOfSameIntegersIsZero(): Unit = repeatTest {
        val x: Integer = Random.integer()

        val actual: Integer = x - x

        assertEquals(expected = Integer.ZERO, actual, message = "Input: $x")
    }

    @Test
    fun minusIsAntiCommutative(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()

        val actual: Integer = x - y

        val expected: Integer = -(y - x)
        val message = "Inputs: x = $x, y = $y"
        assertEquals(expected, actual, message)
    }

    @Test
    fun minusIsConsistentWithPlus(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()

        val actual: Integer = x - y

        val expected: Integer = x + (-y)
        val message = "Inputs: x = $x, y = $y"
        assertEquals(expected, actual, message)
    }

    @Test
    fun minusIsCompatibleWithOrdering(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()
        val z: Integer = Random.integer()

        val actual: Int = (x - z).compareTo(y - z)

        val expected: Int = x.compareTo(y)
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
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
        val one: Integer = Integer.fromLong(1)
        val message = "Input: $x"
        assertEquals(x, x * one, message)
        assertEquals(x, one * x, message)
    }

    @Test
    fun timesIsAssociative(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()
        val z: Integer = Random.integer()

        val actual: Integer = (x * y) * z

        val expected: Integer = x * (y * z)
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun timesHasZeroAsAbsorbingElement(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val zero: Integer = Integer.ZERO

        val message = "Input: $x"
        assertEquals(expected = zero, actual = x * zero, message)
        assertEquals(expected = zero, actual = zero * x, message)
    }

    @Test
    fun timesDistributesOverPlus(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()
        val z: Integer = Random.integer()

        val actual: Integer = x * (y + z)

        val expected: Integer = x * y + x * z
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun timesNegatesWithUnaryMinus(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()

        val actual: Integer = (-x) * y

        val expected: Integer = -(x * y)
        val message = "Inputs: x = $x, y = $y"
        assertEquals(expected, actual, message)
    }

    @Test
    fun timesHasRightDistributivityOverPlus(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()
        val z: Integer = Random.integer()

        val actual: Integer = (x + y) * z

        val expected: Integer = x * z + y * z
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun timesSatisfiesCancellation(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()
        val z: Integer = Random.integerExcept(illegal = Integer.ZERO)

        val actual: Boolean = x * z == y * z

        val expected: Boolean = x == y
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun timesPreservesOrderForPositiveMultiplier(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()
        val z: Integer = Random.positiveInteger()

        val actual: Boolean = (x * z) <= y * z

        val expected: Boolean = x <= y
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun timesPreservesOrderForNegativeMultiplier(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: Integer = Random.integer()
        val z: Integer = -Random.positiveInteger()

        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(x <= y, (x * z) >= y * z, message)
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
    fun divByNonZeroIntegerSanityCheck() {
        val x: Integer = Integer.parse("922337203685477580700")
        val y: NonZeroInteger = NonZeroInteger.fromLong(10)

        val actual: Integer = x / y

        val expected: Integer = Integer.parse("92233720368547758070")
        assertEquals(expected, actual)
    }

    @Test
    fun divByNonZeroIntegerHasRightIdentity(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val one: NonZeroInteger = NonZeroInteger.fromLong(1)

        val actual: Integer = x / one

        assertEquals(expected = x, actual, message = "Input: $x")
    }

    @Test
    fun divByNonZeroIntegerSatisfiesDivisionAlgorithm(): Unit = repeatTest {
        val x: Integer = Random.integer()
        val y: NonZeroInteger = Random.nonZeroInteger()

        val quotient: Integer = x / y
        val remainder: NonNegativeInteger = x % y
        val actual: Integer = quotient * y.toInteger() + remainder.toInteger()

        assertEquals(expected = x, actual, message = "Inputs: x = $x, y = $y")
    }

    @Test
    fun remByNonZeroIntegerSanityCheck() {
        val x: Integer = Integer.fromLong(42)
        val y: NonZeroInteger = NonZeroInteger.fromLong(5)

        val actual: NonNegativeInteger = x % y

        val expected: NonNegativeInteger = NonNegativeInteger.fromLong(2)
        assertEquals(expected, actual)
    }

    @Test
    fun remByNonZeroIntegerIsBoundedByAbsoluteValueOfDivisor(): Unit =
        repeatTest {
            val x: Integer = Random.integer()
            val y: NonZeroInteger = Random.nonZeroInteger()

            val remainder: NonNegativeInteger = x % y

            val divisor: Integer = y.toInteger()
            val absDivisor: Integer =
                if (divisor > Integer.ZERO) divisor else -divisor
            val message = "Inputs: x = $x, y = $y"
            assertTrue(remainder.toInteger() < absDivisor, message)
        }

    @Test
    fun divAndRemByNonZeroIntegerSatisfyDivisionAlgorithm(): Unit =
        repeatTest {
            val x: Integer = Random.integer()
            val y: NonZeroInteger = Random.nonZeroInteger()

            val quotient: Integer = x / y
            val remainder: NonNegativeInteger = x % y
            val actual: Integer =
                quotient * y.toInteger() + remainder.toInteger()

            val message = "Inputs: x = $x, y = $y"
            assertEquals(expected = x, actual, message)
        }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toLongIsConsistentWithFromLong(): Unit = repeatTest {
        val value: Long = Random.nextLong()
        val integer: Integer = Integer.fromLong(value)

        val actual: Long = integer.toLong()

        assertEquals(expected = value, actual, message = "Input: $integer")
    }

    @Test
    fun toLongThrowsExceptionWhenOutOfRange() {
        val aboveMax: Integer =
            Integer.fromLong(Long.MAX_VALUE) + Integer.fromLong(1)
        val belowMin: Integer =
            Integer.fromLong(Long.MIN_VALUE) - Integer.fromLong(1)
        listOf(aboveMax, belowMin).forEach { integer: Integer ->
            val exception: ArithmeticException = assertFailsWith {
                integer.toLong()
            }
            val expected: String =
                errorMessage("Integer out of range for Long", integer)
            val message = "Input: $integer"
            assertEquals(expected, actual = exception.message, message)
        }
    }

    @Test
    fun toLongOrNullIsConsistentWithToLong(): Unit = repeatTest {
        val value: Long = Random.nextLong()
        val integer: Integer = Integer.fromLong(value)

        val actual: Long? = integer.toLongOrNull()

        val expected: Long = integer.toLong()
        assertEquals(expected, actual, message = "Input: $integer")
    }

    @Test
    fun toLongOrNullReturnsNullWhenOutOfRange() {
        val aboveMax: Integer =
            Integer.fromLong(Long.MAX_VALUE) + Integer.fromLong(1)
        val belowMin: Integer =
            Integer.fromLong(Long.MIN_VALUE) - Integer.fromLong(1)
        listOf(aboveMax, belowMin).forEach { integer: Integer ->
            val actual: Long? = integer.toLongOrNull()
            assertNull(actual, message = "Input: $integer")
        }
    }

    @Test
    fun toStringReturnsCanonicalDecimalString(): Unit = repeatTest {
        val integer: Integer = Random.integer()

        val actual: String = integer.toString()

        assertTrue("\"$actual\" must not have leading plus sign (+).") {
            !actual.startsWith('+')
        }
        if (integer == Integer.ZERO) assertEquals(expected = "0", actual)
        else assertTrue("\"$actual\" must not have leading zeros.") {
            actual.first(Char::isDigit) != '0'
        }
    }

    @Test
    fun toStringIsConsistentWithParsing(): Unit = repeatTest {
        val x: Integer = Random.integer()

        val actual: Integer = Integer.parse(x.toString())

        assertEquals(expected = x, actual, message = "Input: $x")
    }

    @Test
    fun parseAndToStringWithNineDigitInteger() {
        val value = "123456789"
        val actual: String = Integer.parse(value).toString()
        assertEquals(expected = value, actual)
    }

    @Test
    fun parseAndToStringWithTenDigitInteger() {
        val value = "1234567890"
        val actual: String = Integer.parse(value).toString()
        assertEquals(expected = value, actual)
    }

    @Test
    fun parseAndToStringWithEighteenDigitInteger() {
        val value = "123456789123456789"
        val actual: String = Integer.parse(value).toString()
        assertEquals(expected = value, actual)
    }

    @Test
    fun parseAndToStringWithLargeNegativeInteger() {
        val value = "-99999999999999999999"
        val actual: String = Integer.parse(value).toString()
        assertEquals(expected = value, actual)
    }
}
