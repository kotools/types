package org.kotools.types.internal

import com.ionspin.kotlin.bignum.integer.BigInteger

/** Creates a platform-specific integer from the specified [number]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(number: Long): PlatformInteger {
    val integer = BigInteger(number)
    return NativeInteger(integer)
}

/** Creates a platform-specific integer from the specified [decimal]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(decimal: String): PlatformInteger {
    val integer: BigInteger = BigInteger.parseString(decimal)
    return NativeInteger(integer)
}

@OptIn(InternalKotoolsTypesApi::class)
internal class NativeInteger(
    private val integer: BigInteger
) : PlatformInteger {
    // ------------------------------ Comparisons ------------------------------

    override fun equals(other: Any?): Boolean =
        other is NativeInteger && this.integer == other.integer

    override fun hashCode(): Int = this.integer.hashCode()

    override fun compareTo(other: PlatformInteger): Int {
        if (other !is NativeInteger) Error.prohibitedComparison(this, other)
        return this.integer.compareTo(other.integer)
    }

    // ------------------------- Arithmetic operations -------------------------

    override fun unaryMinus(): PlatformInteger = NativeInteger(-this.integer)

    override fun plus(other: PlatformInteger): PlatformInteger {
        if (other !is NativeInteger) Error.prohibitedAddition(this, other)
        return NativeInteger(this.integer + other.integer)
    }

    override fun minus(other: PlatformInteger): PlatformInteger {
        if (other !is NativeInteger) Error.prohibitedSubtraction(this, other)
        return NativeInteger(this.integer - other.integer)
    }

    override fun times(other: PlatformInteger): PlatformInteger {
        if (other !is NativeInteger) Error.prohibitedMultiplication(this, other)
        return NativeInteger(this.integer * other.integer)
    }

    override fun div(other: PlatformInteger): PlatformInteger {
        if (other !is NativeInteger) Error.prohibitedDivision(this, other)
        return NativeInteger(this.integer / other.integer)
    }

    override fun rem(other: PlatformInteger): PlatformInteger {
        if (other !is NativeInteger) Error.prohibitedRemainder(this, other)
        return NativeInteger(this.integer % other.integer)
    }

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.integer.toString()
}
