package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class PositiveIntegerSample {
    // --------------------------- Factory functions ---------------------------

    @Test
    fun fromLong() {
        val result: PositiveInteger = PositiveInteger.fromLong(42)
        check(result.toInteger() == Integer.fromLong(42))

        val exception: Throwable? =
            runCatching { PositiveInteger.fromLong(0) }.exceptionOrNull()
        check(exception is IllegalArgumentException)

        val safeResult: PositiveInteger? = PositiveInteger.fromLongOrNull(0)
        check(safeResult == null)
    }

    @Test
    fun fromInteger() {
        val value: Integer = Integer.fromLong(42)
        val result: PositiveInteger = PositiveInteger.fromInteger(value)
        check(result.toInteger() == value)

        val zero: Integer = Integer.fromLong(0)
        val exception: Throwable? = runCatching {
            PositiveInteger.fromInteger(zero)
        }.exceptionOrNull()
        check(exception is IllegalArgumentException)

        val safeResult: PositiveInteger? =
            PositiveInteger.fromIntegerOrNull(zero)
        check(safeResult == null)
    }

    @Test
    fun parsing() {
        val result: PositiveInteger = PositiveInteger.parse("+00042")
        check(result.toInteger() == Integer.fromLong(42))

        val invalid: Throwable? =
            runCatching { PositiveInteger.parse("3.14") }.exceptionOrNull()
        check(invalid is NumberFormatException)

        val zero: Throwable? =
            runCatching { PositiveInteger.parse("0") }.exceptionOrNull()
        check(zero is IllegalArgumentException)

        check(PositiveInteger.parseOrNull("3.14") == null)
        check(PositiveInteger.parseOrNull("-1") == null)
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEquality() {
        val value: Integer = Integer.fromLong(42)
        val x: PositiveInteger = PositiveInteger.fromInteger(value)
        val y: PositiveInteger = PositiveInteger.fromInteger(value)
        check(x == y)
        check(x.hashCode() == y.hashCode())

        val other: Integer = Integer.fromLong(7)
        val z: PositiveInteger = PositiveInteger.fromInteger(other)
        check(x != z)
        check(x.hashCode() != z.hashCode())
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toNonNegativeInteger() {
        val positiveInteger: PositiveInteger = PositiveInteger.fromLong(42)
        val result: NonNegativeInteger = positiveInteger.toNonNegativeInteger()
        check(result == NonNegativeInteger.fromLong(42))
    }

    @Test
    fun toNonZeroInteger() {
        val positiveInteger: PositiveInteger = PositiveInteger.fromLong(42)
        val result: NonZeroInteger = positiveInteger.toNonZeroInteger()
        check(result == NonZeroInteger.fromLong(42))
    }

    @Test
    fun toInteger() {
        val value: Integer = Integer.fromLong(42)
        val positiveInteger: PositiveInteger =
            PositiveInteger.fromInteger(value)
        val result: Integer = positiveInteger.toInteger()
        check(result == value)
    }

    @Test
    fun toStringOverride() {
        val value: Integer = Integer.fromLong(42)
        val positiveInteger: PositiveInteger =
            PositiveInteger.fromInteger(value)
        val result: String = positiveInteger.toString()
        check(result == "42")
    }
}
