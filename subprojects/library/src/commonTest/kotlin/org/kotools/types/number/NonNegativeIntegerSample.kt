package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class NonNegativeIntegerSample {
    // --------------------------- Factory functions ---------------------------

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
