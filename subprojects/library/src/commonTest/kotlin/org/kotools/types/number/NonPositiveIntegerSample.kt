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
}
