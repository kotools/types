package org.kotools.types

internal inline fun repeatTest(block: () -> Unit): Unit =
    repeat(times = 1_000) { block() }

// ------------------------------ Random integers ------------------------------

@OptIn(ExperimentalKotoolsTypesApi::class)
internal fun Integer.Companion.random(): Integer {
    val sign: String = listOf("", "+", "-").random()
    val digits: List<Char> = ('0'..'9').toList()
    val lengthRange: IntRange = 1..12
    val number: String = randomStringWith(digits, lengthRange)
    return Integer.fromDecimal("$sign$number")
}

@OptIn(ExperimentalKotoolsTypesApi::class)
internal fun Integer.Companion.randomExcept(illegal: Integer): Integer {
    var candidate: Integer = this.random()
    while (candidate == illegal) candidate = this.random()
    return candidate
}

// -------------------------- Random decimal numbers ---------------------------

@OptIn(ExperimentalKotoolsTypesApi::class)
internal fun Decimal.Companion.random(): Decimal {
    val text: String = randomNonZeroDecimalString()
    return Decimal.fromDecimal(text)
}

@OptIn(ExperimentalKotoolsTypesApi::class)
internal fun Decimal.Companion.randomExcept(illegal: Decimal): Decimal {
    var candidate: Decimal = this.random()
    while (candidate == illegal) candidate = this.random()
    return candidate
}

// ------------------------------ Random strings -------------------------------

internal fun randomZeroString(): String {
    val sign: String = listOf("", "+", "-").random()
    val digits: List<Char> = listOf('0')
    val lengthRange: IntRange = 1..256
    val integerPart: String = randomStringWith(digits, lengthRange)
    val hasFractionalPart: Boolean = listOf(true, false).random()
    if (!hasFractionalPart) return "$sign$integerPart"
    val fractionalPart: String = randomStringWith(digits, lengthRange)
    return "$sign${integerPart}.$fractionalPart"
}

internal fun randomPositiveDecimalString(): String {
    val sign: String = listOf("", "+").random()
    val digits: List<Char> = ('0'..'9').toList()
    val lengthRange: IntRange = 1..12
    val integerPart: String = randomStringWith(digits, lengthRange)
    val hasFractionalPart: Boolean = listOf(true, false).random()
    if (!hasFractionalPart) {
        val isZero: Boolean = integerPart.all { it == '0' }
        if (!isZero) return "$sign$integerPart"
        val nonZeroDigits: List<Char> = digits.filter { it != '0' }
        val suffix: String = randomStringWith(nonZeroDigits, lengthRange)
        return "$sign$integerPart$suffix"
    }
    val fractionalPart: String = randomStringWith(digits, lengthRange)
    val isZero: Boolean = integerPart.all { it == '0' }
            && fractionalPart.all { it == '0' }
    if (!isZero) return "$sign${integerPart}.$fractionalPart"
    val nonZeroDigits: List<Char> = digits.filter { it != '0' }
    val suffix: String = randomStringWith(nonZeroDigits, lengthRange)
    return "$sign${integerPart}.$fractionalPart$suffix"
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

@OptIn(ExperimentalKotoolsTypesApi::class)
internal fun randomMalformedDecimalString(): String {
    val characters: List<Char> =
        ('A'..'Z') + ('a'..'z') + ('0'..'9') + listOf(' ', '+', '-', '.')
    val lengthRange: IntRange = 0..32
    var text: String = randomStringWith(characters, lengthRange)
    while (Decimal.isValid(text))
        text = randomStringWith(characters, lengthRange)
    return text
}

private fun randomStringWith(
    characters: List<Char>,
    lengthRange: IntRange
): String = buildString {
    val times: Int = lengthRange.random()
    repeat(times) {
        val character: Char = characters.random()
        this.append(character)
    }
}
