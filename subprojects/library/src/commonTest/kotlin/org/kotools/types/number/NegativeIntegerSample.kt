package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class NegativeIntegerSample {
    // --------------------------- Factory functions ---------------------------

    @Test
    fun fromLong() {
        val result: NegativeInteger = NegativeInteger.fromLong(-42)
        check(result.toInteger() == Integer.fromLong(-42))

        val exception: Throwable? =
            runCatching { NegativeInteger.fromLong(0) }.exceptionOrNull()
        check(exception is IllegalArgumentException)

        val safeResult: NegativeInteger? = NegativeInteger.fromLongOrNull(0)
        check(safeResult == null)
    }

    @Test
    fun fromInteger() {
        val value: Integer = Integer.fromLong(-42)
        val result: NegativeInteger = NegativeInteger.fromInteger(value)
        check(result.toInteger() == value)

        val zero: Integer = Integer.fromLong(0)
        val exception: Throwable? = runCatching {
            NegativeInteger.fromInteger(zero)
        }.exceptionOrNull()
        check(exception is IllegalArgumentException)

        val safeResult: NegativeInteger? =
            NegativeInteger.fromIntegerOrNull(zero)
        check(safeResult == null)
    }

    @Test
    fun parsing() {
        val result: NegativeInteger = NegativeInteger.parse("-00042")
        check(result.toInteger() == Integer.fromLong(-42))

        val invalid: Throwable? =
            runCatching { NegativeInteger.parse("3.14") }.exceptionOrNull()
        check(invalid is NumberFormatException)

        val zero: Throwable? =
            runCatching { NegativeInteger.parse("0") }.exceptionOrNull()
        check(zero is IllegalArgumentException)

        check(NegativeInteger.parseOrNull("3.14") == null)
        check(NegativeInteger.parseOrNull("1") == null)
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEquality() {
        val x: NegativeInteger = NegativeInteger.fromLong(-42)
        val y: NegativeInteger = NegativeInteger.fromInteger(x.toInteger())
        check(x == y)
        check(x.hashCode() == y.hashCode())

        val z: NegativeInteger = NegativeInteger.fromLong(-7)
        check(x != z)
        check(x.hashCode() != z.hashCode())
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toNonPositiveInteger() {
        val number = -42L
        val x: NegativeInteger = NegativeInteger.fromLong(number)

        val result: NonPositiveInteger = x.toNonPositiveInteger()

        val expected: NonPositiveInteger = NonPositiveInteger.fromLong(number)
        check(result == expected)
    }

    @Test
    fun toNonZeroInteger() {
        val number = -42L
        val x: NegativeInteger = NegativeInteger.fromLong(number)

        val result: NonZeroInteger = x.toNonZeroInteger()

        val expected: NonZeroInteger = NonZeroInteger.fromLong(number)
        check(result == expected)
    }

    @Test
    fun toInteger() {
        val number = -42L
        val x: NegativeInteger = NegativeInteger.fromLong(number)

        val result: Integer = x.toInteger()

        val expected: Integer = Integer.fromLong(number)
        check(result == expected)
    }

    @Test
    fun toStringOverride() {
        val number = -42L
        val x: NegativeInteger = NegativeInteger.fromLong(number)

        val result: String = x.toString()

        check(result == "$number")
    }
}
