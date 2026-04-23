package org.kotools.types

internal inline fun repeatTest(block: () -> Unit): Unit =
    repeat(times = 1_000) { block() }

// ------------------------------ Random strings -------------------------------

internal fun randomZeroString(): String {
    val sign: String = listOf("", "+", "-").random()
    val integerPart: String = buildString {
        val times: Int = (1..24).random()
        repeat(times) { this.append('0') }
    }
    val hasFractionalPart: Boolean = listOf(true, false).random()
    if (!hasFractionalPart) return "$sign$integerPart"
    val fractionalPart: String = buildString {
        val times: Int = (1..24).random()
        repeat(times) { this.append('0') }
    }
    return "$sign${integerPart}.$fractionalPart"
}

internal fun randomPositiveDecimalString(): String {
    val sign: String = listOf("", "+").random()
    var integerPart: String = buildString {
        val times: Int = (1..24).random()
        repeat(times) {
            val digit: Int = (0..9).random()
            this.append(digit)
        }
    }
    val isZero: Boolean = integerPart.all { it == '0' }
    if (isZero) integerPart += (1..9).random()
    val hasFractionalPart: Boolean = listOf(true, false).random()
    if (!hasFractionalPart) return "$sign$integerPart"
    val fractionalPart: String = buildString {
        val times: Int = (1..24).random()
        repeat(times) {
            val digit: Int = (0..9).random()
            this.append(digit)
        }
    }
    return "$sign${integerPart}.$fractionalPart"
}

internal fun randomNegativeDecimalString(): String {
    val sign = '-'
    val number: String = randomPositiveDecimalString()
        .removePrefix("+")
    return "$sign$number"
}

internal fun randomNonZeroDecimalString(): String {
    val sign: String = listOf("", "+", "-").random()
    val number: String = randomPositiveDecimalString()
        .removePrefix("+")
    return "$sign$number"
}
