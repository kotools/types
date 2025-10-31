package org.kotools.types.internal

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

private external fun BigInt(value: String): BigInt

private external object BigInt

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
