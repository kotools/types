package org.kotools.types

import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class DecimalSample {
    // ----------------------------- Documentation -----------------------------

    @Suppress("SimplifyBooleanWithConstants")
    @Test
    fun representationProblem() {
        check(1.1 + 2.2 == 3.3000000000000003) // instead of exactly 3.3
    }

    @Suppress("DIVISION_BY_ZERO")
    @Test
    fun divisionByZeroProblem() {
        val x: Double = 1.0 / 0.0 // passes instead of throwing exception
        val y: Double = x + 10 // error silently propagates
        check(y == Double.POSITIVE_INFINITY)
    }

    // ------------------------------- Creations -------------------------------

    @Test
    fun ofLong() {
        check(Decimal.of(0).toString() == "0")
        check(Decimal.of(42).toString() == "42")
        check(Decimal.of(-3).toString() == "-3")
    }

    @Test
    fun fromString() {
        check(Decimal.fromString("000") == Decimal.of(0))
        check(Decimal.fromString("+042.0") == Decimal.of(42))
        check(Decimal.fromString("-3.140").toString() == "-3.14")
    }

    @Test
    fun fromStringOrNull() {
        check(Decimal.fromStringOrNull("000") == Decimal.of(0))
        check(Decimal.fromStringOrNull("+042.0") == Decimal.of(42))
        check(Decimal.fromStringOrNull("-3.140").toString() == "-3.14")
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun equality() {
        // Given
        val x: Decimal = Decimal.of(1)
        val y: Decimal = Decimal.fromString("+0001")
        val z: Decimal = Decimal.fromString("+1.000")
        // When
        val equality: Boolean = x == y && y == z
        val hashConformity: Boolean = x.hashCode() == y.hashCode()
                && y.hashCode() == z.hashCode()
        // Then
        check(equality)
        check(hashConformity)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        // Given
        val number = 123456789L
        val decimal: Decimal = Decimal.of(number)
        // When
        val result: String = decimal.toString()
        // Then
        check(result == "$number")
    }
}
