@file:JvmName("_IntegerArithmetics")

package org.kotools.types.internal

import java.math.BigInteger

@JvmSynthetic
internal actual fun integerRemainder(x: String, y: String): String {
    val remainder: BigInteger = BigInteger(x) % BigInteger(y)
    return remainder.toString()
}
