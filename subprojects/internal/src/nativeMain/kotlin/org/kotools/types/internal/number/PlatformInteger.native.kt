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
internal actual fun PlatformInteger(value: String): PlatformInteger {
    val delegate: BigInteger = BigInteger.parseString(value)
    return NativeInteger(delegate)
}

@OptIn(InternalKotoolsTypesApi::class)
private class NativeInteger(
    private val delegate: BigInteger
) : PlatformInteger {
    // ------------------------------ Comparisons ------------------------------

    override fun equals(other: Any?): Boolean =
        other is NativeInteger && this.delegate == other.delegate

    override fun hashCode(): Int = this.delegate.hashCode()

    override fun compareTo(other: PlatformInteger): Int {
        check(other is NativeInteger)
        return this.delegate.compare(other.delegate)
    }

    // ------------------------- Arithmetic operations -------------------------

    override fun unaryMinus(): PlatformInteger = NativeInteger(-this.delegate)

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.delegate.toString()
}
