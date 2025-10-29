package org.kotools.types.internal

import com.ionspin.kotlin.bignum.integer.BigInteger

/** Returns the sum of [x] and [y] integers. */
@InternalKotoolsTypesApi
public actual fun Integers.addition(x: String, y: String): String {
    val sum: BigInteger = BigInteger.parseString(x) + BigInteger.parseString(y)
    return sum.toString()
}

/** Returns the difference between [x] and [y] integers. */
@InternalKotoolsTypesApi
public actual fun Integers.subtraction(x: String, y: String): String {
    val difference: BigInteger =
        BigInteger.parseString(x) - BigInteger.parseString(y)
    return difference.toString()
}
