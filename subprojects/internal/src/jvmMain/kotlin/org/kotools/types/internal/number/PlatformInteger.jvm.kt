package org.kotools.types.internal.number

import org.kotools.types.internal.InternalKotoolsTypesApi
import java.math.BigInteger

/** Returns a [PlatformInteger] representing the specified [value]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(value: Long): PlatformInteger {
    val delegate: BigInteger = BigInteger.valueOf(value)
    return JvmInteger(delegate)
}

/** Returns a [PlatformInteger] representing the number described by [value]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(value: String): PlatformInteger {
    val delegate = BigInteger(value)
    return JvmInteger(delegate)
}

@JvmInline
@OptIn(InternalKotoolsTypesApi::class)
private value class JvmInteger(private val delegate: BigInteger) :
    PlatformInteger {
    override fun toString(): String = this.delegate.toString()
}
