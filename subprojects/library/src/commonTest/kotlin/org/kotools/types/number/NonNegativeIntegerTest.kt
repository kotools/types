package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import org.kotools.types.internal.errorMessage
import org.kotools.types.nonNegativeInteger
import org.kotools.types.nonNegativeIntegerExcept
import org.kotools.types.positiveInteger
import org.kotools.types.repeatTest
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonNegativeIntegerTest {
    // --------------------------- Factory functions ---------------------------

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
