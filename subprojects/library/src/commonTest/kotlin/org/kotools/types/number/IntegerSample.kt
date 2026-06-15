package org.kotools.types.number

import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test

@OptIn(ExperimentalKotoolsTypesApi::class)
class IntegerSample {
    // ------------------------------ Motivations ------------------------------

    @Suppress("INTEGER_OVERFLOW")
    @Test
    fun overflowProblem() {
        val x = 9223372036854775807
        val y = 10
        check(x + y == -9223372036854775799) // instead of 9223372036854775817
        check(-x - y == 9223372036854775799) // instead of -9223372036854775817
        check(x * y == -10L) // instead of 92233720368547758070
    }

    @Test
    fun overflowSolution() {
        val x: Integer = Integer.fromLong(9223372036854775807)
        val y: Integer = Integer.fromLong(10)
        check(x + y == Integer.parse("9223372036854775817"))
        check(-x - y == Integer.parse("-9223372036854775817"))
        check(x * y == Integer.parse("92233720368547758070"))
    }

    @Test
    fun divisionByZeroSolution() {
        // Common code
        val x: Integer = Integer.fromLong(12)
        val y: Integer = Integer.fromLong(0)
        val quotient: Result<Integer> = runCatching { x / y }
        val remainder: Result<Integer> = runCatching { x % y }
        check(quotient.exceptionOrNull() is ArithmeticException)
        check(remainder.exceptionOrNull() is ArithmeticException)
    }

    @Test
    fun euclideanDivisionProblem() {
        check(-7 % 2 == -1) // instead of 1
    }

    // ------------------------------- Creations -------------------------------

