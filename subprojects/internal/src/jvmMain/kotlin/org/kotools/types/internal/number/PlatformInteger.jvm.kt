package org.kotools.types.internal.number

import org.kotools.types.internal.InternalKotoolsTypesApi
import java.math.BigInteger

@OptIn(InternalKotoolsTypesApi::class)
internal actual fun PlatformInteger(value: Long): PlatformInteger {
    val delegate: BigInteger = BigInteger.valueOf(value)
    return JvmInteger(delegate)
}

@OptIn(InternalKotoolsTypesApi::class)
private class JvmInteger(private val delegate: BigInteger) : PlatformInteger {
    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.delegate.toString()
}
