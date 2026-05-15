package org.kotools.types.internal.number

import org.kotools.types.internal.InternalKotoolsTypesApi

/** Returns a [PlatformInteger] representing the specified [value]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(value: Long): PlatformInteger {
    val decimal: String = value.toString()
    return PlatformInteger(decimal)
}

/** Returns a [PlatformInteger] representing the number described by [value]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(value: String): PlatformInteger {
    val delegate = BigInt(value)
    return JsInteger(delegate)
}

@OptIn(InternalKotoolsTypesApi::class)
private value class JsInteger(private val delegate: BigInt) : PlatformInteger {
    override fun toString(): String = this.delegate.toString()
}

private external object BigInt

private external fun BigInt(value: String): BigInt
