package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.decimal
import org.kotools.types.decimalExcept
import org.kotools.types.internal.errorMessage
import org.kotools.types.nonDecimalString
import org.kotools.types.positiveDecimal
import org.kotools.types.repeatTest
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
class DecimalTest {
    // ------------------------------- Creations -------------------------------

    @Test
    fun ofPreservesLongRepresentation(): Unit = repeatTest {
        val value: Long = Random.nextLong()

        val actual: Decimal = Decimal.fromLong(value)

        val expected: String = value.toString()
        assertEquals(expected, actual = "$actual", message = "Input: $value")
    }

    @Test
    fun parsingNormalizesZero(): Unit = repeatTest {
        val zeroVariants: List<String> = listOf(
            "0", "+0", "-0", "00", "0.0", "-0.0", "+0.00"
        )
        val value: String = zeroVariants.random()

        val decimal: Decimal = Decimal.parse(value)
        val safeDecimal: Decimal? = Decimal.parseOrNull(value)

        val expected: Decimal = Decimal.fromLong(0)
        val message = "Input: \"$value\""
        assertEquals(expected, actual = decimal, message)
        assertEquals(expected, actual = safeDecimal, message)
    }

    @Test
    fun parsingRemovesPlusSign(): Unit = repeatTest {
        val value = "+${Random.nextLong(1, Long.MAX_VALUE)}"

        val decimal: Decimal = Decimal.parse(value)
        val safeDecimal: Decimal? = Decimal.parseOrNull(value)

        val actual: String = decimal.toString()
        assertFalse("\"$actual\" must not start with plus sign.") {
            actual.startsWith('+')
        }
        assertEquals(decimal, safeDecimal, message = "Input: \"$value\"")
    }

    @Test
    fun parsingRemovesLeadingZeros(): Unit = repeatTest {
        val value = "00${Random.nextLong(1, Long.MAX_VALUE)}"

        val decimal: Decimal = Decimal.parse(value)
        val safeDecimal: Decimal? = Decimal.parseOrNull(value)

        val actual: String = decimal.toString()
        assertTrue("\"$actual\" must not have leading zeros.") {
            actual.first(Char::isDigit) != '0'
        }
        assertEquals(decimal, safeDecimal, message = "Input: \"$value\"")
    }

    @Test
    fun parsingRemovesTrailingFractionalZeros(): Unit = repeatTest {
        val base: Long = Random.nextLong(1, 10_000_000)
        val value = "$base.10"

        val decimal: Decimal = Decimal.parse(value)

        val actual: String = decimal.toString()
        assertFalse("\"$actual\" must not have trailing fractional zeros.") {
            actual.endsWith('0')
        }
    }

    @Test
    fun parsingNormalizesFractionalOnlyZeros(): Unit =
        listOf("0.0", "-0.0", "+0.00", "00.000").forEach {
            val decimal: Decimal = Decimal.parse(it)

            val actual: String = decimal.toString()
            assertEquals(expected = "0", actual, message = "Input: \"$it\"")
        }

    @Test
    fun parsingPreservesCanonicalRepresentation(): Unit = repeatTest {
        val integerPart: Long = Random.nextLong()
        // Build a fractional part whose last digit is NOT 0, so it's already
        // canonical (no trailing zeros to strip during parse).
        val fractionalLength: Int = Random.nextInt(1, 10)
        val fracPart: String = buildString {
            repeat(fractionalLength - 1) { append((0..9).random()) }
            append((1..9).random())
        }
        val value = "${integerPart}.${fracPart}"

        val decimal: Decimal = Decimal.parse(value)
        val safeDecimal: Decimal? = Decimal.parseOrNull(value)

        val actual: String = decimal.toString()
        val message = "Input: \"$value\""
        assertEquals(value, actual, message)
        assertEquals(decimal, safeDecimal, message)
    }

