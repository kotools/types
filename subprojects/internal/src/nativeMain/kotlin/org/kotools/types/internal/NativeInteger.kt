package org.kotools.types.internal

import com.ionspin.kotlin.bignum.integer.BigInteger

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
    // ------------------------- Arithmetic operations -------------------------

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
