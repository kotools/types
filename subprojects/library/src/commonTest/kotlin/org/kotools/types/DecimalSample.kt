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
        val integer = 123
        val text = "${integer}.000"
        // When
        val result: Decimal = Decimal.fromDecimal(text)
        // Then
        check("$result" == "$integer")
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
