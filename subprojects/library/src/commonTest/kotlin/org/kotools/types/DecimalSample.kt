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
    fun fromIntegerLong() {
        // Given
        val number = 123456789L
        // When
        val result: Decimal = Decimal.fromInteger(number)
        // Then
        check("$result" == "$number")
    }

    @Test
    fun fromDecimalString() {
        // Given
        val text = "+000.000123000"
        // When
        val result: Decimal = Decimal.fromDecimal(text)
        // Then
        check("$result" == "0.000123")
    }

    @Test
    fun fromDecimalStringOrNull() {
        // Given
        val text = "+000.000123000"
        // When
        val result: Decimal? = Decimal.fromDecimalOrNull(text)
        // Then
        check("$result" == "0.000123")
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun equality() {
        // Given
        val x: Decimal = Decimal.fromInteger(1)
        val y: Decimal = Decimal.fromDecimal("+0001")
        val z: Decimal = Decimal.fromDecimal("+1.000")
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
        val decimal: Decimal = Decimal.fromInteger(number)
        // When
        val result: String = decimal.toString()
        // Then
        check(result == "$number")
    }
}
