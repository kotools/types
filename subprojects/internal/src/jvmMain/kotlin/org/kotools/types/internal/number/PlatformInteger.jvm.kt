package org.kotools.types.internal.number

import org.kotools.types.internal.InternalKotoolsTypesApi
import java.math.BigInteger

/** Returns a [PlatformInteger] representing exactly the specified [value]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(value: Long): PlatformInteger {
    val delegate: BigInteger = BigInteger.valueOf(value)
    return JvmInteger(delegate)
}

@OptIn(InternalKotoolsTypesApi::class)
private class JvmInteger(private val delegate: BigInteger) : PlatformInteger {
    // ------------------------------ Comparisons ------------------------------

    override fun equals(other: Any?): Boolean =
        other is JvmInteger && this.delegate == other.delegate

    override fun hashCode(): Int = this.delegate.hashCode()

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.delegate.toString()
}
