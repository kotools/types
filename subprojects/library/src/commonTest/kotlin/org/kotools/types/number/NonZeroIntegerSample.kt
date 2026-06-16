package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonZeroIntegerSample {
    // --------------------------- Factory functions ---------------------------

    @Test
    fun fromInteger() {
        val integer: Integer = Integer.of(42)
        val result: NonZeroInteger = NonZeroInteger.fromInteger(integer)
        check(result.toString() == "42")

        val zero: Integer = Integer.of(0)
        val exception: Throwable? = runCatching {
            NonZeroInteger.fromInteger(zero)
        }.exceptionOrNull()
        check(exception is IllegalArgumentException)
    }

    @Test
    fun fromIntegerOrNull() {
        val integer: Integer = Integer.of(42)
        val result: NonZeroInteger? = NonZeroInteger.fromIntegerOrNull(integer)
        checkNotNull(result)
        check(result.toString() == "42")

        val zero: Integer = Integer.of(0)
        check(NonZeroInteger.fromIntegerOrNull(zero) == null)
    }

    @Test
    fun fromByte() {
        val value: Byte = 42
        val result: NonZeroInteger = NonZeroInteger.fromByte(value)
        check(result.toString() == "42")

        val zero: Byte = 0
        val exception: Throwable? = runCatching {
            NonZeroInteger.fromByte(zero)
        }.exceptionOrNull()
        check(exception is IllegalArgumentException)
    }

    @Test
    fun fromByteOrNull() {
        val value: Byte = 42
        val result: NonZeroInteger? = NonZeroInteger.fromByteOrNull(value)
        checkNotNull(result)
        check(result.toString() == "42")

        val zero: Byte = 0
        check(NonZeroInteger.fromByteOrNull(zero) == null)
    }

    @Test
    fun fromShort() {
        val value: Short = 42
        val result: NonZeroInteger = NonZeroInteger.fromShort(value)
        check(result.toString() == "42")

        val zero: Short = 0
        val exception: Throwable? = runCatching {
            NonZeroInteger.fromShort(zero)
        }.exceptionOrNull()
        check(exception is IllegalArgumentException)
    }

    @Test
    fun fromShortOrNull() {
        val value: Short = 42
        val result: NonZeroInteger? = NonZeroInteger.fromShortOrNull(value)
        checkNotNull(result)
        check(result.toString() == "42")

        val zero: Short = 0
        check(NonZeroInteger.fromShortOrNull(zero) == null)
    }

    @Test
    fun fromInt() {
        val value = 42
        val result: NonZeroInteger = NonZeroInteger.fromInt(value)
        check(result.toString() == "42")

        val exception: Throwable? = runCatching {
            NonZeroInteger.fromInt(0)
        }.exceptionOrNull()
        check(exception is IllegalArgumentException)
    }

    @Test
    fun fromIntOrNull() {
        val value = 42
        val result: NonZeroInteger? = NonZeroInteger.fromIntOrNull(value)
        checkNotNull(result)
        check(result.toString() == "42")

        check(NonZeroInteger.fromIntOrNull(0) == null)
    }

    @Test
    fun fromLong() {
        val value = 42L
        val result: NonZeroInteger = NonZeroInteger.fromLong(value)
        check(result.toString() == "42")

        val exception: Throwable? = runCatching {
            NonZeroInteger.fromLong(0L)
        }.exceptionOrNull()
        check(exception is IllegalArgumentException)
    }

    @Test
    fun fromLongOrNull() {
        val value = 42L
        val result: NonZeroInteger? = NonZeroInteger.fromLongOrNull(value)
        checkNotNull(result)
        check(result.toString() == "42")

        check(NonZeroInteger.fromLongOrNull(0L) == null)
    }

    @Test
    fun parsing() {
        fun parsesTo(input: String, expected: String) {
            check(NonZeroInteger.parse(input).toString() == expected)
            check(NonZeroInteger.parseOrNull(input)?.toString() == expected)
        }

        fun failsWithIllegalArg(input: String) {
            val exception: Throwable? = runCatching {
                NonZeroInteger.parse(input)
            }.exceptionOrNull()
            check(exception is IllegalArgumentException)
            check(NonZeroInteger.parseOrNull(input) == null)
        }

        fun failsWithNumberFormat(input: String) {
            val exception: Throwable? = runCatching {
                NonZeroInteger.parse(input)
            }.exceptionOrNull()
            check(exception is NumberFormatException)
            check(NonZeroInteger.parseOrNull(input) == null)
        }

        parsesTo(input = "42", expected = "42")
        parsesTo(input = "-42", expected = "-42")
        parsesTo(input = "+00042", expected = "42")

        // Fails with IllegalArgumentException for zero:
        failsWithIllegalArg("0")
        failsWithIllegalArg("+0")
        failsWithIllegalArg("-0")
        failsWithIllegalArg("000")

        // Fails with NumberFormatException for non-integer strings:
        failsWithNumberFormat("")
        failsWithNumberFormat("12a")
        failsWithNumberFormat("3.14")
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEquality() {
        fun checkEquality(integer: NonZeroInteger, other: Any?) {
            check(integer == other)
            check(integer.hashCode() == other.hashCode())
        }

        fun checkDiff(integer: NonZeroInteger, other: Any?) {
            check(integer != other)
            check(integer.hashCode() != other.hashCode())
        }

        val x: NonZeroInteger = NonZeroInteger.fromInteger(Integer.of(42))
        val y: NonZeroInteger = NonZeroInteger.fromInteger(Integer.parse("+00042"))
        checkEquality(integer = x, other = y)

        val z: NonZeroInteger = NonZeroInteger.fromInteger(Integer.of(-42))
        checkDiff(integer = x, other = z)
        checkDiff(integer = x, other = 42)
        checkDiff(integer = x, other = null)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toInteger() {
        val integer: Integer = Integer.of(42)
        val nonZero: NonZeroInteger = NonZeroInteger.fromInteger(integer)
        val result: Integer = nonZero.toInteger()
        check(result == integer)
    }

    @Test
    fun toStringOverride() {
        fun checkToString(input: NonZeroInteger, expected: String) {
            val result: String = input.toString()
            check(result == expected)
        }

        checkToString(
            input = NonZeroInteger.fromInteger(Integer.of(42)),
            expected = "42"
        )
        checkToString(
            input = NonZeroInteger.fromInteger(Integer.of(-42)),
            expected = "-42"
        )
        checkToString(
            input = NonZeroInteger.fromInteger(Integer.parse("+00042")),
            expected = "42"
        )
    }
}
