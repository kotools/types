package org.kotools.types.internal

import java.math.BigInteger

/** Returns the sum of [x] and [y] integers. */
@InternalKotoolsTypesApi
public actual fun Integers.addition(x: String, y: String): String {
    val sum: BigInteger = BigInteger(x) + BigInteger(y)
    return sum.toString()
}
