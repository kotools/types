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
    override fun compareTo(other: PlatformInteger): Int =
        this.delegate.compareTo((other as NativeInteger).delegate)

    override fun unaryMinus(): PlatformInteger =
        NativeInteger(this.delegate.negate())

    override fun plus(other: PlatformInteger): PlatformInteger =
        NativeInteger(this.delegate + (other as NativeInteger).delegate)

    override fun minus(other: PlatformInteger): PlatformInteger =
        NativeInteger(this.delegate - (other as NativeInteger).delegate)

    override fun times(other: PlatformInteger): PlatformInteger =
        NativeInteger(this.delegate * (other as NativeInteger).delegate)

    override fun div(other: PlatformInteger): PlatformInteger {
        val divisor: BigInteger = (other as NativeInteger).delegate
        return NativeInteger(this.delegate / divisor)
    }

    override fun rem(other: PlatformInteger): PlatformInteger {
        val divisor: BigInteger = (other as NativeInteger).delegate
        val quotient: BigInteger = this.delegate / divisor
        return NativeInteger(this.delegate - quotient * divisor)
    }

    override fun toString(): String = this.delegate.toString()
}
