package org.kotools.types.internal

import com.ionspin.kotlin.bignum.integer.BigInteger

internal actual fun integerAddition(x: String, y: String): String {
    val sum: BigInteger = BigInteger.parseString(x) + BigInteger.parseString(y)
    return sum.toString()
}

internal actual fun integerSubtraction(x: String, y: String): String {
    val difference: BigInteger =
        BigInteger.parseString(x) - BigInteger.parseString(y)
    return difference.toString()
}

internal actual fun integerMultiplication(x: String, y: String): String {
    val product: BigInteger =
        BigInteger.parseString(x) * BigInteger.parseString(y)
    return product.toString()
}
