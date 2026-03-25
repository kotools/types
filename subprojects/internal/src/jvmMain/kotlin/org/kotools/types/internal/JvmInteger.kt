package org.kotools.types.internal

import java.math.BigInteger

/** Creates a platform-specific integer from the specified [number]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(number: Long): PlatformInteger {
    val value: BigInteger = BigInteger.valueOf(number)
    return JvmInteger(value)
}

/** Creates a platform-specific integer from the specified [decimal]. */
@InternalKotoolsTypesApi
public actual fun PlatformInteger(decimal: String): PlatformInteger {
    val value = BigInteger(decimal)
    return JvmInteger(value)
}

@OptIn(InternalKotoolsTypesApi::class)
internal class JvmInteger(private val integer: BigInteger) : PlatformInteger {
    // ------------------------------ Comparisons ------------------------------

    override fun equals(other: Any?): Boolean =
        other is JvmInteger && this.integer == other.integer

    override fun hashCode(): Int = this.integer.hashCode()

    override fun compareTo(other: PlatformInteger): Int {
        if (other !is JvmInteger) Error.prohibitedComparison(this, other)
        return this.integer.compareTo(other.integer)
    }

    // ------------------------- Arithmetic operations -------------------------

    override fun unaryMinus(): PlatformInteger = JvmInteger(-this.integer)

    override fun plus(other: PlatformInteger): PlatformInteger {
        if (other !is JvmInteger) Error.prohibitedAddition(this, other)
        return JvmInteger(this.integer + other.integer)
    }

    override fun minus(other: PlatformInteger): PlatformInteger {
        if (other !is JvmInteger) Error.prohibitedSubtraction(this, other)
        return JvmInteger(this.integer - other.integer)
    }

    override fun times(other: PlatformInteger): PlatformInteger {
        if (other !is JvmInteger) Error.prohibitedMultiplication(this, other)
        return JvmInteger(this.integer * other.integer)
    }

    override fun div(other: PlatformInteger): PlatformInteger {
        if (other !is JvmInteger) Error.prohibitedDivision(this, other)
        return JvmInteger(this.integer / other.integer)
    }

    override fun rem(other: PlatformInteger): PlatformInteger {
        if (other !is JvmInteger) Error.prohibitedRemainder(this, other)
        return JvmInteger(this.integer % other.integer)
    }

    // ------------------------------ Conversions ------------------------------

    override fun toString(): String = this.integer.toString()
}
