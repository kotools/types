package org.kotools.types.internal.number

import com.ionspin.kotlin.bignum.integer.BigInteger
import org.kotools.types.internal.InternalKotoolsTypesApi

/** Returns a [PlatformInteger] representing exactly the specified [value]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(value: Long): PlatformInteger {
    val delegate: BigInteger = BigInteger.fromLong(value)
    return NativeInteger(delegate)
}

@OptIn(InternalKotoolsTypesApi::class)
private class NativeInteger(
    private val delegate: BigInteger
) : PlatformInteger {
    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.delegate.toString()
}
