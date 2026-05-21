package org.kotools.types.internal

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
