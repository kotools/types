package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.integerExcept
import org.kotools.types.internal.errorMessage
import org.kotools.types.nonIntegerString
import org.kotools.types.nonZeroInteger
import org.kotools.types.nonZeroIntegerExcept
import org.kotools.types.nonZeroIntegerStringWithLeadingZeros
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

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEqualityPassesWithSameValues(): Unit = repeatTest {
        val nonZeroInteger: NonZeroInteger = Random.nonZeroInteger()
        val other: NonZeroInteger =
            NonZeroInteger.parse("$nonZeroInteger")

        val message = "Inputs: this = $nonZeroInteger, other = $other"
        assertEquals(nonZeroInteger, other, message)
        assertEquals(nonZeroInteger.hashCode(), other.hashCode(), message)
    }

    @Test
    fun structuralEqualityIsReflexive(): Unit = repeatTest {
        val x: NonZeroInteger = Random.nonZeroInteger()

        val message = "Input: $x"
        assertSame(x, x, message)
        assertEquals(x.hashCode(), x.hashCode(), message)
    }

    @Test
    fun structuralEqualityIsSymmetrical(): Unit = repeatTest {
        val x: NonZeroInteger = Random.nonZeroInteger()
        val y: NonZeroInteger = NonZeroInteger.parse("$x")

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
        val x: NonZeroInteger = Random.nonZeroInteger()
        val y: NonZeroInteger = NonZeroInteger.parse("$x")
        val z: NonZeroInteger = NonZeroInteger.parse("$y")

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
        val nonZeroInteger: NonZeroInteger = Random.nonZeroInteger()
        val other: NonZeroInteger =
            Random.nonZeroIntegerExcept(illegal = nonZeroInteger)

        val message = "Inputs: this = $nonZeroInteger, other = $other"
        assertNotEquals(nonZeroInteger, other, message)
        assertNotEquals(nonZeroInteger.hashCode(), other.hashCode(), message)
    }

    @Test
    fun structuralEqualityFailsWithDifferentTypes(): Unit = repeatTest {
        val nonZeroInteger: NonZeroInteger = Random.nonZeroInteger()
        val other: Any = nonZeroInteger.toString()

        val message = "Inputs: this = $nonZeroInteger, other = \"$other\""
        assertNotEquals(nonZeroInteger, other, message)
        assertNotEquals(nonZeroInteger.hashCode(), other.hashCode(), message)
    }

    @Test
    fun structuralEqualityFailsWithNull(): Unit = repeatTest {
        val x: NonZeroInteger = Random.nonZeroInteger()
        val y: Any? = null

        val equality: Boolean = x == y
        val hashEquality: Boolean = x.hashCode() == y.hashCode()

        val message = "Structural equality must fail with null."
        assertFalse(equality, message)
        assertFalse(hashEquality, message)
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinusIsInvolutory(): Unit = repeatTest {
        val x: NonZeroInteger = Random.nonZeroInteger()
        val actual: NonZeroInteger = -(-x)
        assertEquals(x, actual, message = "Input: $x")
    }

    @Test
    fun unaryMinusInversesSign(): Unit = repeatTest {
        val zero: Integer = Integer.of(0)
        val x: NonZeroInteger = Random.nonZeroInteger()

        val xSign: Int = x.toInteger()
            .compareTo(zero)
        val negXSign: Int = (-x).toInteger()
            .compareTo(zero)

        val message = "Input: $x"
        when {
            xSign > 0 -> assertTrue(negXSign < 0, message)
            else -> assertTrue(negXSign > 0, message)
        }
    }

    @Test
    fun unaryMinusOnPositiveInteger() {
        val x: NonZeroInteger = NonZeroInteger.parse("99999999999999999999")
        val actual: NonZeroInteger = -x
        val expected: NonZeroInteger =
            NonZeroInteger.parse("-99999999999999999999")
        assertEquals(expected, actual)
    }

    @Test
    fun unaryMinusOnNegativeInteger() {
        val x: NonZeroInteger = NonZeroInteger.parse("-99999999999999999999")
        val actual: NonZeroInteger = -x
        val expected: NonZeroInteger =
            NonZeroInteger.parse("99999999999999999999")
        assertEquals(expected, actual)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toIntegerRoundTripsWithFromInteger(): Unit = repeatTest {
        val integer: Integer = Random.integerExcept(illegal = Integer.of(0))

        val nonZeroInteger: NonZeroInteger = NonZeroInteger.fromInteger(integer)
        val actual: Integer = nonZeroInteger.toInteger()

        assertEquals(integer, actual, message = "Input: $integer")
    }

    @Test
    fun toStringDelegatesToInteger(): Unit = repeatTest {
        val integer: Integer = Random.integerExcept(illegal = Integer.of(0))

        val nonZeroInteger: NonZeroInteger = NonZeroInteger.fromInteger(integer)

        val actual: String = nonZeroInteger.toString()
        val expected: String = integer.toString()
        assertEquals(expected, actual, message = "Input: $integer")
    }
}
