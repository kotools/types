package org.kotools.types.internal.number

import com.ionspin.kotlin.bignum.integer.BigInteger
import org.kotools.types.internal.InternalKotoolsTypesApi

@OptIn(InternalKotoolsTypesApi::class)
internal actual fun PlatformInteger(value: Long): PlatformInteger {
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
