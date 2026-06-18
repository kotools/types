package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonZeroIntegerSample {
    // ------------------------------- Creations -------------------------------

    @Test
    fun fromLong() {
        val result: NonZeroInteger = NonZeroInteger.fromLong(42)
        check("$result" == "42")

        val exception: Throwable? =
            runCatching { NonZeroInteger.fromLong(0) }.exceptionOrNull()
        check(exception is IllegalArgumentException)

        val safeResult: NonZeroInteger? = NonZeroInteger.fromLongOrNull(0)
        check(safeResult == null)
    }

    @Test
    fun parsing() {
        val result: NonZeroInteger = NonZeroInteger.parse("+00042")
        check("$result" == "42")

        val invalid: Throwable? =
            runCatching { NonZeroInteger.parse("3.14") }.exceptionOrNull()
        check(invalid is NumberFormatException)

        val zero: Throwable? =
            runCatching { NonZeroInteger.parse("0") }.exceptionOrNull()
        check(zero is IllegalArgumentException)

        check(NonZeroInteger.parseOrNull("3.14") == null)
        check(NonZeroInteger.parseOrNull("0") == null)
    }

    @Test
    fun fromInteger() {
        val value: Integer = Integer.of(42)
        val result: NonZeroInteger = NonZeroInteger.fromInteger(value)
        check("$result" == "42")

        val zero: Integer = Integer.of(0)
        val exception: Throwable? =
            runCatching { NonZeroInteger.fromInteger(zero) }.exceptionOrNull()
        check(exception is IllegalArgumentException)

        val safeResult: NonZeroInteger? =
            NonZeroInteger.fromIntegerOrNull(zero)
        check(safeResult == null)
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEquality() {
        val x: NonZeroInteger = NonZeroInteger.fromLong(42)
        val y: NonZeroInteger = NonZeroInteger.parse("+00042")
        check(x == y)
        check(x.hashCode() == y.hashCode())

        val z: NonZeroInteger = NonZeroInteger.fromLong(-42)
        check(x != z)
        check(x.hashCode() != z.hashCode())
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinus() {
        val x: NonZeroInteger = NonZeroInteger.fromLong(42)
        val result: NonZeroInteger = -x
        val expected: NonZeroInteger = NonZeroInteger.fromLong(-42)
        check(result == expected)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toInteger() {
        val nonZeroInteger: NonZeroInteger = NonZeroInteger.fromLong(42)
        val result: Integer = nonZeroInteger.toInteger()
        check(result == Integer.of(42))
    }

    @Test
    fun toStringOverride() {
        val value: Integer = Integer.of(-42)
        val nonZeroInteger: NonZeroInteger = NonZeroInteger.fromInteger(value)
        val result: String = nonZeroInteger.toString()
        check(result == "-42")
    }
}
