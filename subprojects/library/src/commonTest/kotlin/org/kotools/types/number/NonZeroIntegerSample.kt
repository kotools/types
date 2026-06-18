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

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        val value: Integer = Integer.of(-42)
        val nonZeroInteger: NonZeroInteger = NonZeroInteger.fromInteger(value)
        val result: String = nonZeroInteger.toString()
        check(result == "-42")
    }
}
