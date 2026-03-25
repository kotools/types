package org.kotools.types.internal

/** Creates a platform-specific integer from the specified [number]. */
@InternalKotoolsTypesApi
public expect fun PlatformInteger(number: Long): PlatformInteger

/** Creates a platform-specific integer from the specified [decimal]. */
@InternalKotoolsTypesApi
public expect fun PlatformInteger(decimal: String): PlatformInteger

/** Implementations of this interface represent a platform-specific integer. */
@InternalKotoolsTypesApi
public interface PlatformInteger {
    // ------------------------------ Comparisons ------------------------------

    /**
     * Returns `true` if the [other] object is an instance of [PlatformInteger]
     * and represents the same integer as this object, or returns `false`
     * otherwise.
     */
    override fun equals(other: Any?): Boolean

    /** Returns a hash code for this integer. */
    override fun hashCode(): Int

    /**
     * Compares this integer with the [other] one for order.
     * Returns a negative number, zero, or a positive number as this integer is
     * less than, equal to, or greater than the [other] one.
     */
    public operator fun compareTo(other: PlatformInteger): Int

    // ------------------------- Arithmetic operations -------------------------

    /** Returns the negative of this integer. */
    public operator fun unaryMinus(): PlatformInteger

    /** Adds the [other] integer to this one. */
    public operator fun plus(other: PlatformInteger): PlatformInteger

    /** Subtracts the [other] integer from this one. */
    public operator fun minus(other: PlatformInteger): PlatformInteger

    /** Multiplies this integer by the [other] one. */
    public operator fun times(other: PlatformInteger): PlatformInteger

    /**
     * Returns the quotient of dividing this integer by the [other] one, or
     * throws an [ArithmeticException] if the [other] integer is zero.
     */
    public operator fun div(other: PlatformInteger): PlatformInteger

    /**
     * Returns the remainder of dividing this integer by the [other] one, or
     * throws an [ArithmeticException] if the [other] integer is zero.
     */
    public operator fun rem(other: PlatformInteger): PlatformInteger

    // ------------------------------ Conversions ------------------------------

    /** Returns the decimal string representation of this integer. */
    override fun toString(): String
}

// -------------- Internal checks for platform-specific integers ---------------