    @Test
    fun parsingFailsWithNondecimalString(): Unit = repeatTest {
        val value: String = Random.nonDecimalString()

        val message: String = errorMessage("Input", value)
        val exception: NumberFormatException = assertFailsWith(message) {
            Decimal.parse(value)
        }
        val expected: String =
            errorMessage("Invalid decimal representation", value)
        assertEquals(expected, actual = exception.message)

        val safeDecimal: Decimal? = Decimal.parseOrNull(value)
        assertNull(safeDecimal, message)
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEqualityPassesWithSameDecimals(): Unit = repeatTest {
        val decimal: Decimal = Random.decimal()
        val other: Decimal = Decimal.parse("$decimal")

        val message = "Inputs: this = $decimal, other = $other"
        assertEquals(decimal, other, message)
        assertEquals(decimal.hashCode(), other.hashCode(), message)
    }

    @Test
    fun structuralEqualityIsReflexive(): Unit = repeatTest {
        val x: Decimal = Random.decimal()

        val message = "Input: $x"
        assertSame(x, x, message)
        assertEquals(x.hashCode(), x.hashCode(), message)
    }

    @Test
    fun structuralEqualityIsSymmetrical(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Decimal.parse("$x")

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
        val x: Decimal = Random.decimal()
        val y: Decimal = Decimal.parse("$x")
        val z: Decimal = Decimal.parse("$y")

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
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()

        val equalityConsistency: Boolean = (x == y) == (x == y)
        val hashConsistency: Boolean =
            (x.hashCode() == y.hashCode()) == (x.hashCode() == y.hashCode())
        val actual = equalityConsistency && hashConsistency

        assertTrue(actual, message = "Structural equality must be consistent.")
    }

    @Test
    fun structuralEqualityFailsWithDifferentDecimals(): Unit = repeatTest {
        val decimal: Decimal = Random.decimal()
        val other: Decimal = Random.decimalExcept(illegal = decimal)

        val message = "Inputs: this = $decimal, other = $other"
        assertNotEquals(decimal, other, message)
        assertNotEquals(decimal.hashCode(), other.hashCode(), message)
    }

    @Test
    fun structuralEqualityFailsWithDifferentTypes(): Unit = repeatTest {
        val decimal: Decimal = Random.decimal()
        val other: Any = decimal.toString()

        val message = "Inputs: this = $decimal, other = \"$other\""
        assertNotEquals(decimal, other, message)
        assertNotEquals(decimal.hashCode(), other.hashCode(), message)
    }

    @Test
    fun structuralEqualityFailsWithNull(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Any? = null

        val equality: Boolean = x == y
        val hashEquality: Boolean = x.hashCode() == y.hashCode()

        val message = "Structural equality must fail with null."
        assertFalse(equality, message)
        assertFalse(hashEquality, message)
    }

    @Test
    fun parseEquivalenceThroughScaleVariants() {
        val equivalents: List<String> = listOf("3.14", "+03.140", "3.14000")
        val first: Decimal = Decimal.parse(equivalents[0])
        for (value in equivalents) {
            val decimal: Decimal = Decimal.parse(value)
            assertEquals(
                first, decimal,
                message = "\"$value\" should equal \"${equivalents[0]}\""
            )
            assertEquals(first.hashCode(), decimal.hashCode())
        }
    }

    @Test
    fun compareToIsReflexive(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val actual: Int = x.compareTo(x)
        assertEquals(expected = 0, actual, message = "Input: $x")
    }

    @Test
    fun compareToIsAntiSymmetric(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()
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
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()
        val z: Decimal = Random.decimal()

        val xy: Int = x.compareTo(y)
        val yz: Int = y.compareTo(z)

        val message = "Inputs: x = $x, y = $y, z = $z"
        if (xy <= 0 && yz <= 0) assertTrue(x <= z, message)
        if (xy >= 0 && yz >= 0) assertTrue(x >= z, message)
    }

    @Test
    fun compareToIsConsistentWithEquals(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()

        val actual: Boolean = x.compareTo(y) == 0

        val expected: Boolean = x == y
        val message = "Inputs: x = $x, y = $y"
        assertEquals(expected, actual, message)
    }

    @Test
    fun compareToWithSmallerDecimal() {
        val x: Decimal = Decimal.parse("-99999999999999999999.99")
        val y: Decimal = Decimal.parse("99999999999999999999.99")
        val actual: Int = x.compareTo(y)
        assertTrue(actual < 0)
    }

    @Test
    fun compareToWithEqualDecimal() {
        val x: Decimal = Decimal.parse("3.14")
        val y: Decimal = Decimal.parse("3.140")
        val actual: Int = x.compareTo(y)
        assertEquals(expected = 0, actual)
    }

    @Test
    fun compareToWithLargerDecimal() {
        val x: Decimal = Decimal.parse("99999999999999999999.99")
        val y: Decimal = Decimal.parse("-99999999999999999999.99")
        val actual: Int = x.compareTo(y)
        assertTrue(actual > 0)
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinusIsInvolutory(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val actual: Decimal = -(-x)
        assertEquals(x, actual, message = "Input: $x")
    }

    @Test
    fun unaryMinusIsAdditiveInverse(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val zero: Decimal = Decimal.fromLong(0)

        val actual: Decimal = x + (-x)

        assertEquals(expected = zero, actual, message = "Input: $x")
    }

    @Test
    fun unaryMinusReversesComparison(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()

        val actual: Int = (-y).compareTo(-x)

        val expected: Int = x.compareTo(y)
        assertEquals(expected, actual, message = "Inputs: x = $x, y = $y")
    }

    @Test
    fun unaryMinusInversesSign(): Unit = repeatTest {
        val zero: Decimal = Decimal.fromLong(0)
        val x: Decimal = Random.decimalExcept(illegal = zero)

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
        val x: Decimal = Decimal.fromLong(0)
        val actual: Decimal = -x
        assertEquals(x, actual)
    }

    @Test
    fun unaryMinusOnPositiveDecimal() {
        val x: Decimal = Decimal.parse("3.14")
        val actual: Decimal = -x
        val expected: Decimal = Decimal.parse("-3.14")
        assertEquals(expected, actual)
    }

    @Test
    fun unaryMinusOnNegativeDecimal() {
        val x: Decimal = Decimal.parse("-3.14")
        val actual: Decimal = -x
        val expected: Decimal = Decimal.parse("3.14")
        assertEquals(expected, actual)
    }

    @Test
    fun plusIsCommutative(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()
        val message = "Inputs: x = $x, y = $y"
        assertEquals(x + y, y + x, message)
    }

    @Test
    fun plusHasZeroAsIdentityElement(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val zero: Decimal = Decimal.fromLong(0)
        val message = "Input: $x"
        assertEquals(x, x + zero, message)
        assertEquals(x, zero + x, message)
    }

    @Test
    fun plusIsAssociative(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()
        val z: Decimal = Random.decimal()

        val actual: Decimal = (x + y) + z

        val expected: Decimal = x + (y + z)
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun plusIsCompatibleWithOrdering(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()
        val z: Decimal = Random.decimal()

        val actual: Int = (x + z).compareTo(y + z)

        val expected: Int = x.compareTo(y)
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun plusHasUniqueInverseElement(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()
        val zero: Decimal = Decimal.fromLong(0)

        val actual: Boolean = x == -y

        val expected: Boolean = x + y == zero
        val message = "Inputs: x = $x, y = $y"
        assertEquals(expected, actual, message)
    }

    @Test
    fun plusSatisfiesCancellation(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()
        val z: Decimal = Random.decimal()

        val actual: Boolean = x + z == y + z

        val expected: Boolean = x == y
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun plusDistributesNegation(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()

        val actual: Decimal = -(x + y)

        val expected: Decimal = (-x) + (-y)
        val message = "Inputs: x = $x, y = $y"
        assertEquals(expected, actual, message)
    }

    @Test
    fun plusSanityCheck() {
        val x: Decimal = Decimal.parse("1.5")
        val y: Decimal = Decimal.parse("1.25")
        val actual: Decimal = x + y
        val expected: Decimal = Decimal.parse("2.75")
        assertEquals(expected, actual)
    }

    @Test
    fun plusAvoidingFloatingPointImprecision() {
        val x: Decimal = Decimal.parse("0.1")
        val y: Decimal = Decimal.parse("0.2")
        val actual: Decimal = x + y
        val expected: Decimal = Decimal.parse("0.3")
        assertEquals(expected, actual)
    }

    @Test
    fun minusHasZeroAsRightIdentityElement(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val zero: Decimal = Decimal.fromLong(0)
        val message = "Input: $x"
        assertEquals(x, x - zero, message)
    }

    @Test
    fun minusOfSameDecimalsIsZero(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val zero: Decimal = Decimal.fromLong(0)
        val message = "Input: $x"
        assertEquals(zero, x - x, message)
    }

    @Test
    fun minusIsAntiCommutative(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()

        val actual: Decimal = x - y

        val expected: Decimal = -(y - x)
        val message = "Inputs: x = $x, y = $y"
        assertEquals(expected, actual, message)
    }

    @Test
    fun minusIsConsistentWithPlus(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()

        val actual: Decimal = x - y

        val expected: Decimal = x + (-y)
        val message = "Inputs: x = $x, y = $y"
        assertEquals(expected, actual, message)
    }

    @Test
    fun minusIsCompatibleWithOrdering(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()
        val z: Decimal = Random.decimal()

        val actual: Int = (x - z).compareTo(y - z)

        val expected: Int = x.compareTo(y)
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun minusSanityCheck() {
        val x: Decimal = Decimal.parse("3.14")
        val y: Decimal = Decimal.parse("1.5")
        val actual: Decimal = x - y
        val expected: Decimal = Decimal.parse("1.64")
        assertEquals(expected, actual)
    }

    @Test
    fun timesIsCommutative(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()
        val message = "Inputs: x = $x, y = $y"
        assertEquals(x * y, y * x, message)
    }

    @Test
    fun timesHasOneAsIdentityElement(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val one: Decimal = Decimal.fromLong(1)
        val message = "Input: $x"
        assertEquals(x, x * one, message)
        assertEquals(x, one * x, message)
    }

    @Test
    fun timesIsAssociative(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()
        val z: Decimal = Random.decimal()

        val actual: Decimal = (x * y) * z

        val expected: Decimal = x * (y * z)
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun timesHasZeroAsAbsorbingElement(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val zero: Decimal = Decimal.fromLong(0)

        val message = "Input: $x"
        assertEquals(zero, x * zero, message)
        assertEquals(zero, zero * x, message)
    }

    @Test
    fun timesDistributesOverPlus(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()
        val z: Decimal = Random.decimal()

        val actual: Decimal = x * (y + z)

        val expected: Decimal = x * y + x * z
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun timesNegatesWithUnaryMinus(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()

        val actual: Decimal = (-x) * y

        val expected: Decimal = -(x * y)
        val message = "Inputs: x = $x, y = $y"
        assertEquals(expected, actual, message)
    }

    @Test
    fun timesHasRightDistributivityOverPlus(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()
        val z: Decimal = Random.decimal()

        val actual: Decimal = (x + y) * z

        val expected: Decimal = x * z + y * z
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun timesSatisfiesCancellation(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()
        val z: Decimal = Random.decimalExcept(illegal = Decimal.fromLong(0))

        val actual: Boolean = x * z == y * z

        val expected: Boolean = x == y
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun timesPreservesOrderForPositiveMultiplier(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()
        val z: Decimal = Random.positiveDecimal()

        val actual: Boolean = (x * z) <= y * z

        val expected: Boolean = x <= y
        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(expected, actual, message)
    }

    @Test
    fun timesPreservesOrderForNegativeMultiplier(): Unit = repeatTest {
        val x: Decimal = Random.decimal()
        val y: Decimal = Random.decimal()
        val z: Decimal = -Random.positiveDecimal()

        val message = "Inputs: x = $x, y = $y, z = $z"
        assertEquals(x <= y, (x * z) >= y * z, message)
    }

    @Test
    fun timesSanityCheck() {
        val x: Decimal = Decimal.parse("1.1")
        val y: Decimal = Decimal.parse("1.1")
        val actual: Decimal = x * y
        val expected: Decimal = Decimal.parse("1.21")
        assertEquals(expected, actual)
    }

    @Test
    fun timesStripsTrailingZerosInResult() {
        val x: Decimal = Decimal.parse("2.5")
        val y: Decimal = Decimal.parse("2")
        val actual: Decimal = x * y
        val expected: Decimal = Decimal.parse("5")
        assertEquals(expected, actual)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringReturnsCanonicalRepresentation() {
        fun checkToString(input: String, expected: String) {
            val actual: String = Decimal.parse(input).toString()
            assertEquals(expected, actual, message = "Input: \"$input\"")
        }

        // Zero variants
        checkToString("0", "0")
        checkToString("+0", "0")
        checkToString("-0", "0")
        checkToString("0.0", "0")
        checkToString("-0.0", "0")

        // Plain integers
        checkToString("42", "42")
        checkToString("-42", "-42")
        checkToString("+42", "42")

        // Trailing zero removal
        checkToString("3.10", "3.1")
        checkToString("5.00", "5")
        checkToString("-2.50", "-2.5")

        // Leading zero in fractional part preserved
        checkToString("0.001", "0.001")
        checkToString("-0.5", "-0.5")

        // Large values
        checkToString("99999999999999999999.99", "99999999999999999999.99")
    }

    @Test
    fun toStringIsConsistentWithParsing(): Unit = repeatTest {
        val x: Decimal = Random.decimal()

        val actual: Decimal = Decimal.parse(x.toString())

        assertEquals(expected = x, actual, message = "Input: $x")
    }
}
