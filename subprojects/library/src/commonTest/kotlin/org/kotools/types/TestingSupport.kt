package org.kotools.types

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
