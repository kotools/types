package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonNegativeIntegerSample {
    // --------------------------- Factory functions ---------------------------

    @Test
    fun fromLong() {
        val result: NonNegativeInteger = NonNegativeInteger.fromLong(42)
        check("$result" == "42")

        val exception: Throwable? =
            runCatching { NonNegativeInteger.fromLong(-1) }.exceptionOrNull()
        check(exception is IllegalArgumentException)

        val safeResult: NonNegativeInteger? =
            NonNegativeInteger.fromLongOrNull(-1)
        check(safeResult == null)
    }

    @Test
    fun fromInteger() {
        val value: Integer = Integer.fromLong(42)
        val result: NonNegativeInteger = NonNegativeInteger.fromInteger(value)
        check("$result" == "42")

        val negative: Integer = Integer.fromLong(-1)
        val exception: Throwable? = runCatching {
            NonNegativeInteger.fromInteger(negative)
        }.exceptionOrNull()
        check(exception is IllegalArgumentException)

        val safeResult: NonNegativeInteger? =
            NonNegativeInteger.fromIntegerOrNull(negative)
        check(safeResult == null)
    }

    @Test
    fun parsing() {
        val result: NonNegativeInteger = NonNegativeInteger.parse("+00042")
        check("$result" == "42")

        val invalid: Throwable? =
            runCatching { NonNegativeInteger.parse("3.14") }.exceptionOrNull()
        check(invalid is NumberFormatException)

        val negative: Throwable? =
            runCatching { NonNegativeInteger.parse("-1") }.exceptionOrNull()
        check(negative is IllegalArgumentException)

        check(NonNegativeInteger.parseOrNull("3.14") == null)
        check(NonNegativeInteger.parseOrNull("-1") == null)
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEquality() {
        val value: Integer = Integer.fromLong(42)
        val x: NonNegativeInteger = NonNegativeInteger.fromInteger(value)
        val y: NonNegativeInteger = NonNegativeInteger.fromInteger(value)
        check(x == y)
        check(x.hashCode() == y.hashCode())

        val other: Integer = Integer.fromLong(7)
        val z: NonNegativeInteger = NonNegativeInteger.fromInteger(other)
        check(x != z)
        check(x.hashCode() != z.hashCode())
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinus() {
        val x: NonNegativeInteger = NonNegativeInteger.fromLong(42)
        val result: NonPositiveInteger = -x
        val expected: NonPositiveInteger = NonPositiveInteger.fromLong(-42)
        check(result == expected)

        val zero: NonNegativeInteger = NonNegativeInteger.fromLong(0)
        check(-zero == NonPositiveInteger.fromLong(0))
    }

    @Test
    fun plus() {
        val x: NonNegativeInteger = NonNegativeInteger.fromLong(42)
        val y: NonNegativeInteger = NonNegativeInteger.fromLong(8)
        val result: NonNegativeInteger = x + y
        val expected: NonNegativeInteger = NonNegativeInteger.fromLong(50)
        check(result == expected)
    }

    @Test
    fun minus() {
        val x: NonNegativeInteger = NonNegativeInteger.fromLong(42)
        val y: NonPositiveInteger = NonPositiveInteger.fromLong(-8)

        val result: NonNegativeInteger = x - y

        val expected: NonNegativeInteger = NonNegativeInteger.fromLong(50)
        check(result == expected)
    }

    @Test
    fun timesWithNonNegativeInteger() {
        val x: NonNegativeInteger =
            NonNegativeInteger.fromLong(99999999999999999)
        val y: NonNegativeInteger = NonNegativeInteger.fromLong(10)
        val result: NonNegativeInteger = x * y
        val expected: NonNegativeInteger =
            NonNegativeInteger.parse("999999999999999990")
        check(result == expected)
    }

    @Test
    fun timesWithNonPositiveInteger() {
        val x: NonNegativeInteger =
            NonNegativeInteger.fromLong(99999999999999999)
        val y: NonPositiveInteger = NonPositiveInteger.fromLong(-10)
        val result: NonPositiveInteger = x * y
        val expected: NonPositiveInteger =
            NonPositiveInteger.parse("-999999999999999990")
        check(result == expected)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toInteger() {
        val value: Integer = Integer.fromLong(42)
        val nonNegativeInteger: NonNegativeInteger =
            NonNegativeInteger.fromInteger(value)
        val result: Integer = nonNegativeInteger.toInteger()
        check(result == value)
    }

    @Test
    fun toStringOverride() {
        val value: Integer = Integer.fromLong(42)
        val nonNegativeInteger: NonNegativeInteger =
            NonNegativeInteger.fromInteger(value)
        val result: String = nonNegativeInteger.toString()
        check(result == "42")
    }
}
