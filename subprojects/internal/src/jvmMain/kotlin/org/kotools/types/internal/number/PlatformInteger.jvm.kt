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
    override fun compareTo(other: PlatformInteger): Int =
        this.delegate.compareTo((other as JvmInteger).delegate)

    override fun unaryMinus(): PlatformInteger =
        JvmInteger(this.delegate.negate())

    override fun plus(other: PlatformInteger): PlatformInteger =
        JvmInteger(this.delegate + (other as JvmInteger).delegate)

    override fun minus(other: PlatformInteger): PlatformInteger =
        JvmInteger(this.delegate - (other as JvmInteger).delegate)

    override fun times(other: PlatformInteger): PlatformInteger =
        JvmInteger(this.delegate * (other as JvmInteger).delegate)

    override fun div(other: PlatformInteger): PlatformInteger {
        val dividend: BigInteger = this.delegate
        val divisor: BigInteger = (other as JvmInteger).delegate
        val remainder: BigInteger = ((this % other) as JvmInteger).delegate

        val quotient: BigInteger = (dividend - remainder) / divisor

        return JvmInteger(quotient)
    }

    override fun rem(other: PlatformInteger): PlatformInteger {
        val dividend: BigInteger = this.delegate
        val divisor: BigInteger = (other as JvmInteger).delegate.abs()

        val remainder: BigInteger = dividend.mod(divisor)

        return JvmInteger(remainder)
    }

    override fun toString(): String = this.delegate.toString()
}
