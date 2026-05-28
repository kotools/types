package org.kotools.types

import org.kotools.types.number.Decimal
import org.kotools.types.number.Integer
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.random.nextLong

// -----------------------------------------------------------------------------
// ------------------------------ Test repetition ------------------------------
// -----------------------------------------------------------------------------

internal inline fun repeatTest(block: () -> Unit): Unit =
    repeat(times = 1_000) { block() }

// -----------------------------------------------------------------------------
// ------------------------------- Random values -------------------------------
// -----------------------------------------------------------------------------

// Functions below are grouped by types (e.g., String), and sorted from smaller
// to greater range of values.

// ------------------------------ Random decimals ------------------------------

@OptIn(ExperimentalKotoolsTypesApi::class)
internal fun Random.decimal(): Decimal {
    val sign: String = listOf("", "+", "-").random()
    val integerPartLength: Int = this.nextInt(1..31)
    val fractionalPartLength: Int = this.nextInt(0..31)

    val capacity: Int =
        sign.length + integerPartLength + fractionalPartLength + 1 // for '.'
    val builder = StringBuilder(capacity)

    builder.append(sign)
    repeat(integerPartLength) {
        val digit: Int = (0..9).random()
        builder.append(digit)
    }
    if (fractionalPartLength > 0) {
        builder.append('.')
        repeat(fractionalPartLength) {
            val digit: Int = (0..9).random()
            builder.append(digit)
        }
    }

    val value: String = builder.toString()
    return Decimal.parse(value)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
internal fun Random.decimalExcept(illegal: Decimal): Decimal {
    var candidate: Decimal = this.decimal()
    while (candidate == illegal) candidate = this.decimal()
    return candidate
}

@OptIn(ExperimentalKotoolsTypesApi::class)
internal fun Random.positiveDecimal(): Decimal {
    val zero: Decimal = Decimal.of(0)
    var candidate: Decimal = this.decimal()
    while (candidate <= zero) candidate = this.decimal()
    return candidate
}

// ------------------------------ Random integers ------------------------------

@OptIn(ExperimentalKotoolsTypesApi::class)
internal fun Random.integer(): Integer {
    val sign: String = listOf("", "+", "-").random()
    val capacity: Int = this.nextInt(1..64) + sign.length
    val builder = StringBuilder(capacity)

    builder.append(sign)
    repeat(times = capacity - sign.length) {
        val digit: Int = (0..9).random()
        builder.append(digit)
    }

    val value: String = builder.toString()
    return Integer.parse(value)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
internal fun Random.integerExcept(illegal: Integer): Integer {
    var candidate = this.integer()
    while (candidate == illegal) candidate = this.integer()
    return candidate
}

@OptIn(ExperimentalKotoolsTypesApi::class)
internal fun Random.positiveInteger(): Integer {
    val value: String = this.positiveIntegerString()
    return Integer.parse(value)
}

// ------------------------------ Random strings -------------------------------

internal fun Random.zeroString(): String {
    val sign: String = listOf("", "+", "-").random()
    val capacity: Int = this.nextInt(1..64) + sign.length
    val builder = StringBuilder(capacity)

    builder.append(sign)
    repeat(times = capacity - sign.length) { builder.append(0) }

    return builder.toString()
}

internal fun Random.positiveIntegerString(): String {
    val sign: String = listOf("", "+").random()
    val digits: Long = this.nextLong(1..Long.MAX_VALUE)
    return "$sign$digits"
}

internal fun Random.nonZeroIntegerStringWithLeadingZeros(): String {
    val prefix: String = this.zeroString()
    val suffix: String = this.positiveIntegerString()
        .removePrefix("+")
    return "$prefix$suffix"
}

internal fun Random.nonIntegerString(): String {
    var candidate: String = this.string()
    val regex = Regex("""^[+-]?\d+$""")
    while (candidate matches regex) candidate = this.string()
    return candidate
}

internal fun Random.nonDecimalString(): String {
    var candidate: String = this.string()
    val regex = Regex("""^[+-]?\d+(\.\d+)?$""")
    while (candidate matches regex) candidate = this.string()
    return candidate
}

private fun Random.string(): String {
    val capacity: Int = this.nextInt(0..64)
    val builder = StringBuilder(capacity)

    val characterRange: CharRange = Char.MIN_VALUE..Char.MAX_VALUE
    repeat(times = capacity) {
        val character: Char = characterRange.random()
        builder.append(character)
    }

    return builder.toString()
}
