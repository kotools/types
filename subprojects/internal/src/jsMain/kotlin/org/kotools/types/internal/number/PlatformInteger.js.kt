package org.kotools.types.internal.number

import org.kotools.types.internal.InternalKotoolsTypesApi

@OptIn(InternalKotoolsTypesApi::class)
internal actual fun PlatformInteger(value: Long): PlatformInteger {
    val decimal: String = value.toString()
    val delegate = BigInt(decimal)
    return JsInteger(delegate)
}

@OptIn(InternalKotoolsTypesApi::class)
private class JsInteger(private val delegate: BigInt) : PlatformInteger {
    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.delegate.toString()
}

private external fun BigInt(value: String): BigInt

private external object BigInt
