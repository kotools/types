package org.kotools.types.internal.number

import org.kotools.types.internal.InternalKotoolsTypesApi
import java.math.BigInteger

/** Returns a [PlatformInteger] representing exactly the specified [value]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(value: Long): PlatformInteger {
    val delegate: BigInteger = BigInteger.valueOf(value)
    return JvmInteger(delegate)
}

@JvmSynthetic
@OptIn(InternalKotoolsTypesApi::class)
internal actual fun PlatformInteger(value: String): PlatformInteger {
    val delegate = BigInteger(value)
    return JvmInteger(delegate)
}

@OptIn(InternalKotoolsTypesApi::class)
private class JvmInteger(private val delegate: BigInteger) : PlatformInteger {
    // ------------------------------ Comparisons ------------------------------

    override fun equals(other: Any?): Boolean =
        other is JvmInteger && this.delegate == other.delegate

    override fun hashCode(): Int = this.delegate.hashCode()

    override fun compareTo(other: PlatformInteger): Int {
        check(other is JvmInteger)
        return this.delegate.compareTo(other.delegate)
    }

    // ------------------------- Arithmetic operations -------------------------

    override fun unaryMinus(): PlatformInteger = JvmInteger(-this.delegate)

    override fun plus(other: PlatformInteger): PlatformInteger {
        check(other is JvmInteger)
        val sum: BigInteger = this.delegate.add(other.delegate)
        return JvmInteger(sum)
    }

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.delegate.toString()
}
