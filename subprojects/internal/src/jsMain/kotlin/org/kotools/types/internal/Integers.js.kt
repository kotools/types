package org.kotools.types.internal

/** Returns the sum of [x] and [y] integers. */
@InternalKotoolsTypesApi
public actual fun Integers.addition(x: String, y: String): String {
    val sum: BigInt = BigInt(x) + BigInt(y)
    return sum.toString()
}
