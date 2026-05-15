package org.kotools.types.internal.number

import com.ionspin.kotlin.bignum.integer.BigInteger
import org.kotools.types.internal.InternalKotoolsTypesApi

/** Returns a [PlatformInteger] representing the specified [value]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(value: Long): PlatformInteger {
    val delegate: BigInteger = BigInteger.fromLong(value)
    return NativeInteger(delegate)
}

/** Returns a [PlatformInteger] representing the number described by [value]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(value: String): PlatformInteger {
    val delegate: BigInteger = BigInteger.parseString(value)
    return NativeInteger(delegate)
}

@OptIn(InternalKotoolsTypesApi::class)
private value class NativeInteger(private val delegate: BigInteger) :
    PlatformInteger {
    override fun toString(): String = this.delegate.toString()
}
