@file:JvmName("_IntegerArithmetics")

package org.kotools.types.internal

import java.math.BigInteger

@JvmSynthetic
internal actual fun integerAddition(x: String, y: String): String {
    val sum: BigInteger = BigInteger(x) + BigInteger(y)
    return sum.toString()
}

@JvmSynthetic
internal actual fun integerSubtraction(x: String, y: String): String {
    val difference: BigInteger = BigInteger(x) - BigInteger(y)
    return difference.toString()
}

@JvmSynthetic
internal actual fun integerMultiplication(x: String, y: String): String {
    val product: BigInteger = BigInteger(x) * BigInteger(y)
    return product.toString()
}
