package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonPositiveIntegerSample {
    // --------------------------- Factory functions ---------------------------

    @Test
    fun fromLong() {
        val result: NonPositiveInteger = NonPositiveInteger.fromLong(-42)
        check(result.toInteger() == Integer.fromLong(-42))

        val exception: Throwable? =
            runCatching { NonPositiveInteger.fromLong(1) }.exceptionOrNull()
        check(exception is IllegalArgumentException)

        val safeResult: NonPositiveInteger? =
            NonPositiveInteger.fromLongOrNull(1)
        check(safeResult == null)
    }

    @Test
    fun fromInteger() {
        val value: Integer = Integer.fromLong(-42)
        val result: NonPositiveInteger = NonPositiveInteger.fromInteger(value)
        check(result.toInteger() == value)

        val positive: Integer = Integer.fromLong(1)
        val exception: Throwable? = runCatching {
            NonPositiveInteger.fromInteger(positive)
        }.exceptionOrNull()
        check(exception is IllegalArgumentException)

        val safeResult: NonPositiveInteger? =
            NonPositiveInteger.fromIntegerOrNull(positive)
        check(safeResult == null)
    }

    @Test
    fun parsing() {
        val result: NonPositiveInteger = NonPositiveInteger.parse("-00042")
        check(result.toInteger() == Integer.fromLong(-42))

        val invalid: Throwable? =
            runCatching { NonPositiveInteger.parse("3.14") }.exceptionOrNull()
        check(invalid is NumberFormatException)

        val positive: Throwable? =
            runCatching { NonPositiveInteger.parse("1") }.exceptionOrNull()
        check(positive is IllegalArgumentException)

        check(NonPositiveInteger.parseOrNull("3.14") == null)
        check(NonPositiveInteger.parseOrNull("1") == null)
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEquality() {
        val value: Integer = Integer.fromLong(-42)
        val x: NonPositiveInteger = NonPositiveInteger.fromInteger(value)
        val y: NonPositiveInteger = NonPositiveInteger.fromInteger(value)
        check(x == y)
        check(x.hashCode() == y.hashCode())

        val other: Integer = Integer.fromLong(-7)
        val z: NonPositiveInteger = NonPositiveInteger.fromInteger(other)
        check(x != z)
        check(x.hashCode() != z.hashCode())
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinus() {
        val x: NonPositiveInteger = NonPositiveInteger.fromLong(-42)
        val result: NonNegativeInteger = -x
        val expected: NonNegativeInteger = NonNegativeInteger.fromLong(42)
        check(result == expected)
    }

    @Test
    fun plus() {
        val x: NonPositiveInteger = NonPositiveInteger.fromLong(-42)
        val y: NonPositiveInteger = NonPositiveInteger.fromLong(-8)
        val result: NonPositiveInteger = x + y
        val expected: NonPositiveInteger = NonPositiveInteger.fromLong(-50)
        check(result == expected)
    }

    @Test
    fun minus() {
        val x: NonPositiveInteger = NonPositiveInteger.fromLong(-42)
        val y: NonNegativeInteger = NonNegativeInteger.fromLong(8)
        val result: NonPositiveInteger = x - y
        val expected: NonPositiveInteger = NonPositiveInteger.fromLong(-50)
        check(result == expected)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toInteger() {
        val value: Integer = Integer.fromLong(-42)
        val nonPositiveInteger: NonPositiveInteger =
            NonPositiveInteger.fromInteger(value)
        val result: Integer = nonPositiveInteger.toInteger()
        check(result == value)
    }

    @Test
    fun toStringOverride() {
        val value: Integer = Integer.fromLong(-42)
        val nonPositiveInteger: NonPositiveInteger =
            NonPositiveInteger.fromInteger(value)
        val result: String = nonPositiveInteger.toString()
        check(result == "-42")
    }
}
