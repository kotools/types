package org.kotools.types.internal

import com.ionspin.kotlin.bignum.integer.BigInteger

internal actual fun integerRemainder(x: String, y: String): String {
    val remainder: BigInteger =
        BigInteger.parseString(x) % BigInteger.parseString(y)
    return remainder.toString()
}
