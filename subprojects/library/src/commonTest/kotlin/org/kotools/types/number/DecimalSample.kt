package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
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
    fun parse() {
        check(Decimal.parse("000") == Decimal.of(0))
        check(Decimal.parse("+042.0") == Decimal.of(42))
        check(Decimal.parse("-3.140").toString() == "-3.14")
    }

    @Test
    fun parseOrNull() {
        check(Decimal.parseOrNull("000") == Decimal.of(0))
        check(Decimal.parseOrNull("+042.0") == Decimal.of(42))
        check(Decimal.parseOrNull("-3.140").toString() == "-3.14")
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun equality() {
        val x: Decimal = Decimal.of(1)
        val y: Decimal = Decimal.parse("+0001")
        val z: Decimal = Decimal.parse("+1.000")

        val equality: Boolean = x == y && y == z
        val hashConformity: Boolean = x.hashCode() == y.hashCode()
                && y.hashCode() == z.hashCode()

        check(equality && hashConformity)
    }

    @Test
    fun compareTo() {
        check(Decimal.of(0) < Decimal.parse("0.001"))
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinus() {
        val zero: Decimal = Decimal.of(0)
        check(-zero === zero)

        val x: Decimal = Decimal.parse("-0.120")
        val y: Decimal = Decimal.parse("0.12")
        check(-x == y)
    }

    @Test
    fun plus() {
        val x: Decimal = Decimal.parse("1.1")
        val y: Decimal = Decimal.parse("2.002")
        val zero: Decimal = Decimal.of(0)
        check(x + zero === x)
        check(x + y == Decimal.parse("3.102"))
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        check(Decimal.of(0).toString() == "0")
        check(Decimal.parse("+42").toString() == "42")
        check(Decimal.parse("-3.14").toString() == "-3.14")
    }
}