    @Test
    fun fromLong() {
        fun createsFromLong(input: Long, expected: String) {
            val result: Integer = Integer.fromLong(input)
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
    fun fromByte() {
        fun createsFromByte(input: Byte, expected: String) {
            val result: Integer = Integer.fromByte(input)
            check("$result" == expected)
        }

        createsFromByte(input = 0, expected = "0")
        createsFromByte(input = 42, expected = "42")
        createsFromByte(input = -42, expected = "-42")
        createsFromByte(input = Byte.MAX_VALUE, expected = "127")
        createsFromByte(input = Byte.MIN_VALUE, expected = "-128")
    }

    @Test
    fun fromShort() {
        fun createsFromShort(input: Short, expected: String) {
            val result: Integer = Integer.fromShort(input)
            check("$result" == expected)
        }

        createsFromShort(input = 0, expected = "0")
        createsFromShort(input = 42, expected = "42")
        createsFromShort(input = -42, expected = "-42")
        createsFromShort(input = Short.MAX_VALUE, expected = "32767")
        createsFromShort(input = Short.MIN_VALUE, expected = "-32768")
    }

    @Test
    fun fromInt() {
        fun createsFromInt(input: Int, expected: String) {
            val result: Integer = Integer.fromInt(input)
            check("$result" == expected)
        }

        createsFromInt(input = 0, expected = "0")
        createsFromInt(input = 42, expected = "42")
        createsFromInt(input = -42, expected = "-42")
        createsFromInt(input = Int.MAX_VALUE, expected = "2147483647")
        createsFromInt(input = Int.MIN_VALUE, expected = "-2147483648")
    }

    @Test
    fun parsing() {
        fun parsesTo(input: String, expected: String) {
            check(Integer.parse(input).toString() == expected)
            check(Integer.parseOrNull(input).toString() == expected)
        }

        fun parsingFailsWith(input: String) {
            val exception: Throwable? = runCatching { Integer.parse(input) }
                .exceptionOrNull()
            check(exception is NumberFormatException)

            check(Integer.parseOrNull(input) == null)
        }

        // Parsing normalizes zero:
        parsesTo(input = "0", expected = "0")
        parsesTo(input = "+0", expected = "0")
        parsesTo(input = "-0", expected = "0")
        parsesTo(input = "000", expected = "0")
        parsesTo(input = "+000", expected = "0")
        parsesTo(input = "-000", expected = "0")

        // Parsing removes leading plus sign:
        parsesTo(input = "+42", expected = "42")

        // Parsing removes leading zeros:
        parsesTo(input = "00042", expected = "42")
        parsesTo(input = "-00042", expected = "-42")

        // Parsing preserves canonical representation:
        parsesTo(input = "42", expected = "42")
        parsesTo(input = "-42", expected = "-42")

        // Parsing fails with noninteger string:
        parsingFailsWith("")
        parsingFailsWith("+")
        parsingFailsWith("-")
        parsingFailsWith("12a")
        parsingFailsWith("3.14")
        parsingFailsWith(" 42")
    }

    // ------------------------------ Comparisons ------------------------------

    @Test
    fun structuralEquality() {
        fun checkEquality(integer: Integer, other: Any?) {
            check(integer == other)
            check(integer.hashCode() == other.hashCode())
        }

        fun checkDiff(integer: Integer, other: Any?) {
            check(integer != other)
            check(integer.hashCode() != other.hashCode())
        }

        checkEquality(
            integer = Integer.fromLong(0),
            other = Integer.parse("-000")
        )
        checkEquality(
            integer = Integer.fromLong(42),
            other = Integer.parse("+00042")
        )
        checkEquality(
            integer = Integer.fromLong(-42),
            other = Integer.parse("-0042")
        )

        checkDiff(integer = Integer.fromLong(0), other = Integer.fromLong(1))
        checkDiff(integer = Integer.fromLong(42), other = 42)
        checkDiff(integer = Integer.fromLong(-42), other = null)
    }

    @Test
    fun compareTo() {
        val x: Integer = Integer.parse("-99999999999999999999")
        val y: Integer = Integer.parse("99999999999999999999")
        val result: Boolean = x < y // or x.compareTo(y) < 0
        check(result)
    }

    // ------------------------- Arithmetic operations -------------------------

    @Test
    fun unaryMinus() {
        val x: Integer = Integer.parse("99999999999999999999")
        val result: Integer = -x
        val expected: Integer = Integer.parse("-99999999999999999999")
        check(result == expected)
    }

    @Test
    fun plus() {
        val x: Integer = Integer.parse("99999999999999999999")
        val y: Integer = Integer.parse("1")
        val result: Integer = x + y
        val expected: Integer = Integer.parse("100000000000000000000")
        check(result == expected)
    }

    @Test
    fun minus() {
        val x: Integer = Integer.parse("-99999999999999999999")
        val y: Integer = Integer.parse("1")
        val result: Integer = x - y
        val expected: Integer = Integer.parse("-100000000000000000000")
        check(result == expected)
    }

    @Test
    fun times() {
        val x: Integer = Integer.parse("99999999999999999999")
        val y: Integer = Integer.parse("10")
        val result: Integer = x * y
        val expected: Integer = Integer.parse("999999999999999999990")
        check(result == expected)
    }

    @Test
    fun euclideanDivision() {
        val x: Integer = Integer.fromLong(-7)
        val y: Integer = Integer.fromLong(2)

        val quotient: Integer = x / y
        val remainder: Integer = x % y

        check(quotient == Integer.fromLong(-4))
        check(remainder == Integer.fromLong(1))
        check(x == quotient * y + remainder)
    }

    @Test
    fun euclideanDivisionOrNull() {
        val x: Integer = Integer.fromLong(-7)
        val y: Integer = Integer.fromLong(2)

        val quotient: Integer? = x.divOrNull(y)
        val remainder: Integer? = x.remOrNull(y)

        checkNotNull(quotient)
        checkNotNull(remainder)
        check(quotient == Integer.fromLong(-4))
        check(remainder == Integer.fromLong(1))
        check(x == quotient * y + remainder)
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    fun toStringOverride() {
        fun checkToString(input: Integer, expected: String) {
            val result: String = input.toString()
            check(result == expected)
        }

        checkToString(input = Integer.fromLong(0), expected = "0")
        checkToString(input = Integer.parse("+0"), expected = "0")
        checkToString(input = Integer.parse("-0"), expected = "0")
        checkToString(input = Integer.parse("+42"), expected = "42")
        checkToString(input = Integer.fromLong(-42), expected = "-42")
        checkToString(input = Integer.parse("00042"), expected = "42")
        checkToString(input = Integer.parse("+00042"), expected = "42")
        checkToString(input = Integer.parse("-00042"), expected = "-42")
    }

    @Test
    fun toLong() {
        val x: Integer = Integer.fromLong(42)
        val result: Long = x.toLong()
        check(result == 42L)

        val y: Integer = Integer.parse("99999999999999999999")
        val exception: Throwable? = runCatching { y.toLong() }
            .exceptionOrNull()
        check(exception is ArithmeticException)
    }

    @Test
    fun toLongOrNull() {
        val x: Integer = Integer.fromLong(42)
        val result: Long? = x.toLongOrNull()
        check(result == 42L)

        val y: Integer = Integer.parse("99999999999999999999")
        check(y.toLongOrNull() == null)
    }

    @Test
    fun toInt() {
        val x: Integer = Integer.fromLong(42)
        val result: Int = x.toInt()
        check(result == 42)

        val y: Integer = Integer.parse("9999999999")
        val exception: Throwable? = runCatching { y.toInt() }
            .exceptionOrNull()
        check(exception is ArithmeticException)
    }

    @Test
    fun toIntOrNull() {
        val x: Integer = Integer.fromLong(42)
        val result: Int? = x.toIntOrNull()
        check(result == 42)

        val y: Integer = Integer.parse("9999999999")
        check(y.toIntOrNull() == null)
    }

    @Test
    fun toShort() {
        val x: Integer = Integer.fromLong(42)
        val result: Short = x.toShort()
        check(result == 42.toShort())

        val y: Integer = Integer.parse("99999")
        val exception: Throwable? = runCatching { y.toShort() }
            .exceptionOrNull()
        check(exception is ArithmeticException)
    }
}
