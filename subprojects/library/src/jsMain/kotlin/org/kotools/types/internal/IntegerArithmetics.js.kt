package org.kotools.types.internal

internal actual fun integerUnaryMinus(x: String): String = BigInt(x)
    .unaryMinus()
    .toString()

internal actual fun integerAddition(x: String, y: String): String {
    val sum: BigInt = BigInt(x) + BigInt(y)
    return sum.toString()
}

internal actual fun integerSubtraction(x: String, y: String): String {
    val difference: BigInt = BigInt(x) - BigInt(y)
    return difference.toString()
}

internal actual fun integerMultiplication(x: String, y: String): String {
    val product: BigInt = BigInt(x) * BigInt(y)
    return product.toString()
}

internal actual fun integerDivision(x: String, y: String): String {
    val quotient: BigInt = BigInt(x) / BigInt(y)
    return quotient.toString()
}

internal actual fun integerRemainder(x: String, y: String): String {
    val remainder: BigInt = BigInt(x) % BigInt(y)
    return remainder.toString()
}

private external fun BigInt(value: String): BigInt

private external object BigInt

private operator fun BigInt.unaryMinus(): BigInt {
    @Suppress("UNUSED_VARIABLE") val x: BigInt = this
    val negative: dynamic = js("-x")
    return BigInt("$negative")
}

@Suppress("UNUSED_VARIABLE")
private operator fun BigInt.plus(other: BigInt): BigInt {
    val x = this
    val y = other
    val sum: dynamic = js("x + y")
    return BigInt("$sum")
}

@Suppress("UNUSED_VARIABLE")
private operator fun BigInt.minus(other: BigInt): BigInt {
    val x = this
    val y = other
    val difference: dynamic = js("x - y")
    return BigInt("$difference")
}

@Suppress("UNUSED_VARIABLE")
private operator fun BigInt.times(other: BigInt): BigInt {
    val x = this
    val y = other
    val product: dynamic = js("x * y")
    return BigInt("$product")
}

@Suppress("UNUSED_VARIABLE")
private operator fun BigInt.div(other: BigInt): BigInt {
    val x = this
    val y = other
    val quotient: dynamic = js("x / y")
    return BigInt("$quotient")
}

@Suppress("UNUSED_VARIABLE")
private operator fun BigInt.rem(other: BigInt): BigInt {
    val x = this
    val y = other
    val remainder: dynamic = js("x % y")
    return BigInt("$remainder")
}
