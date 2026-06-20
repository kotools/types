package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class DecimalSample {
    // ------------------------------- Creations -------------------------------

    @Test
    fun fromLong() {
        fun createsFromLong(input: Long, expected: String) {
            val result: Decimal = Decimal.fromLong(input)
            check("$result" == expected)
        }

        createsFromLong(input = 0, expected = "0")
        createsFromLong(input = 42, expected = "42")
        createsFromLong(input = -42, expected = "-42")
        createsFromLong(
            input = Long.MAX_VALUE,
            expected = "9223372036854775807"
        )
        createsFromLong(
            input = Long.MIN_VALUE,
            expected = "-9223372036854775808"
        )
    }

    @Test
    fun parsing() {
        fun parsesTo(input: String, expected: String) {
            check(Decimal.parse(input).toString() == expected)
            check(Decimal.parseOrNull(input).toString() == expected)
        }

        fun parsingFailsWith(input: String) {
            val exception: Throwable? = runCatching { Decimal.parse(input) }
                .exceptionOrNull()
            check(exception is NumberFormatException)
            check(Decimal.parseOrNull(input) == null)
        }

        // Parsing normalizes zero:
        parsesTo(input = "0", expected = "0")
        parsesTo(input = "+0", expected = "0")
        parsesTo(input = "-0", expected = "0")
        parsesTo(input = "0.0", expected = "0")
        parsesTo(input = "-0.0", expected = "0")

        // Parsing removes leading plus sign:
        parsesTo(input = "+3.14", expected = "3.14")

        // Parsing removes leading zeros:
        parsesTo(input = "00042", expected = "42")
        parsesTo(input = "-00042", expected = "-42")

        // Parsing removes trailing fractional zeros:
        parsesTo(input = "3.10", expected = "3.1")
        parsesTo(input = "5.00", expected = "5")

        // Parsing preserves canonical representation:
        parsesTo(input = "3.14", expected = "3.14")
        parsesTo(input = "-2.5", expected = "-2.5")
        parsesTo(input = "0.001", expected = "0.001")

        // Parsing fails with non-decimal string:
        parsingFailsWith("")
        parsingFailsWith("+")
        parsingFailsWith("-")
        parsingFailsWith(".")
        parsingFailsWith(".5")
        parsingFailsWith("3.")
        parsingFailsWith("1.2.3")
        parsingFailsWith("1.5E3")
        parsingFailsWith("12a")
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEquality() {
        fun checkEquality(decimal: Decimal, other: Any?) {
            check(decimal == other)
            check(decimal.hashCode() == other.hashCode())
        }

        fun checkDiff(decimal: Decimal, other: Any?) {
            check(decimal != other)
            check(decimal.hashCode() != other.hashCode())
        }

        // Scale-normalised values are equal:
        checkEquality(
            decimal = Decimal.fromLong(0),
            other = Decimal.parse("-0.0")
        )
        checkEquality(
            decimal = Decimal.parse("3.14"),
            other = Decimal.parse("3.140")
        )
        checkEquality(
            decimal = Decimal.parse("-2.5"),
            other = Decimal.parse("-2.50")
        )

        // Different values or types are not equal:
        checkDiff(decimal = Decimal.fromLong(0), other = Decimal.fromLong(1))
        checkDiff(decimal = Decimal.parse("3.14"), other = 3.14)
        checkDiff(decimal = Decimal.fromLong(-42), other = null)
    }

    @Test
    fun compareTo() {
        val x: Decimal = Decimal.parse("-99999999999999999999.99")
        val y: Decimal = Decimal.parse("99999999999999999999.99")
        val result: Boolean = x < y // or x.compareTo(y) < 0
        check(result)
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinus() {
        val x: Decimal = Decimal.parse("3.14")
        val result: Decimal = -x
        val expected: Decimal = Decimal.parse("-3.14")
        check(result == expected)
    }

    @Test
    fun plus() {
        val x: Decimal = Decimal.parse("1.5")
        val y: Decimal = Decimal.parse("1.25")
        val result: Decimal = x + y
        val expected: Decimal = Decimal.parse("2.75")
        check(result == expected)
    }

    @Test
    fun minus() {
        val x: Decimal = Decimal.parse("3.14")
        val y: Decimal = Decimal.parse("1.5")
        val result: Decimal = x - y
        val expected: Decimal = Decimal.parse("1.64")
        check(result == expected)
    }

    @Test
    fun times() {
        val x: Decimal = Decimal.parse("1.1")
        val y: Decimal = Decimal.parse("1.1")
        val result: Decimal = x * y
        val expected: Decimal = Decimal.parse("1.21")
        check(result == expected)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        fun checkToString(input: String, expected: String) {
            val result: String = Decimal.parse(input).toString()
            check(result == expected)
        }

        // Zero is always "0":
        checkToString(input = "0", expected = "0")
        checkToString(input = "+0", expected = "0")
        checkToString(input = "-0", expected = "0")
        checkToString(input = "0.0", expected = "0")

        // Plus sign removed, leading zeros removed:
        checkToString(input = "+42", expected = "42")
        checkToString(input = "00042", expected = "42")

        // Trailing fractional zeros removed:
        checkToString(input = "3.10", expected = "3.1")
        checkToString(input = "5.00", expected = "5")

        // Canonical representations preserved:
        checkToString(input = "3.14", expected = "3.14")
        checkToString(input = "-2.5", expected = "-2.5")
        checkToString(input = "0.001", expected = "0.001")
    }
}
