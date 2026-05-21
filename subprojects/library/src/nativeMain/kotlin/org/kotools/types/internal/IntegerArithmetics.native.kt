package org.kotools.types.internal

import com.ionspin.kotlin.bignum.integer.BigInteger

internal actual fun integerMultiplication(x: String, y: String): String {
    val product: BigInteger =
        BigInteger.parseString(x) * BigInteger.parseString(y)
    return product.toString()
}

internal actual fun integerDivision(x: String, y: String): String {
    val quotient: BigInteger =
        BigInteger.parseString(x) / BigInteger.parseString(y)
    return quotient.toString()
}

internal actual fun integerRemainder(x: String, y: String): String {
    val remainder: BigInteger =
        BigInteger.parseString(x) % BigInteger.parseString(y)
    return remainder.toString()
}
