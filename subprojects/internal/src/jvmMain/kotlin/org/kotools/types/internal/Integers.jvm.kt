package org.kotools.types.internal

import java.math.BigInteger

/** Returns the sum of [x] and [y] integers. */
@InternalKotoolsTypesApi
public actual fun Integers.addition(x: String, y: String): String {
    val sum: BigInteger = BigInteger(x) + BigInteger(y)
    return sum.toString()
}

/** Returns the difference between [x] and [y] integers. */
@InternalKotoolsTypesApi
public actual fun Integers.subtraction(x: String, y: String): String {
    val difference: BigInteger = BigInteger(x) - BigInteger(y)
    return difference.toString()
}

/** Returns the product of [x] and [y] integers. */
@InternalKotoolsTypesApi
public actual fun Integers.multiplication(x: String, y: String): String {
    val product: BigInteger = BigInteger(x) * BigInteger(y)
    return product.toString()
}
