package org.kotools.types.internal.number

import org.kotools.types.internal.InternalKotoolsTypesApi

/** Returns a [PlatformInteger] representing exactly the specified [value]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(value: Long): PlatformInteger {
    val delegate = BigInt("$value")
    return JsInteger(delegate)
}

@OptIn(InternalKotoolsTypesApi::class)
private class JsInteger(private val delegate: BigInt) : PlatformInteger {
    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.delegate.toString()
}

private external fun BigInt(value: String): BigInt

private external object BigInt
