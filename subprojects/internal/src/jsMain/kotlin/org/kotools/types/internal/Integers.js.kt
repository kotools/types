package org.kotools.types.internal

/** Returns the sum of [x] and [y] integers. */
@InternalKotoolsTypesApi
public actual fun Integers.addition(x: String, y: String): String {
    val sum: BigInt = BigInt(x) + BigInt(y)
    return sum.toString()
}

/** Returns the difference between [x] and [y] integers. */
@InternalKotoolsTypesApi
public actual fun Integers.subtraction(x: String, y: String): String {
    val difference: BigInt = BigInt(x) - BigInt(y)
    return difference.toString()
}

/** Returns the product of [x] and [y] integers. */
@InternalKotoolsTypesApi
public actual fun Integers.multiplication(x: String, y: String): String {
    val product: BigInt = BigInt(x) * BigInt(y)
    return product.toString()
}
